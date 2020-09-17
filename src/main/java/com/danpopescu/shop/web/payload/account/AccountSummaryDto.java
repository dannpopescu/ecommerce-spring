package com.danpopescu.shop.web.payload.account;

import lombok.Data;

@Data
public class AccountSummaryDto {
    private String id, firstName, lastName, email, username;
}
