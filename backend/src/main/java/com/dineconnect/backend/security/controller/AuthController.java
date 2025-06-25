package com.dineconnect.backend.security.controller;

import com.dineconnect.backend.dto.AuthRequest;
import com.dineconnect.backend.security.service.JwtService;
import com.dineconnect.backend.user.model.User;
import com.dineconnect.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String register(@RequestBody  AuthRequest authRequest) {
        User user = userService.createUser(authRequest.username(), authRequest.password(), false);
        return jwtService.generateToken(user.getUsername());
    }

    @PostMapping("/register/admin")
    public String registerAdmin(@RequestBody  AuthRequest authRequest) {
        User user = userService.createUser(authRequest.username(), authRequest.password(), true);
        return jwtService.generateToken(user.getUsername());
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        if(authentication.isAuthenticated())
            return  jwtService.generateToken(authRequest.username());
        throw new RuntimeException();
    }

}
