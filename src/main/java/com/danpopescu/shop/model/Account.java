package com.danpopescu.shop.model;

import com.danpopescu.shop.model.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"username"})
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account extends DateAudit {

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

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private Boolean active;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

}
