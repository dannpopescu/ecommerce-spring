package com.danpopescu.shop.payload;

import com.danpopescu.shop.model.Account;
import com.danpopescu.shop.service.AccountService;
import com.danpopescu.shop.validation.Username;
import io.jsonwebtoken.lang.Objects;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * A class for all Account-related Data-Transfer-Objects.
 *
 * The class is a derivative of the code from Quarano project:
 * https://github.com/quarano/quarano-application/blob/develop/backend/src/main/java/quarano/account/web/StaffAccountRepresentations.java
 */
@Component
@RequiredArgsConstructor
public class AccountRepresentations {

    private final PasswordEncoder passwordEncoder;
    private final AddressRepresentations addressRepresentations;

    public Account from(AccountCreateInputDto payload) {
        return Account.builder()
                .firstName(payload.firstName)
                .lastName(payload.lastName)
                .email(payload.email)
                .username(payload.username)
                .active(true)
                .address(addressRepresentations.addressFrom(payload.address))
                .password(passwordEncoder.encode(payload.password))
                .build();
    }

    public Account from(AccountUpdateInputDto payload, Account existing) {
        existing.setFirstName(payload.firstName);
        existing.setLastName(payload.lastName);
        existing.setEmail(payload.email);
        existing.setUsername(payload.username);
        existing.setAddress(addressRepresentations.addressFrom(payload.address, existing.getAddress()));
        existing.setActive(payload.active);
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
        dto.setAddress(addressRepresentations.toDto(account.getAddress()));
        return dto;
    }

    @Data
    public static class AccountSummaryDto {
        private String id, firstName, lastName, email, username;
    }

    @Data
    public static class AccountDetailsDto {
        private String id, firstName, lastName, email, username;
        private AddressRepresentations.AddressDto address;
    }

    @Data
    public static class AccountCreateInputDto {
        private @NotBlank String firstName, lastName;
        private @NotBlank @Email String email;
        private @NotBlank @Username String username;
        private @NotBlank String password, passwordConfirm;
        private @NotNull @Valid AddressRepresentations.AddressDto address;

        public void validate(Errors errors, AccountService accountService) {
            validateUsername(errors, this.username, accountService);
            validateEmail(errors, this.email, accountService);
            validatePassword(errors, this.password, this.passwordConfirm);
        }
    }

    @Data
    public static class AccountUpdateInputDto {
        private @NotBlank String firstName, lastName;
        private @NotBlank @Email String email;
        private @NotBlank @Username String username;
        private @NotNull @Valid AddressRepresentations.AddressDto address;
        private @NotNull Boolean active;

        public void validate(Errors errors, Account existing, AccountService accountService) {
            if (!existing.getUsername().equals(this.username)) {
                validateUsername(errors, this.username, accountService);
            }

            if (!existing.getEmail().equals(this.email)) {
                validateEmail(errors, this.email, accountService);
            }
        }
    }

    @Data
    public static class PasswordUpdateInputDto {
        private @NotBlank String password, passwordConfirm;

        public void validate(Errors errors) {
            validatePassword(errors, this.password, this.passwordConfirm);
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

    private static void validatePassword(Errors errors, String password, String passwordConfirm) {
        if (!Objects.nullSafeEquals(password, passwordConfirm)) {
            errors.rejectValue("password", "NonMatchingPassword");
            errors.rejectValue("passwordConfirm", "NonMatchingPassword");
        }
    }
}
