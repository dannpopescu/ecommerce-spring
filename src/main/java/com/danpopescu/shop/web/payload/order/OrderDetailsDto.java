package com.danpopescu.shop.web.payload.order;

import com.danpopescu.shop.web.payload.account.AccountSummaryDto;
import com.danpopescu.shop.web.payload.product.ProductOutputDto;
import lombok.Data;

import java.time.Instant;

@Data
public class OrderDetailsDto {
    private String id, comment;
    private Integer count;
    private AccountSummaryDto customer;
    private ProductOutputDto product;
    private Instant createdAt, modifiedAt;
}
