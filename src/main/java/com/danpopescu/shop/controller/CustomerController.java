package com.danpopescu.shop.controller;

import com.danpopescu.shop.model.Account;
import com.danpopescu.shop.payload.ApiResponse;
import com.danpopescu.shop.payload.SignUpRequest;
import com.danpopescu.shop.payload.UserProfile;
import com.danpopescu.shop.payload.UserSummary;
import com.danpopescu.shop.service.AccountService;
import com.danpopescu.shop.util.Mapper;
import com.danpopescu.shop.util.PatchHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.json.JsonMergePatch;
import javax.json.JsonObject;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final AccountService accountService;
    private final Mapper mapper;
    private final PatchHelper patchHelper;

    @PostMapping
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (accountService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email address is already taken"),
                    HttpStatus.BAD_REQUEST);
        }

        if (accountService.existsByEmail(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken"),
                    HttpStatus.BAD_REQUEST);
        }

        Account account = accountService.createCustomer(signUpRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(account.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Customer registered successfully"));
    }

    @GetMapping
    @PreAuthorize("hasRole('STAFF')")
    public List<UserSummary> getAllCustomers() {
        List<Account> staffMembers = accountService.getAllCustomers();
        return toUserSummaries(staffMembers);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public UserProfile getCustomerProfile(@PathVariable UUID id) {
        Account customer = accountService.findById(id);
        return mapper.userToUserProfile(customer);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> deleteCustomer(@PathVariable UUID id) {
        accountService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private List<UserSummary> toUserSummaries(List<Account> staffMembers) {
        return staffMembers.stream()
                .map(mapper::userToUserSummary)
                .collect(Collectors.toList());
    }

    @PatchMapping(path = "/{id}", consumes = "application/merge-patch+json")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> updateCustomerById(@PathVariable UUID id,
                                                @RequestBody JsonMergePatch patchDocument) {
        JsonObject patchObject = patchDocument.toJsonValue().asJsonObject();
        List<String> nonUpdatableFields = List.of("id", "email", "username", "role", "password", "createdAt", "modifiedAt");
        for (String nonUpdatableField : nonUpdatableFields) {
            if (patchObject.containsKey(nonUpdatableField)) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "'" + nonUpdatableField + "' field cannot be updated"));
            }
        }

        Account account = accountService.findById(id);
        Account updated = patchHelper.mergePatch(patchDocument, account, Account.class);
        accountService.save(updated);
        return ResponseEntity.noContent().build();
    }

}
