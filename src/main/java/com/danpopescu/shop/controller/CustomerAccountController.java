package com.danpopescu.shop.controller;

import com.danpopescu.shop.model.Account;
import com.danpopescu.shop.payload.AccountRepresentations;
import com.danpopescu.shop.payload.AccountRepresentations.AccountCreateInputDto;
import com.danpopescu.shop.payload.AccountRepresentations.AccountDetailsDto;
import com.danpopescu.shop.payload.AccountRepresentations.AccountSummaryDto;
import com.danpopescu.shop.payload.AccountRepresentations.AccountUpdateInputDto;
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
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerAccountController {

    private final AccountService accountService;
    private final AccountRepresentations representations;

    @PostMapping
    public ResponseEntity<?> createCustomerAccount(@Valid @RequestBody AccountCreateInputDto payload,
                                                   Errors errors) {

        payload.validate(errors, accountService);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Account account = accountService.createCustomerAccount(representations.from(payload));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(account.getId()).toUri();

        return ResponseEntity.created(location).body(representations.toDetails(account));
    }

    @GetMapping
    public List<AccountSummaryDto> getCustomerAccounts() {
        List<Account> accounts = accountService.findAllCustomerAccounts();
        return accounts.stream()
                .map(representations::toSummary)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccountDetailsDto getCustomerAccount(@PathVariable UUID id) {
        Account account = accountService.findCustomerAccountById(id);
        return representations.toDetails(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomerAccount(@PathVariable UUID id,
                                                   @Valid @RequestBody AccountUpdateInputDto payload,
                                                   Errors errors) {

        Account existing = accountService.findCustomerAccountById(id);

        payload.validate(errors, existing, accountService);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Account updated = accountService.save(representations.from(payload, existing));

        return ResponseEntity.ok(representations.toDetails(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerAccount(@PathVariable UUID id) {
        accountService.deleteCustomerAccountById(id);
        return ResponseEntity.noContent().build();
    }

}
