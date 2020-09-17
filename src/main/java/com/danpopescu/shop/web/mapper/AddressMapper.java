package com.danpopescu.shop.web.mapper;

import com.danpopescu.shop.domain.Address;
import com.danpopescu.shop.web.payload.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address addressFrom(AddressDto payload) {
        return Address.builder()
                .address(payload.getAddress())
                .address2(payload.getAddress2())
                .city(payload.getCity())
                .postalCode(payload.getPostalCode())
                .country(payload.getCountry())
                .phone(payload.getPhone())
                .build();
    }

    public Address addressFrom(AddressDto payload, Address existing) {
        existing.setAddress(payload.getAddress());
        existing.setAddress2(payload.getAddress2());
        existing.setCity(payload.getCity());
        existing.setPostalCode(payload.getPostalCode());
        existing.setCountry(payload.getCountry());
        existing.setPhone(payload.getPhone());
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

}
