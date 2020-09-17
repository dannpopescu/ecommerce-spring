package com.danpopescu.shop.web.controller;

import com.danpopescu.shop.domain.Account;
import com.danpopescu.shop.domain.Order;
import com.danpopescu.shop.security.UserPrincipal;
import com.danpopescu.shop.service.AccountService;
import com.danpopescu.shop.service.OrderService;
import com.danpopescu.shop.service.ProductService;
import com.danpopescu.shop.web.mapper.OrderMapper;
import com.danpopescu.shop.web.payload.order.OrderDetailsDto;
import com.danpopescu.shop.web.payload.order.OrderInputDto;
import com.danpopescu.shop.web.payload.order.OrderSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    private final ProductService productService;
    private final AccountService accountService;
    private final OrderMapper representations;

    @PostMapping
    public ResponseEntity<?> createOrder(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                         @Valid @RequestBody OrderInputDto payload,
                                         Errors errors) {

        payload.validate(errors, productService);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Order order = representations.from(payload);
        Account customer = accountService.findCustomerAccountById(userPrincipal.getId());
        order.setCustomer(customer);

        Order saved = orderService.save(order);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(order.getId()).toUri();

        return ResponseEntity.created(location).body(representations.toDetails(saved));
    }

    @GetMapping
    public List<OrderSummaryDto> getOrders() {
        List<Order> orders = orderService.findAll();
        return orders.stream()
                .map(representations::toSummary)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderDetailsDto getOrder(@PathVariable UUID id) {
        Order order = orderService.findById(id);
        return representations.toDetails(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable UUID id,
                                         @Valid @RequestBody OrderInputDto payload,
                                         Errors errors) {

        Order existing = orderService.findById(id);

        payload.validate(errors, productService);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Order updated = orderService.save(representations.from(payload, existing));

        return ResponseEntity.ok(representations.toDetails(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable UUID id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
