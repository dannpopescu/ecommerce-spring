package com.danpopescu.shop.web.payload.account;

import com.danpopescu.shop.domain.Account;
import com.danpopescu.shop.persistence.validation.Username;
import com.danpopescu.shop.service.AccountService;
import com.danpopescu.shop.web.payload.AddressDto;
import lombok.Data;
import org.springframework.validation.Errors;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AccountUpdateInputDto {
    private @NotBlank String firstName, lastName;
    private @NotBlank @Email String email;
    private @NotBlank @Username
    String username;
    private @NotNull @Valid AddressDto address;
    private @NotNull Boolean active;

    public void validate(Errors errors, Account existing, AccountService accountService) {
        if (!existing.getUsername().equals(this.username)) {
            AccountInputValidationHelper.validateUsername(errors, this.username, accountService);
        }

        if (!existing.getEmail().equals(this.email)) {
            AccountInputValidationHelper.validateEmail(errors, this.email, accountService);
        }
    }
}
