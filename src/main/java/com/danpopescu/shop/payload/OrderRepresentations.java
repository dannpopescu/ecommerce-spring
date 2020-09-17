package com.danpopescu.shop.payload;

import com.danpopescu.shop.model.Order;
import com.danpopescu.shop.payload.AccountRepresentations.AccountSummaryDto;
import com.danpopescu.shop.payload.ProductRepresentations.ProductOutputDto;
import com.danpopescu.shop.service.ProductService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderRepresentations {

    private final ProductService productService;
    private final AccountRepresentations accountRepresentations;
    private final ProductRepresentations productRepresentations;

    public Order from(OrderInputDto payload) {
        return Order.builder()
                .product(productService.findById(payload.pid))
                .count(payload.count)
                .comment(payload.comment)
                .build();
    }

    public Order from(OrderInputDto payload, Order existing) {
        existing.setProduct(productService.findById(payload.pid));
        existing.setCount(payload.count);
        existing.setComment(payload.comment);
        return existing;
    }

    public OrderDetailsDto toDetails(Order order) {
        var dto = new OrderDetailsDto();
        dto.setId(order.getId().toString());
        dto.setComment(order.getComment());
        dto.setCount(order.getCount());
        dto.setCustomer(accountRepresentations.toSummary(order.getCustomer()));
        dto.setProduct(productRepresentations.toDto(order.getProduct()));
        dto.setCreatedAt(order.getCreatedAt());
        dto.setModifiedAt(order.getModifiedAt());
        return dto;
    }

    public OrderSummaryDto toSummary(Order order) {
        var dto = new OrderSummaryDto();
        dto.setId(order.getId().toString());
        dto.setComment(order.getComment());
        dto.setCount(order.getCount());
        dto.setCid(order.getCustomer().getId().toString());
        dto.setPid(order.getProduct().getId().toString());
        return dto;
    }

    @Data
    public static class OrderSummaryDto {
        private String id, pid, cid, comment;
        private Integer count;
    }

    @Data
    public static class OrderDetailsDto {
        private String id, comment;
        private Integer count;
        private AccountSummaryDto customer;
        private ProductOutputDto product;
        private Instant createdAt, modifiedAt;
    }

    @Data
    public static class OrderInputDto {
        private @NotNull UUID pid;
        private @NotNull @Min(1) Integer count;
        private @NotBlank String comment;

        public void validate(Errors errors, ProductService productService) {
            if (!productService.existsById(this.pid)) {
                errors.rejectValue("pid", "InvalidProductId");
            }
        }
    }
}
