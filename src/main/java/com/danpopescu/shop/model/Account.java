package com.danpopescu.shop.model;

import com.danpopescu.shop.model.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    private @NotBlank String firstName, lastName;
    private @NotBlank String username, password;
    private @NotBlank @Email String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private Boolean active;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

}
