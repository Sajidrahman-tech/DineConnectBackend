package com.dineconnect.backend.restaurant.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dineconnect.backend.dto.DineConnectResponse;
import com.dineconnect.backend.restaurant.model.Restaurant;
import com.dineconnect.backend.restaurant.service.RestaurantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/restaurant")
@Tag(name = "Admin Restaurant Management", description = "Endpoints for managing restaurants by admin")
@RequiredArgsConstructor
public class AdminRestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    @Operation(summary = "Add a new restaurant", description = "Allows admin to add a new restaurant to the system")
    public DineConnectResponse addRestaurant(@RequestBody Restaurant restaurant) {
        return new DineConnectResponse(
                "success",
                "Restaurant added successfully",
                restaurantService.createRestaurant(restaurant)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a restaurant", description = "Allows admin to delete a restaurant by its ID")
    public DineConnectResponse deleteRestaurant(String id) {
        restaurantService.deleteRestaurant(id);
        return new DineConnectResponse(
                "success",
                "Restaurant deleted successfully",
                null
        );
    }
    
}
