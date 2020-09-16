package com.danpopescu.shop.service.impl;

import com.danpopescu.shop.exception.ResourceNotFoundException;
import com.danpopescu.shop.model.Account;
import com.danpopescu.shop.model.Role;
import com.danpopescu.shop.payload.SignUpRequest;
import com.danpopescu.shop.repository.AccountRepository;
import com.danpopescu.shop.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public boolean existsById(UUID id) {
        return accountRepository.existsById(id);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account createCustomer(SignUpRequest signUpRequest) {
        return createUser(signUpRequest, Role.ROLE_CUSTOMER);
    }

    @Override
    public Account createStaffAccount(Account account) {
        account.setRole(Role.ROLE_STAFF);
        return accountRepository.save(account);
    }

    @Override
    public List<Account> findAllStaffAccounts() {
        return accountRepository.findAllByRole(Role.ROLE_STAFF);
    }

    @Override
    public List<Account> getAllCustomers() {
        return accountRepository.findAllByRole(Role.ROLE_CUSTOMER);
    }

    @Override
    @Transactional
    public Account findById(UUID id) throws ResourceNotFoundException {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public void deleteById(UUID id) throws ResourceNotFoundException {
        if (!accountRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        accountRepository.deleteById(id);
    }

    private Account createUser(SignUpRequest signUpRequest, Role role) {
        Account account = Account.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .username(signUpRequest.getUsername())
                .active(signUpRequest.getActive())
                .address(signUpRequest.getAddress())
                .build();

        account.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        account.setRole(role);

        return accountRepository.save(account);
    }
}
