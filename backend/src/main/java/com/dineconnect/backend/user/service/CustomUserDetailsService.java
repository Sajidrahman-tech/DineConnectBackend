package com.dineconnect.backend.user.service;

import com.dineconnect.backend.user.model.Role;
import com.dineconnect.backend.user.respository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (Objects.equals(username, adminUsername)){
            return new org.springframework.security.core.userdetails.User(
                    username,
                    passwordEncoder.encode(adminPassword),
                    Collections.singleton(Role.ADMIN.getAuthority())
            );
        }
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public String getCurrentUsername() {
        
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return switch (principal) {
            case UserDetails user -> user.getUsername();
            case String username -> username;
            default -> principal.toString();
        };
    }
}
