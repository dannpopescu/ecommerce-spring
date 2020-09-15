package com.danpopescu.shop.controller;

import com.danpopescu.shop.model.Order;
import com.danpopescu.shop.payload.ApiResponse;
import com.danpopescu.shop.payload.CreateOrderRequest;
import com.danpopescu.shop.payload.OrderResponse;
import com.danpopescu.shop.payload.OrderSummary;
import com.danpopescu.shop.security.UserPrincipal;
import com.danpopescu.shop.service.OrderService;
import com.danpopescu.shop.util.Mapper;
import com.danpopescu.shop.util.PatchHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.json.JsonMergePatch;
import javax.json.JsonObject;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final Mapper mapper;
    private final PatchHelper patchHelper;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> createOrder(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                         @Valid @RequestBody CreateOrderRequest orderRequest) {

        Order order = orderService.createOrder(userPrincipal, orderRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('STAFF')")
    public List<OrderSummary> getAllOrders() {
        List<Order> orders = orderService.getAll();
        return orders.stream()
                .map(mapper::orderToOrderSummary)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public OrderResponse getOrderById(@PathVariable UUID id) {
        Order order = orderService.getById(id);
        return mapper.orderToOrderResponse(order);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> deleteOrderById(@PathVariable UUID id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{id}", consumes = "application/merge-patch+json")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> updateOrderById(@PathVariable UUID id,
                                             @RequestBody JsonMergePatch patchDocument) {
        JsonObject patchObject = patchDocument.toJsonValue().asJsonObject();
        List<String> nonUpdatableFields = List.of("id", "user", "product", "createdAt", "modifiedAt");
        for (String nonUpdatableField : nonUpdatableFields) {
            if (patchObject.containsKey(nonUpdatableField)) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "'" + nonUpdatableField + "' field cannot be updated"));
            }
        }

        Order order = orderService.getById(id);
        Order updated = patchHelper.mergePatch(patchDocument, order, Order.class);
        orderService.save(updated);
        return ResponseEntity.noContent().build();
    }
}
