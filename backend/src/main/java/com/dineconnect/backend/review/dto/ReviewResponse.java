package com.dineconnect.backend.review.dto;

import java.time.LocalDateTime;

import com.dineconnect.backend.review.model.Review;

import lombok.Builder;

@Builder
public record ReviewResponse(String id, String reviewerName, String comment, Double rating, LocalDateTime reviewedAt) {

    public static ReviewResponse buildReviewResponse(Review review){
        return ReviewResponse
        .builder()
        .id(review.getId())
        .reviewerName(review.getReviewerName())
        .comment(review.getComment())
        .rating(review.getRating())
        .reviewedAt(review.getReviewedAt())
        .build();
    }
}
