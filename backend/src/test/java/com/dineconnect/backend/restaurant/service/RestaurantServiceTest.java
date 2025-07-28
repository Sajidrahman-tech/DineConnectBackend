package com.dineconnect.backend.restaurant.service;

import com.dineconnect.backend.restaurant.dto.FilterRequest;
import com.dineconnect.backend.restaurant.dto.RestaurantRequest;
import com.dineconnect.backend.restaurant.dto.RestaurantResponse;
import com.dineconnect.backend.restaurant.dto.RestaurantResponseWithoutHref;
import com.dineconnect.backend.restaurant.exception.RestaurantAlreadyExistsException;
import com.dineconnect.backend.restaurant.exception.RestaurantNotFoundException;
import com.dineconnect.backend.restaurant.model.Restaurant;
import com.dineconnect.backend.restaurant.respository.RestaurantRepository;
import com.dineconnect.backend.review.model.OverallReview;
import com.dineconnect.backend.review.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantServiceTest {
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private ReviewService reviewService;
    @Mock
    private RestaurantServiceUtil restaurantServiceUtil;
    @InjectMocks
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantService = new RestaurantService(restaurantRepository, reviewService, restaurantServiceUtil);
    }

    @Test
    void createRestaurant_success() {
        RestaurantRequest request = mock(RestaurantRequest.class);
        when(restaurantServiceUtil.checkIfRestaurantExists(anyString(), anyString())).thenReturn(false);
        Restaurant restaurant = mock(Restaurant.class);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);
        Restaurant result = restaurantService.createRestaurant(request);
        assertNotNull(result);
    }


    @Test
    void getAllRestaurants_returnsList() {
        Restaurant restaurant = mock(Restaurant.class);
        when(restaurantRepository.findAll()).thenReturn(Collections.singletonList(restaurant));
        when(reviewService.getOverallReview(anyString())).thenReturn(mock(OverallReview.class));
        when(restaurant.getId()).thenReturn("1");
        List<RestaurantResponse> result = restaurantService.getAllRestaurants();
        assertEquals(1, result.size());
    }

    @Test
    void getRestaurantById_found() {
        Restaurant restaurant = mock(Restaurant.class);
        when(restaurantRepository.findById("1")).thenReturn(Optional.of(restaurant));
        when(reviewService.getOverallReview("1")).thenReturn(mock(OverallReview.class));
        when(restaurant.getId()).thenReturn("1");
        when(restaurant.getName()).thenReturn("Test");
        when(restaurant.getDescription()).thenReturn("Desc");
        when(restaurant.getCuisine()).thenReturn("Cuisine");
        when(restaurant.getContactNumber()).thenReturn("123");
        when(restaurant.getAddress()).thenReturn("Addr");
        when(restaurant.getPriceRange()).thenReturn(50);
        when(restaurant.getType()).thenReturn("Type");
        when(restaurant.getKeywords()).thenReturn(Collections.singletonList("Key"));
        when(restaurant.getImageUrls()).thenReturn(Collections.singletonList("img.jpg"));
        when(restaurant.getReservationCharge()).thenReturn(10.0);
        RestaurantResponseWithoutHref resp = restaurantService.getRestaurantById("1");
        assertEquals("1", resp.id());
    }

    @Test
    void getRestaurantById_notFound_throwsException() {
        when(restaurantRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.getRestaurantById("1"));
    }

    @Test
    void deleteRestaurant_success() {
        doNothing().when(restaurantServiceUtil).checkIfRestaurantExists("1");
        doNothing().when(restaurantRepository).deleteById("1");
        assertDoesNotThrow(() -> restaurantService.deleteRestaurant("1"));
    }

    @Test
    void updateRestaurant_success() {
        RestaurantRequest request = mock(RestaurantRequest.class);
        Restaurant restaurant = mock(Restaurant.class);
        Restaurant existing = mock(Restaurant.class);
        when(restaurantRepository.findById("1")).thenReturn(Optional.of(existing));
        when(restaurantRepository.save(existing)).thenReturn(existing);
        when(request.name()).thenReturn("Name");
        when(request.address()).thenReturn("Addr");
        when(request.description()).thenReturn("Desc");
        when(request.cuisine()).thenReturn("Cuisine");
        when(request.contactNumber()).thenReturn("123");
        when(request.priceRange()).thenReturn(10);
        when(request.type()).thenReturn("Type");
        when(request.keywords()).thenReturn(Collections.singletonList("Key"));
        when(request.imageUrls()).thenReturn(Collections.singletonList("img.jpg"));
        when(request.reservationCharge()).thenReturn(5.0);
        Restaurant result = restaurantService.updateRestaurant("1", request);
        assertNotNull(result);
    }

    @Test
    void filterRestaurants_noFilters_returnsAll() {
        FilterRequest filter = mock(FilterRequest.class);
        when(filter.cuisines()).thenReturn(Collections.emptyList());
        when(filter.types()).thenReturn(Collections.emptyList());
        when(filter.keywords()).thenReturn(Collections.emptyList());
        when(filter.priceRange()).thenReturn(100);
        Restaurant restaurant = mock(Restaurant.class);
        when(restaurantRepository.findAll()).thenReturn(Collections.singletonList(restaurant));
        when(reviewService.getOverallReview(anyString())).thenReturn(mock(OverallReview.class));
        when(restaurant.getId()).thenReturn("1");
        List<RestaurantResponse> result = restaurantService.filterRestaurants(filter);
        assertEquals(1, result.size());
    }

    @Test
    void filterRestaurants_withFilters_returnsFiltered() {
        FilterRequest filter = mock(FilterRequest.class);
        when(filter.cuisines()).thenReturn(Arrays.asList("Indian"));
        when(filter.types()).thenReturn(Collections.emptyList());
        when(filter.keywords()).thenReturn(Collections.emptyList());
        when(filter.priceRange()).thenReturn(100);
        Restaurant restaurant = mock(Restaurant.class);
        when(restaurant.getCuisine()).thenReturn("Indian");
        when(restaurant.getType()).thenReturn("Veg");
        when(restaurant.getPriceRange()).thenReturn(50);
        when(restaurant.getKeywords()).thenReturn(Collections.singletonList("Spicy"));
        when(restaurantRepository.findAll()).thenReturn(Collections.singletonList(restaurant));
        when(reviewService.getOverallReview(anyString())).thenReturn(mock(OverallReview.class));
        when(restaurant.getId()).thenReturn("1");
        List<RestaurantResponse> result = restaurantService.filterRestaurants(filter);
        assertEquals(1, result.size());
    }
}

