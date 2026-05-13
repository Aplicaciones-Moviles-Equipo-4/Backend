package com.eventify.platform.operation.interfaces.rest.resources;

import java.util.Date;

public record CreateReviewResource(String content, String fullName, Date socialEventDate, Integer rating, Long profileId, Long socialEventId) {
    public CreateReviewResource {
        if (content == null || content.isBlank()) throw new IllegalArgumentException("Content cannot be null or empty.");

        if (rating == null) throw new IllegalArgumentException("Rating cannot be null or empty.");

        if (fullName == null || fullName.isBlank()) throw new IllegalArgumentException("Full name cannot be null or empty.");

        if (socialEventDate == null) throw new IllegalArgumentException("Social event date cannot be null or empty.");

        if (profileId == null) throw new IllegalArgumentException("Profile ID cannot be null or empty.");

        if (socialEventId == null) throw new IllegalArgumentException("Social event ID cannot be null or empty.");

    }
}
