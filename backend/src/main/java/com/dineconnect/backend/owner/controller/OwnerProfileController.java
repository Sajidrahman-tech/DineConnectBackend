package com.dineconnect.backend.owner.controller;

import com.dineconnect.backend.owner.service.OwnerService;
import com.dineconnect.backend.security.service.JwtService;
import com.dineconnect.backend.user.model.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
@Tag(name = "Owner Profile", description = "Fetch restaurant mapping for authenticated owner")
public class OwnerProfileController {

    private final OwnerService ownerService;

    @GetMapping("/profile")
    @Operation(summary = "Get Owner Profile", description = "Returns the restaurantId linked to the currently logged-in owner")
    public ResponseEntity<?> getOwnerProfile() {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ownerId = user.getId();

        String restaurantId = ownerService.getRestaurantIdByOwnerId(ownerId);

        return ResponseEntity.ok().body(
            java.util.Map.of("ownerId", ownerId, "restaurantId", restaurantId)
        );
    }
}
