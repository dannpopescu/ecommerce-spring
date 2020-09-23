package com.danpopescu.shop.web.payload.order;

import lombok.Data;

@Data
public class OrderSummaryDto {
    private String id, pid, cid, comment;
    private Integer count;
}
