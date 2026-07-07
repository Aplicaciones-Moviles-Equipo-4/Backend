package com.eventify.platform.operation.domain.model.commands;

import java.util.Date;

/**
 * Command to create a new review.
 * Contains all the necessary data to create a review entity.
 *
 * @param content         the content of the review
 * @param fullName        the full name of the reviewer
 * @param socialEventDate the date of the social event
 * @param rating          the rating of the review (1 to 5)
 * @param profileId       the ID of the profile associated with the review
 * @param socialEventId   the ID of the social event associated with the review
 */
public record CreateReviewCommand(String content, String fullName, Date socialEventDate, Integer rating, Long profileId, Long socialEventId) {
    /**
     * Validates the input data for creating a review.
     *
     * @throws IllegalArgumentException if any field is invalid
     */
    public CreateReviewCommand {
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
