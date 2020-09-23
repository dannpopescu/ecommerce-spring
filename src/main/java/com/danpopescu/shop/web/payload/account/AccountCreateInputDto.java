package com.danpopescu.shop.web.payload.account;

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
public class AccountCreateInputDto {
    private @NotBlank String firstName, lastName;
    private @NotBlank @Email String email;
    private @NotBlank @Username
    String username;
    private @NotBlank String password, passwordConfirm;
    private @NotNull @Valid AddressDto address;

    public void validate(Errors errors, AccountService accountService) {
        AccountInputValidationHelper.validateUsername(errors, this.username, accountService);
        AccountInputValidationHelper.validateEmail(errors, this.email, accountService);
        AccountInputValidationHelper.validatePassword(errors, this.password, this.passwordConfirm);
    }
}
