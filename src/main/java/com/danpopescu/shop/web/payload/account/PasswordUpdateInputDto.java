package com.danpopescu.shop.web.payload.account;

import lombok.Data;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordUpdateInputDto {
    private @NotBlank String password, passwordConfirm;

    public void validate(Errors errors) {
        AccountInputValidationHelper.validatePassword(errors, this.password, this.passwordConfirm);
    }
}
