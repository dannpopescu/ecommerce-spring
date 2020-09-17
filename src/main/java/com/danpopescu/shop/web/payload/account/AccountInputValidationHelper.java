package com.danpopescu.shop.web.payload.account;

import com.danpopescu.shop.service.AccountService;
import io.jsonwebtoken.lang.Objects;
import org.springframework.validation.Errors;

class AccountInputValidationHelper {
    static void validateEmail(Errors errors, String email, AccountService accountService) {
        if (accountService.existsByEmail(email)) {
            errors.rejectValue("email", "EmailNotAvailable");
        }
    }

    static void validateUsername(Errors errors, String username, AccountService accountService) {
        if (accountService.existsByUsername(username)) {
            errors.rejectValue("username", "UsernameNotAvailable");
        }
    }

    static void validatePassword(Errors errors, String password, String passwordConfirm) {
        if (!Objects.nullSafeEquals(password, passwordConfirm)) {
            errors.rejectValue("password", "NonMatchingPassword");
            errors.rejectValue("passwordConfirm", "NonMatchingPassword");
        }
    }
}
