package com.danpopescu.shop.controller;

import com.danpopescu.shop.model.Account;
import com.danpopescu.shop.payload.AccountRepresentations;
import com.danpopescu.shop.payload.AccountRepresentations.AccountCreateInputDto;
import com.danpopescu.shop.payload.UserSummary;
import com.danpopescu.shop.service.AccountService;
import com.danpopescu.shop.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffAccountController {

    private final AccountService accountService;
    private final AccountRepresentations representations;
    private final Mapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
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

        return ResponseEntity.created(location).body(representations.toSummary(account));
    }

    @GetMapping
    @PreAuthorize("hasRole('STAFF')")
    public List<UserSummary> getAllStaffMembers() {
        List<Account> staffMembers = accountService.getAllStaffMembers();
        return toUserSummaries(staffMembers);
    }

    private List<UserSummary> toUserSummaries(List<Account> staffMembers) {
        return staffMembers.stream()
                .map(mapper::userToUserSummary)
                .collect(Collectors.toList());
    }
}
