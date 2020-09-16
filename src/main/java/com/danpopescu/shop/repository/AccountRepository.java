package com.danpopescu.shop.repository;

import com.danpopescu.shop.model.Account;
import com.danpopescu.shop.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, UUID> {

    Optional<Account> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByIdAndRole(UUID id, Role role);

    List<Account> findAllByRole(Role role);

    Optional<Account> findByIdAndRole(UUID id, Role role);
}
