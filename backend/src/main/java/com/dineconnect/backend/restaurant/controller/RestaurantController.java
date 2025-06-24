package com.dineconnect.backend.restaurant.controller;


import com.dineconnect.backend.dto.DineConnectResponse;
import com.dineconnect.backend.restaurant.model.Restaurant;
import com.dineconnect.backend.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public DineConnectResponse getRestaurants() {
        return new DineConnectResponse(
                "success",
                "Restaurants retrieved successfully",
                restaurantService.getAllRestaurants()
        );
    }

    @PostMapping("/admin/create")
    public DineConnectResponse addRestaurant(@RequestBody Restaurant restaurant) {
        return new DineConnectResponse(
                "success",
                "Restaurant added successfully",
                restaurantService.createRestaurant(restaurant)
        );
    }


}
