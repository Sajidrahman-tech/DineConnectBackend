package com.dineconnect.backend.restaurant.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dineconnect.backend.dto.DineConnectResponse;
import com.dineconnect.backend.dto.DineConnectResponseNoData;
import com.dineconnect.backend.restaurant.dto.RestaurantRequest;
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
    public DineConnectResponse addRestaurant(@RequestBody RestaurantRequest restaurant) {
        return new DineConnectResponse(
                "success",
                "Restaurant added successfully",
                restaurantService.createRestaurant(restaurant)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a restaurant", description = "Allows admin to delete a restaurant by its ID")
    public DineConnectResponseNoData deleteRestaurant(@PathVariable String id) {
        restaurantService.deleteRestaurant(id);
        return new DineConnectResponseNoData(
                "success",
                "Restaurant deleted successfully"
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a restaurant", description = "Allows admin to update a restaurant by its ID")
    public DineConnectResponse updateRestaurant(@PathVariable String id, @RequestBody RestaurantRequest restaurant ){
        return new DineConnectResponse(
            "success"
            , "Restaurant updated successfully", 
            restaurantService.updateRestaurant(id, restaurant));
    }
    
}
