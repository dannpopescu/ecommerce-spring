package com.danpopescu.shop.service;

import com.danpopescu.shop.model.Account;
import com.danpopescu.shop.payload.SignUpRequest;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsById(UUID id);

    Account save(Account account);

    Account createCustomer(SignUpRequest signUpRequest);

    Account createStaffAccount(Account account);

    List<Account> getAllStaffMembers();

    List<Account> getAllCustomers();

    Account getById(UUID id);

    void deleteById(UUID id);
}
