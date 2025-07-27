package com.dineconnect.backend.owner.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dineconnect.backend.owner.model.Owner;

public interface OwnerRepository extends MongoRepository<Owner, String> {
    Optional<Owner> findByUserId(String userId);
}
