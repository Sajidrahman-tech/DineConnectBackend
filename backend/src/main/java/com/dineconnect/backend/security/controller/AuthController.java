package com.dineconnect.backend.security.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dineconnect.backend.dto.AuthLoginRequest;
import com.dineconnect.backend.dto.AuthRequest;
import com.dineconnect.backend.dto.AuthResponse;
import com.dineconnect.backend.owner.service.OwnerService;
import com.dineconnect.backend.security.service.JwtService;
import com.dineconnect.backend.user.model.Role;
import com.dineconnect.backend.user.model.User;
import com.dineconnect.backend.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final OwnerService ownerService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user and returns a JWT token")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@RequestBody AuthRequest authRequest) {
        User user = userService.createUser(authRequest.email(), authRequest.username(), authRequest.password(), Role.USER);
        return new AuthResponse(jwtService.generateToken(user.getEmail(), user.getRole(), null));
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT token")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody AuthLoginRequest authLoginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authLoginRequest.email(), authLoginRequest.password())
            );

            if (authentication.isAuthenticated()) {
                User user = (User) authentication.getPrincipal();

                String restaurantId = null;
                if (user.getRole() == Role.OWNER) {
                    restaurantId = ownerService.getRestaurantIdByOwnerId(user.getId());
                }

                String token = jwtService.generateToken(user.getEmail(), user.getRole(), restaurantId);
                return new AuthResponse(token);
            }

            throw new RuntimeException("Authentication failed");
            
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset user password", description = "Resets the password for a user and returns a new JWT token")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> resetPassword(@RequestBody AuthRequest authRequest) {
        userService.resetPassword(authRequest.email(), authRequest.password());
        return Map.of("Response", "Password reset successful !!");
    }
}