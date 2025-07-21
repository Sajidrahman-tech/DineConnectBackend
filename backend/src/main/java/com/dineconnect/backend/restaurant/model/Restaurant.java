package com.dineconnect.backend.restaurant.model;

//Geo-Search Integration: Customers can discover nearby restaurants using Google Maps API
//with filtering options (e.g., cuisine, availability, ratings).
//        • Booking System: Real-time calendar interface showing table availability, enabling users to
//        book tables instantly.
//        • Review System: Users can post reviews and rate restaurants based on their dining experience.
//        • Photo Uploads: Restaurant owners can upload images of their venue and dishes to enhance
//their listings.
//        • Owner Dashboard: Admin panel for restaurant owners to manage reservations, edit restaurant
//details, and monitor customer feedback.
//        • Notifications: Automated messages confirming reservations and notifying users of any changes


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurant {
    @Id
    private String id;
    private String name;
    private String description;
    private String cuisine;
    private String contactNumber;
    private String address;

    //filter by price range, type, and keywords
    private int priceRange;
    private String type;
    private List<String> keywords;
    private List<String> imageUrls;
    private Double reservationCharge;
}

