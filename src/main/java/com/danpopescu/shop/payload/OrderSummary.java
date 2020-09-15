package com.danpopescu.shop.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderSummary {

    private UUID id;
    private UUID uid;
    private UUID pid;
    private Integer count;
    private String comment;

    public OrderSummary() {
    }

    public OrderSummary(UUID id, UUID uid, UUID pid, Integer count, String comment) {
        this.id = id;
        this.uid = uid;
        this.pid = pid;
        this.count = count;
        this.comment = comment;
    }
}
