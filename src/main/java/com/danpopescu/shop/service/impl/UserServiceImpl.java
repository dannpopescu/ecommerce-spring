package com.danpopescu.shop.service.impl;

import com.danpopescu.shop.exception.ResourceNotFoundException;
import com.danpopescu.shop.model.Role;
import com.danpopescu.shop.model.User;
import com.danpopescu.shop.payload.SignUpRequest;
import com.danpopescu.shop.repository.UserRepository;
import com.danpopescu.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsById(UUID id) {
        return userRepository.existsById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User createCustomer(SignUpRequest signUpRequest) {
        return createUser(signUpRequest, Role.ROLE_CUSTOMER);
    }

    @Override
    public User createStaffMember(SignUpRequest signUpRequest) {
        return createUser(signUpRequest, Role.ROLE_STAFF);
    }

    @Override
    public List<User> getAllStaffMembers() {
        return userRepository.findAllByRole(Role.ROLE_STAFF);
    }

    @Override
    public List<User> getAllCustomers() {
        return userRepository.findAllByRole(Role.ROLE_CUSTOMER);
    }

    @Override
    @Transactional
    public User getById(UUID id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public void deleteById(UUID id) throws ResourceNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        userRepository.deleteById(id);
    }

    private User createUser(SignUpRequest signUpRequest, Role role) {
        User user = User.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .username(signUpRequest.getUsername())
                .active(signUpRequest.getActive())
                .address(signUpRequest.getAddress())
                .build();

        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(role);

        return userRepository.save(user);
    }
}
