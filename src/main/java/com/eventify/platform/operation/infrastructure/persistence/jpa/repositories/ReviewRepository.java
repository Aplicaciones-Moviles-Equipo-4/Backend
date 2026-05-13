package com.eventify.platform.operation.infrastructure.persistence.jpa.repositories;

import com.eventify.platform.operation.domain.model.aggregates.Review;
import com.eventify.platform.operation.domain.model.valueobjects.ProfileId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProfileId(ProfileId profileId);
    boolean existsByProfileId(ProfileId profileId);
}
