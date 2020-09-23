package com.danpopescu.shop.security;

import com.danpopescu.shop.domain.Account;
import com.danpopescu.shop.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return UserPrincipal.create(account);
    }

    @Transactional
    public UserDetails loadUserById(UUID uuid) throws UsernameNotFoundException {
        Account account = accountRepository.findById(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + uuid));
        return UserPrincipal.create(account);
    }
}
