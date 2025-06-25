package com.dineconnect.backend.restaurant.service;

import com.dineconnect.backend.restaurant.model.Restaurant;
import com.dineconnect.backend.restaurant.model.Review;
import com.dineconnect.backend.restaurant.respository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant addReview(String id, Review review) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();
        restaurant.getReviews().add(review);
        return restaurantRepository.save(restaurant);
    }

}
