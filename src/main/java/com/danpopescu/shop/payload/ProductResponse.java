package com.danpopescu.shop.payload;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ProductResponse {

    private UUID id;
    private String title;
    private String description;
    private BigDecimal price;

    public ProductResponse() {
    }

    public ProductResponse(UUID id, String title, String description, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
    }
}
