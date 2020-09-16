package com.danpopescu.shop.payload;

import com.danpopescu.shop.model.Account;
import com.danpopescu.shop.model.Address;
import com.danpopescu.shop.service.AccountService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
@RequiredArgsConstructor
public class AccountRepresentations {

    private final PasswordEncoder passwordEncoder;

    public Account from(AccountCreateInputDto payload) {
        return Account.builder()
                .firstName(payload.getFirstName())
                .lastName(payload.getLastName())
                .email(payload.getEmail())
                .username(payload.getUsername())
                .active(true)
                .address(payload.getAddress())
                .password(passwordEncoder.encode(payload.getPassword()))
                .build();
    }

    public Account from(AccountUpdateInputDto payload, Account existing) {
        existing.setFirstName(payload.getFirstName());
        existing.setLastName(payload.getLastName());
        existing.setUsername(payload.getUsername());
        existing.setEmail(payload.getEmail());
        existing.setAddress(payload.getAddress());
        return existing;
    }

    public AccountSummaryDto toSummary(Account account) {
        var dto = new AccountSummaryDto();
        dto.setId(account.getId().toString());
        dto.setFirstName(account.getFirstName());
        dto.setLastName(account.getLastName());
        dto.setEmail(account.getEmail());
        dto.setUsername(account.getUsername());
        return dto;
    }

    public AccountDetailsDto toDetails(Account account) {
        var dto = new AccountDetailsDto();
        dto.setId(account.getId().toString());
        dto.setFirstName(account.getFirstName());
        dto.setLastName(account.getLastName());
        dto.setEmail(account.getEmail());
        dto.setUsername(account.getUsername());
        dto.setAddress(account.getAddress());
        return dto;
    }

    @Data
    public static class AccountSummaryDto {
        private String id, firstName, lastName, email, username;
    }

    @Data
    public static class AccountDetailsDto {
        private String id, firstName, lastName, email, username;
        private Address address;
    }

    @Data
    public static class AccountCreateInputDto {
        private @NotBlank String firstName, lastName, username, password;
        private @NotBlank @Email String email;
        private @NotNull @Valid Address address;

        public void validate(Errors errors, AccountService accountService) {
            validateUsername(errors, this.username, accountService);
            validateEmail(errors, this.email, accountService);
        }
    }

    @Data
    public static class AccountUpdateInputDto {
        private @NotBlank String firstName, lastName, username;
        private @NotBlank @Email String email;
        private @NotNull @Valid Address address;

        public void validate(Errors errors, Account existing, AccountService accountService) {
            if (!existing.getUsername().equals(this.username)) {
                validateUsername(errors, username, accountService);
            }

            if (!existing.getEmail().equals(this.email)) {
                validateEmail(errors, email, accountService);
            }
        }
    }

    private static void validateEmail(Errors errors, String email, AccountService accountService) {
        if (accountService.existsByEmail(email)) {
            errors.rejectValue("email", "EmailNotAvailable");
        }
    }

    private static void validateUsername(Errors errors, String username, AccountService accountService) {
        if (accountService.existsByUsername(username)) {
            errors.rejectValue("username", "UsernameNotAvailable");
        }
    }
}
