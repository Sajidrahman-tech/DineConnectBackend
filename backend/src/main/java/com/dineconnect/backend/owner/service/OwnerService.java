package com.dineconnect.backend.owner.service;

import org.springframework.stereotype.Service;

import com.dineconnect.backend.owner.model.Owner;
import com.dineconnect.backend.owner.repository.OwnerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public Owner saveOwnerMapping(String userId, String restaurantId) {
        Owner owner = Owner.builder()
                .userId(userId)
                .restaurantId(restaurantId)
                .build();
        return ownerRepository.save(owner);
    }

    public String getRestaurantIdByOwnerId(String ownerId) {
        Owner owner = ownerRepository.findByUserId(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found for userId: " + ownerId));
        return owner.getRestaurantId();
    }
}
