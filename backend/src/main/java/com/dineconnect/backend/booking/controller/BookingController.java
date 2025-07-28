package com.dineconnect.backend.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.dineconnect.backend.booking.dto.BookingRequest;
import com.dineconnect.backend.booking.service.BookingService;
import com.dineconnect.backend.dto.DineConnectResponse;
import com.dineconnect.backend.dto.DineConnectResponseNoData;
import com.dineconnect.backend.user.model.Role;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "User Bookings", description = "User endpoints for managing restaurant reviews")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DineConnectResponse create(@AuthenticationPrincipal UserDetails user, @RequestBody BookingRequest request) {
        return new DineConnectResponse("success", "Booking confirmed", bookingService.createBooking(user.getUsername(), request));
    }

    @GetMapping
    public DineConnectResponse getMyBookings(@AuthenticationPrincipal UserDetails user) {
        return new DineConnectResponse("success", "Bookings fetched", bookingService.getUserBookings(user.getUsername()));
    }

    @DeleteMapping("/{id}")
    public DineConnectResponseNoData cancel(@PathVariable String id, @AuthenticationPrincipal UserDetails user) {
        Role userRole = extractRole(user);
        bookingService.cancelBooking(id, user.getUsername(), userRole);
        return new DineConnectResponseNoData("success", "Booking cancelled");
    }

    private Role extractRole(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority().replace("ROLE_", ""))
                .map(Role::valueOf)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No role found for user"));
    }
}
