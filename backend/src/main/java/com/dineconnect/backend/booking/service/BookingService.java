package com.dineconnect.backend.booking.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dineconnect.backend.booking.dto.BookingRequest;
import com.dineconnect.backend.booking.dto.BookingResponse;
import com.dineconnect.backend.booking.model.Booking;
import com.dineconnect.backend.booking.model.BookingStatus;
import com.dineconnect.backend.booking.repository.BookingRepository;
import com.dineconnect.backend.restaurant.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RestaurantService restaurantService;

    public BookingResponse createBooking(String userId, BookingRequest request) {
        Booking booking = Booking.builder()
                .restaurantId(request.restaurantId())
                .userId(userId)
                .bookingDate(request.bookingDate())
                .bookingTime(request.bookingTime())
                .numberOfGuests(request.numberOfGuests())
                .status(BookingStatus.CONFIRMED)
                .build();

        booking = bookingRepository.save(booking);
        String restaurantName = restaurantService.getRestaurantById(request.restaurantId()).name();

        return mapToResponse(booking, restaurantName);
    }

    public List<BookingResponse> getUserBookings(String userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(b -> mapToResponse(b, ""))
                .collect(Collectors.toList());
    }

    public void cancelBooking(String bookingId, String userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (!booking.getUserId().equals(userId)) {
            throw new SecurityException("Unauthorized to cancel this booking");
        }
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    public List<BookingResponse> getRestaurantBookings(String restaurantId) {
        return bookingRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(b -> mapToResponse(b, ""))
                .collect(Collectors.toList());
    }

    private BookingResponse mapToResponse(Booking booking, String restaurantName) {
        return BookingResponse.builder()
                .id(booking.getId())
                .restaurantId(booking.getRestaurantId())
                .restaurantName(restaurantName)
                .bookingDate(booking.getBookingDate())
                .bookingTime(booking.getBookingTime())
                .numberOfGuests(booking.getNumberOfGuests())
                .status(booking.getStatus())
                .build();
    }

    public List<BookingResponse> getBookingsByRestaurant(String restaurantId) {
        return bookingRepository.findByRestaurantIdOrderByBookingDateDesc(restaurantId)
                .stream()
                .map(b -> mapToResponse(b, ""))
                .toList();
    }
    
    public List<BookingResponse> getBookingsByRestaurantAndDate(String restaurantId, LocalDate date) {
        return bookingRepository.findByRestaurantIdAndBookingDateOrderByBookingTimeAsc(restaurantId, date)
                .stream()
                .map(b -> mapToResponse(b, ""))
                .toList();
    }    

}
