package com.dineconnect.backend.restaurant.service;

import com.dineconnect.backend.restaurant.dto.RestaurantRequest;
import com.dineconnect.backend.restaurant.dto.RestaurantResponse;
import com.dineconnect.backend.restaurant.dto.RestaurantResponse.RestaurantResponseBuilder;
import com.dineconnect.backend.restaurant.exception.RestaurantAlreadyExistsException;
import com.dineconnect.backend.restaurant.exception.RestaurantNotFoundException;
import com.dineconnect.backend.restaurant.model.Restaurant;
import com.dineconnect.backend.restaurant.respository.RestaurantRepository;
import com.dineconnect.backend.review.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.dineconnect.backend.review.model.OverallReview;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ReviewService reviewService;

    public Restaurant createRestaurant(RestaurantRequest restaurant) {
        if(checkIfRestaurantExists(restaurant.name(), restaurant.address())){
            throw new RestaurantAlreadyExistsException(
                String.format("Restaurant with name: %s and address: %s , already exists.", 
                restaurant.name(), restaurant.address())
            );
        }
            
        return restaurantRepository.save(buildRestaurant(restaurant));
    }

    public List<RestaurantResponse> getAllRestaurants(){
        return restaurantRepository.findAll()
        .stream()
        .map(restaurant -> buildRestaurantResponse(restaurant, reviewService.getOverallReview(restaurant.getId())))
        .toList();
    }

    public RestaurantResponse getRestaurantById(String id) {
        RestaurantResponse restaurantResponse =  buildRestaurantResponse(restaurantRepository.findById(id)
            .orElseThrow(() -> 
                new RestaurantNotFoundException("Restaurant not found with id: " + id)
                ),reviewService.getOverallReview(id));
        return restaurantResponse; 
    }

    public void deleteRestaurant(String id) {
        checkIfRestaurantExists(id);
        restaurantRepository.deleteById(id);
    }
    public Restaurant updateRestaurant(String id, RestaurantRequest restaurantRequest){
        checkIfRestaurantExists(id);
        Restaurant restaurant = buildRestaurant(restaurantRequest);
        Restaurant existingRestaurant = restaurantRepository.findById(id).get();
        existingRestaurant.setAddress(restaurant.getAddress());
        existingRestaurant.setContactNumber(restaurant.getContactNumber());
        existingRestaurant.setCuisine(restaurant.getCuisine());
        existingRestaurant.setDescription(restaurant.getDescription());
        existingRestaurant.setName(restaurant.getName());
        return restaurantRepository.save(existingRestaurant);
    }

    public void checkIfRestaurantExists(String id){
        if (!restaurantRepository.existsById(id)) {
            throw new RestaurantNotFoundException("Restaurant not found with id: " + id);
        }
    }

    public boolean checkIfRestaurantExists(String name, String address){
        return restaurantRepository.findByNameAndAddress(name,address).isPresent();
    }

    public Restaurant buildRestaurant(RestaurantRequest restaurantRequest){
        return Restaurant.builder()
        .name(restaurantRequest.name())
        .address(restaurantRequest.address())
        .description(restaurantRequest.description())
        .cuisine(restaurantRequest.cuisine())
        .contactNumber(restaurantRequest.contactNumber())
        .address(restaurantRequest.address())
        .build();
    }

    public RestaurantResponse buildRestaurantResponse(Restaurant restaurant){
        return buildRestaurantResponseBuilder(restaurant).build();
    }

    public RestaurantResponse buildRestaurantResponse(Restaurant restaurant, OverallReview overallReview){
        return buildRestaurantResponseBuilder(restaurant)
        .overallReview(overallReview)
        .build();
    }

    public RestaurantResponseBuilder buildRestaurantResponseBuilder(Restaurant restaurant){
        return RestaurantResponse.builder()
        .name(restaurant.getName())
        .description(restaurant.getDescription())
        .cuisine(restaurant.getCuisine())
        .contactNumber(restaurant.getContactNumber())
        .address(restaurant.getAddress());
    }



}
