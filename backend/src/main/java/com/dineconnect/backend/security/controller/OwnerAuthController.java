package com.dineconnect.backend.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

@RequestMapping("/api/owner/auth")
@RestController
@RequiredArgsConstructor
@Tag(name = "Owner Authentication", description = "Endpoints for owner authentication")
public class OwnerAuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final OwnerService ownerService;

    @PostMapping("/register")
    @Operation(summary = "Register a new owner", description = "Creates a new OWNER user and links to a restaurant, then returns a JWT token")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse registerOwner(@RequestBody AuthRequest authRequest) {
        User user = userService.createUser(authRequest.email(), authRequest.username(), authRequest.password(), Role.OWNER);
        ownerService.saveOwnerMapping(user.getId(), authRequest.restaurantId());
        return new AuthResponse(jwtService.generateToken(user.getEmail(), user.getRole(), null));
    }
}
