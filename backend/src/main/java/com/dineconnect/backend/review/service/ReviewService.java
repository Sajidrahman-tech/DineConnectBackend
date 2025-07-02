package com.dineconnect.backend.review.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dineconnect.backend.review.dto.ReviewRequest;
import com.dineconnect.backend.review.model.OverallReview;
import com.dineconnect.backend.review.model.Review;
import com.dineconnect.backend.review.repository.ReviewRepository;
import com.dineconnect.backend.user.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomUserDetailsService userDetailsService;

    public List<Review> getReviewsByRestaurantId(String restaurantId) {
        // Logic to fetch reviews by restaurant ID
        return reviewRepository.findByRestaurantId(restaurantId)
                .orElseThrow(() -> new RuntimeException("No reviews found for restaurant with ID: " + restaurantId));
    }

    public Review addReview(String restaurantId, ReviewRequest review) {
        return reviewRepository.save(convertToReview(restaurantId, review));
    }

    public Review convertToReview(String restaurantId, ReviewRequest reviewRequest) {

        return Review.builder()
                .restaurantId(restaurantId)
                .comment(reviewRequest.comment())
                .rating(reviewRequest.rating())
                .reviewerName(userDetailsService.getCurrentUsername())
                .reviewedAt(java.time.LocalDateTime.now())
                .build();
    }

    public OverallReview getOverallReview(String restaurantId) {
        List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId)
                .orElseThrow(() -> new RuntimeException("No reviews found for restaurant with ID: " + restaurantId));

        double overallRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        return new OverallReview(overallRating, "/api/reviews/" + restaurantId);
    }

    
}
