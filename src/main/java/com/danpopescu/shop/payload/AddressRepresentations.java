package com.danpopescu.shop.payload;

import com.danpopescu.shop.model.Address;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
public class AddressRepresentations {

    public Address addressFrom(AddressDto payload) {
        return Address.builder()
                .address(payload.address)
                .address2(payload.address2)
                .city(payload.city)
                .postalCode(payload.postalCode)
                .country(payload.country)
                .phone(payload.phone)
                .build();
    }

    public Address addressFrom(AddressDto payload, Address existing) {
        existing.setAddress(payload.address);
        existing.setAddress2(payload.address2);
        existing.setCity(payload.city);
        existing.setPostalCode(payload.postalCode);
        existing.setCountry(payload.country);
        existing.setPhone(payload.phone);
        return existing;
    }

    public AddressDto toDto(Address address) {
        var dto = new AddressDto();
        dto.setAddress(address.getAddress());
        dto.setAddress2(address.getAddress2());
        dto.setCity(address.getCity());
        dto.setPostalCode(address.getPostalCode());
        dto.setCountry(address.getCountry());
        dto.setPhone(address.getPhone());
        return dto;
    }

    @Data
    public static class AddressDto {
        private @NotBlank String address, city, postalCode, country, phone;
        private String address2;
    }
}
