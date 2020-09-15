package com.danpopescu.shop.payload;

import com.danpopescu.shop.model.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserProfile {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private Address address;

    public UserProfile(UUID id, String firstName, String lastName, String email, String username, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.address = address;
    }
}
