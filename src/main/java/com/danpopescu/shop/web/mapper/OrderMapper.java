package com.danpopescu.shop.web.mapper;

import com.danpopescu.shop.domain.Order;
import com.danpopescu.shop.service.ProductService;
import com.danpopescu.shop.web.payload.order.OrderDetailsDto;
import com.danpopescu.shop.web.payload.order.OrderInputDto;
import com.danpopescu.shop.web.payload.order.OrderSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ProductService productService;
    private final AccountMapper accountMapper;
    private final ProductMapper productMapper;

    public Order from(OrderInputDto payload) {
        return Order.builder()
                .product(productService.findById(payload.getPid()))
                .count(payload.getCount())
                .comment(payload.getComment())
                .build();
    }

    public Order from(OrderInputDto payload, Order existing) {
        existing.setProduct(productService.findById(payload.getPid()));
        existing.setCount(payload.getCount());
        existing.setComment(payload.getComment());
        return existing;
    }

    public OrderDetailsDto toDetails(Order order) {
        var dto = new OrderDetailsDto();
        dto.setId(order.getId().toString());
        dto.setComment(order.getComment());
        dto.setCount(order.getCount());
        dto.setCustomer(accountMapper.toSummary(order.getCustomer()));
        dto.setProduct(productMapper.toDto(order.getProduct()));
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

}
