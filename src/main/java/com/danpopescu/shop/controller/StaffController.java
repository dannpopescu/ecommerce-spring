package com.danpopescu.shop.controller;

import com.danpopescu.shop.model.User;
import com.danpopescu.shop.payload.ApiResponse;
import com.danpopescu.shop.payload.SignUpRequest;
import com.danpopescu.shop.payload.UserSummary;
import com.danpopescu.shop.service.UserService;
import com.danpopescu.shop.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {

    private final UserService userService;
    private final Mapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerStaffMember(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email address is already taken"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userService.existsByEmail(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = userService.createStaffMember(signUpRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Staff member registered successfully"));
    }

    @GetMapping
    @PreAuthorize("hasRole('STAFF')")
    public List<UserSummary> getAllStaffMembers() {
        List<User> staffMembers = userService.getAllStaffMembers();
        return toUserSummaries(staffMembers);
    }

    private List<UserSummary> toUserSummaries(List<User> staffMembers) {
        return staffMembers.stream()
                .map(mapper::userToUserSummary)
                .collect(Collectors.toList());
    }
}
