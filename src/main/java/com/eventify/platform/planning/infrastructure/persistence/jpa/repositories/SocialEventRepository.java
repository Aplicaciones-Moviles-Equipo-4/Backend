package com.eventify.platform.planning.infrastructure.persistence.jpa.repositories;

import com.eventify.platform.planning.domain.model.aggregates.SocialEvent;
import com.eventify.platform.planning.domain.model.valueobjects.SocialEventTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for SocialEvent aggregate.
 */
@Repository
public interface SocialEventRepository extends JpaRepository<SocialEvent, Long> {

    /**
     * Finds social events by status (accessing embedded value object property).
     */
    List<SocialEvent> findByStatusValueStatus(String valueStatus);

    /**
     * Finds social events by title (accessing embedded value object property).
     */
    List<SocialEvent> findByTitleTitle(String title);

    /**
     * Finds social events by customer name (accessing embedded value object property).
     */
    List<SocialEvent> findByCustomerNameCustomerName(String customerName);

    /**
     * Finds social events owned by a given organizer (profileId).
     */
    List<SocialEvent> findByOrganizerId(Long organizerId);

    /**
     * Finds social events by title containing a specific string (case-insensitive).
     */
    List<SocialEvent> findByTitleTitleContainingIgnoreCase(String title);

    /**
     * Checks if a social event exists by title.
     */
    boolean existsByTitleTitle(String title);

    /**
     * Command to avoid that Customer creates or updates events with same title.
     */
    boolean existsByTitleTitleAndCustomerNameCustomerNameAndIdIsNot(String title, String customerName, Long socialEventId);
}