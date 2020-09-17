package com.danpopescu.shop.service;

import com.danpopescu.shop.exception.ResourceNotFoundException;
import com.danpopescu.shop.model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Account save(Account account);

    Account createStaffAccount(Account account);

    Account createCustomerAccount(Account account);

    List<Account> findAllStaffAccounts();

    List<Account> findAllCustomerAccounts();

    void changePassword(Account account, String password);

    Account findStaffAccountById(UUID id) throws ResourceNotFoundException;

    Account findCustomerAccountById(UUID id) throws ResourceNotFoundException;

    void deleteCustomerAccountById(UUID id) throws ResourceNotFoundException;
}
