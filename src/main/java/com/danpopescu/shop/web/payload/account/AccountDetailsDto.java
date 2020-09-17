package com.danpopescu.shop.web.payload.account;

import com.danpopescu.shop.web.payload.AddressDto;
import lombok.Data;

@Data
public class AccountDetailsDto {
    private String id, firstName, lastName, email, username;
    private AddressDto address;
}
