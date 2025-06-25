package com.dineconnect.backend.user.service;

import com.dineconnect.backend.user.model.Role;
import com.dineconnect.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.dineconnect.backend.user.respository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //Create a new user
    public User createUser(String username, String password, Boolean isAdmin) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        return userRepository.save(
                User.builder()
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .role(isAdmin? Role.ADMIN : Role.USER)
                        .build()
        );
    }
}
