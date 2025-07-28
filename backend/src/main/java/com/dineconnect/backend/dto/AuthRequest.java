package com.dineconnect.backend.dto;

public record AuthRequest(
    String email,
    String username,
    String password,
    String restaurantId //for OWNER only
) {}
