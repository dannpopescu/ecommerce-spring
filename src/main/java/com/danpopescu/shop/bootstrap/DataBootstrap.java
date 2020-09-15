package com.danpopescu.shop.bootstrap;

import com.danpopescu.shop.model.Address;
import com.danpopescu.shop.model.Role;
import com.danpopescu.shop.model.User;
import com.danpopescu.shop.repository.UserRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataBootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final Faker faker;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User admin = generateUser();
        admin.setUsername("admin");
        admin.setPassword("123");
        admin.setRole(Role.ROLE_ADMIN);

        User staff = generateUser();
        staff.setUsername("staff");
        staff.setPassword("123");
        staff.setRole(Role.ROLE_STAFF);


        User customer = generateUser();
        customer.setUsername("customer");
        customer.setPassword("123");
        customer.setRole(Role.ROLE_CUSTOMER);

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        staff.setPassword(passwordEncoder.encode(staff.getPassword()));
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        userRepository.save(admin);
        userRepository.save(staff);
        userRepository.save(customer);

        System.out.println("##### number of users: " + userRepository.count());
    }

    public Address generateAddress() {
        return Address.builder()
                .address(faker.address().streetAddress(false))
                .city(faker.address().cityName())
                .postalCode(faker.address().zipCode())
                .country(faker.country().name())
                .phone(faker.phoneNumber().cellPhone())
                .build();
    }

    public User generateUser() {
        return User.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.bothify("?????###@gmail.com"))
                .active(true)
                .address(generateAddress())
                .build();
    }
}
