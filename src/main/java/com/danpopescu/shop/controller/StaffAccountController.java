package com.danpopescu.shop.controller;

import com.danpopescu.shop.model.Account;
import com.danpopescu.shop.payload.AccountRepresentations;
import com.danpopescu.shop.payload.AccountRepresentations.*;
import com.danpopescu.shop.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffAccountController {

    private final AccountService accountService;
    private final AccountRepresentations representations;

    @PostMapping
    public ResponseEntity<?> createStaffAccount(@Valid @RequestBody AccountCreateInputDto payload,
                                                Errors errors) {

        payload.validate(errors, accountService);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Account account = accountService.createStaffAccount(representations.from(payload));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(account.getId()).toUri();

        return ResponseEntity.created(location).body(representations.toDetails(account));
    }

    @GetMapping
    public List<AccountSummaryDto> getStaffAccounts() {
        List<Account> accounts = accountService.findAllStaffAccounts();
        return accounts.stream()
                .map(representations::toSummary)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccountDetailsDto getStaffAccount(@PathVariable UUID id) {
        Account account = accountService.findStaffAccountById(id);
        return representations.toDetails(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStaffAccount(@PathVariable UUID id,
                                                @Valid @RequestBody AccountUpdateInputDto payload,
                                                Errors errors) {

        Account existing = accountService.findStaffAccountById(id);

        payload.validate(errors, existing, accountService);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Account updated = accountService.save(representations.from(payload, existing));

        return ResponseEntity.ok(representations.toDetails(updated));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> updateStaffAccountPassword(@PathVariable UUID id,
                                                        @Valid @RequestBody PasswordUpdateInputDto payload,
                                                        Errors errors) {

        Account account = accountService.findStaffAccountById(id);

        payload.validate(errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        accountService.changePassword(account, payload.getPassword());

        return ResponseEntity.noContent().build();
    }
}
