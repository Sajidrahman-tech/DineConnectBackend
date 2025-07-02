package com.dineconnect.backend.restaurant.respository;

import com.dineconnect.backend.restaurant.model.Restaurant;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    public Optional<Restaurant> findByNameAndAddress(String name, String address);
}
