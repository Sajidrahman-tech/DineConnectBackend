package com.dineconnect.backend.restaurant.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private String reviewerName;
    private String comment;
    private Double rating;
    private LocalDateTime reviewedAt;
}
