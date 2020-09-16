package com.danpopescu.shop.service;

import com.danpopescu.shop.model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsById(UUID id);

    Account save(Account account);

    Account createStaffAccount(Account account);

    Account createCustomerAccount(Account account);

    List<Account> findAllStaffAccounts();

    List<Account> findAllCustomerAccounts();

    Account findById(UUID id);

    void deleteById(UUID id);

    void changePassword(Account account, String password);

    Account findStaffAccountById(UUID id);

    Account findCustomerAccountById(UUID id);

    void deleteCustomerAccountById(UUID id);
}
