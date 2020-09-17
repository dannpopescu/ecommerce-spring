package com.danpopescu.shop.service.impl;

import com.danpopescu.shop.domain.Account;
import com.danpopescu.shop.domain.Role;
import com.danpopescu.shop.persistence.repository.AccountRepository;
import com.danpopescu.shop.service.AccountService;
import com.danpopescu.shop.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean existsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account createStaffAccount(Account account) {
        account.setRole(Role.ROLE_STAFF);
        return accountRepository.save(account);
    }

    @Override
    public Account createCustomerAccount(Account account) {
        account.setRole(Role.ROLE_CUSTOMER);
        return accountRepository.save(account);
    }

    @Override
    public List<Account> findAllStaffAccounts() {
        return accountRepository.findAllByRole(Role.ROLE_STAFF);
    }

    @Override
    public List<Account> findAllCustomerAccounts() {
        return accountRepository.findAllByRole(Role.ROLE_CUSTOMER);
    }

    @Override
    public void changePassword(Account account, String password) {
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
    }

    @Override
    public Account findStaffAccountById(UUID id) throws ResourceNotFoundException {
        return accountRepository.findByIdAndRole(id, Role.ROLE_STAFF)
                .orElseThrow(() -> new ResourceNotFoundException("Staff Account", "id", id));
    }

    @Override
    public Account findCustomerAccountById(UUID id) throws ResourceNotFoundException {
        return accountRepository.findByIdAndRole(id, Role.ROLE_CUSTOMER)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Account", "id", id));
    }

    @Override
    public void deleteCustomerAccountById(UUID id) throws ResourceNotFoundException {
        if (!accountRepository.existsByIdAndRole(id, Role.ROLE_CUSTOMER)) {
            throw new ResourceNotFoundException("Customer Account", "id", id);
        }
        accountRepository.deleteById(id);
    }
}
