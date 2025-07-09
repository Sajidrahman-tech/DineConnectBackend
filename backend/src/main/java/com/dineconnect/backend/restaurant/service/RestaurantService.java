package com.dineconnect.backend.restaurant.service;

import com.dineconnect.backend.restaurant.dto.RestaurantRequest;
import com.dineconnect.backend.restaurant.dto.RestaurantResponse;
import com.dineconnect.backend.restaurant.dto.RestaurantResponseWithoutHref;
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
    private final RestaurantServiceUtil restaurantServiceUtil;

    public Restaurant createRestaurant(RestaurantRequest restaurant) {
        if(restaurantServiceUtil.checkIfRestaurantExists(restaurant.name(), restaurant.address())){
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
            .map(restaurant -> 
                            restaurantResponseBuilder(
                                restaurant, 
                                reviewService.getOverallReview(restaurant.getId())
                            )
                            .href("/api/restaurant/"+ restaurant.getId())
                            .build()
                        )
            .toList();
    }

    public RestaurantResponseWithoutHref getRestaurantById(String id) {
        Restaurant restaurant = restaurantRepository.findById(id)
            .orElseThrow(() -> 
                new RestaurantNotFoundException("Restaurant not found with id: " + id));

        
        
        return new RestaurantResponseWithoutHref(
            restaurant.getId(),
            restaurant.getName(), 
            restaurant.getDescription(), 
            restaurant.getCuisine(), 
            restaurant.getContactNumber(), 
            restaurant.getAddress(), 
            reviewService.getOverallReview(id)
        ); 
    }

    public void deleteRestaurant(String id) {
        restaurantServiceUtil.checkIfRestaurantExists(id);
        restaurantRepository.deleteById(id);
    }
    public Restaurant updateRestaurant(String id, RestaurantRequest restaurantRequest){
        restaurantServiceUtil.checkIfRestaurantExists(id);
        Restaurant restaurant = buildRestaurant(restaurantRequest);
        Restaurant existingRestaurant = restaurantRepository.findById(id).get();
        existingRestaurant.setAddress(restaurant.getAddress());
        existingRestaurant.setContactNumber(restaurant.getContactNumber());
        existingRestaurant.setCuisine(restaurant.getCuisine());
        existingRestaurant.setDescription(restaurant.getDescription());
        existingRestaurant.setName(restaurant.getName());
        return restaurantRepository.save(existingRestaurant);
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

    public RestaurantResponseBuilder restaurantResponseBuilder(Restaurant restaurant, OverallReview overallReview){
        return restaurantResponseBuilder(restaurant)
        .overallReview(overallReview);
    }

    public RestaurantResponseBuilder restaurantResponseBuilder(Restaurant restaurant){
        return RestaurantResponse.builder()
        .id(restaurant.getId())
        .name(restaurant.getName())
        .description(restaurant.getDescription())
        .cuisine(restaurant.getCuisine())
        .contactNumber(restaurant.getContactNumber())
        .address(restaurant.getAddress());
    }



}
