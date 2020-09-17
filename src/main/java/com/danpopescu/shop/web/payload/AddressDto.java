package com.danpopescu.shop.web.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddressDto {
    private @NotBlank String address, city, postalCode, country, phone;
    private String address2;
}
