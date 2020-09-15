package com.danpopescu.shop.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class OrderResponse {

    private UUID id;
    private UserProfile user;
    private ProductResponse product;
    private Integer count;
    private String comment;
    private Instant createdAt;
    private Instant modifiedAt;

    public OrderResponse() {
    }

    public OrderResponse(UUID id, UserProfile user, ProductResponse product, Integer count, String comment,
                         Instant createdAt, Instant modifiedAt) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.count = count;
        this.comment = comment;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
