package com.dineconnect.backend.user.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    USER,
    OWNER,
    ADMIN;

    public SimpleGrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority("ROLE_" + this.name());
    }

    public static Role fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Role value cannot be null or empty");
        }
        
        String cleanValue = value.trim().toUpperCase();
        
        try {
            return Role.valueOf(cleanValue);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                String.format("Invalid role: '%s'. Expected values: USER, OWNER, ADMIN. " +
                             "Received value: '%s' (length: %d)", 
                             value, cleanValue, value.length()), e);
        }
    }
    
    // Add this method for MongoDB compatibility
    public static Role fromStringCaseInsensitive(String value) {
        return fromString(value);
    }
}