package com.danpopescu.shop.payload;

import com.danpopescu.shop.model.Address;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank
    @Size(min = 1, max = 40)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 40)
    private String lastName;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 15)
    private String username;

    @NotBlank
    @Size(max = 100)
    private String password;

    @NotNull
    private Boolean active;

    @NotNull
    @Valid
    private Address address;
}
