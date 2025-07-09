package com.dineconnect.backend.restaurant.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RestaurantRequest(
    @NotBlank
    String name, 
    @NotBlank
    String description, 
    @NotBlank
    String cuisine, 
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    String contactNumber, 
    String address) {}
