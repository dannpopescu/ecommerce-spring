package com.danpopescu.shop.service;

import com.danpopescu.shop.model.User;
import com.danpopescu.shop.payload.SignUpRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsById(UUID id);

    User save(User user);

    User createCustomer(SignUpRequest signUpRequest);

    User createStaffMember(SignUpRequest signUpRequest);

    List<User> getAllStaffMembers();

    List<User> getAllCustomers();

    User getById(UUID id);

    void deleteById(UUID id);
}
