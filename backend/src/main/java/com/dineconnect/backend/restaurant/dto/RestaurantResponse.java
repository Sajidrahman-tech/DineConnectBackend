package com.dineconnect.backend.restaurant.dto;

import com.dineconnect.backend.review.model.OverallReview;

import lombok.Builder;

@Builder
public record RestaurantResponse(String name, String description, String cuisine, String contactNumber, String address, OverallReview overallReview) {}
