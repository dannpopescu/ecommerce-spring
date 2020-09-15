package com.danpopescu.shop.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserSummary {

    private UUID id;
    private String firstName;
    private String lastName;
    private String username;

    public UserSummary() {
    }

    public UserSummary(UUID id, String firstName, String lastName, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }
}
