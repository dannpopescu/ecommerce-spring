package com.danpopescu.shop.model;

import com.danpopescu.shop.model.audit.DateAudit;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

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
public class User extends DateAudit {

    @NotBlank
    @Size(min = 1, max = 40)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 40)
    private String lastName;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NaturalId
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

    public User() {
    }

    @Builder
    public User(String firstName, String lastName, String email, String username, String password,
                Boolean active, Address address, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.active = active;
        this.address = address;
        this.role = role;
    }
}
