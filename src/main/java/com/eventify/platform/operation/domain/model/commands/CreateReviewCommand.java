package com.eventify.platform.operation.domain.model.commands;

import java.util.Date;

public record CreateReviewCommand(String content, String fullName,Date socialEventDate, Integer rating, Long profileId, Long socialEventId) {
    public CreateReviewCommand{
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or empty.");
        }
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name cannot be null or empty.");
        }
        if (socialEventDate == null) {
            throw new IllegalArgumentException("Social event date cannot be null or empty.");
        }
        if (socialEventId == null) {
            throw new IllegalArgumentException("Social event ID cannot be null or empty.");
        }
    }
}
