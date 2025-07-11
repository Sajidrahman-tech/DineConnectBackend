package com.dineconnect.backend.booking.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dineconnect.backend.booking.model.Booking;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByUserId(String userId);
    List<Booking> findByRestaurantId(String restaurantId);
    List<Booking> findByRestaurantIdAndBookingDate(String restaurantId, LocalDate bookingDate);
    List<Booking> findByRestaurantIdOrderByBookingDateDesc(String restaurantId);
    List<Booking> findByRestaurantIdAndBookingDateOrderByBookingTimeAsc(String restaurantId, LocalDate bookingDate);
}

