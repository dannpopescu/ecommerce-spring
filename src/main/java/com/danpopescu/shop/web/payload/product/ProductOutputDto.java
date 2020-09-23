package com.danpopescu.shop.web.payload.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductOutputDto {
    private String id, title, description;
    private BigDecimal price;
}
