package com.danpopescu.shop.web.mapper;

import com.danpopescu.shop.domain.Account;
import com.danpopescu.shop.web.payload.account.AccountCreateInputDto;
import com.danpopescu.shop.web.payload.account.AccountDetailsDto;
import com.danpopescu.shop.web.payload.account.AccountSummaryDto;
import com.danpopescu.shop.web.payload.account.AccountUpdateInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    private final PasswordEncoder passwordEncoder;
    private final AddressMapper addressMapper;

    public Account from(AccountCreateInputDto payload) {
        return Account.builder()
                .firstName(payload.getFirstName())
                .lastName(payload.getLastName())
                .email(payload.getEmail())
                .username(payload.getUsername())
                .active(true)
                .address(addressMapper.addressFrom(payload.getAddress()))
                .password(passwordEncoder.encode(payload.getPassword()))
                .build();
    }

    public Account from(AccountUpdateInputDto payload, Account existing) {
        existing.setFirstName(payload.getFirstName());
        existing.setLastName(payload.getLastName());
        existing.setEmail(payload.getEmail());
        existing.setUsername(payload.getUsername());
        existing.setAddress(addressMapper.addressFrom(payload.getAddress(), existing.getAddress()));
        existing.setActive(payload.getActive());
        return existing;
    }

    public AccountSummaryDto toSummary(Account account) {
        var dto = new AccountSummaryDto();
        dto.setId(account.getId().toString());
        dto.setFirstName(account.getFirstName());
        dto.setLastName(account.getLastName());
        dto.setEmail(account.getEmail());
        dto.setUsername(account.getUsername());
        return dto;
    }

    public AccountDetailsDto toDetails(Account account) {
        var dto = new AccountDetailsDto();
        dto.setId(account.getId().toString());
        dto.setFirstName(account.getFirstName());
        dto.setLastName(account.getLastName());
        dto.setEmail(account.getEmail());
        dto.setUsername(account.getUsername());
        dto.setAddress(addressMapper.toDto(account.getAddress()));
        return dto;
    }

}
