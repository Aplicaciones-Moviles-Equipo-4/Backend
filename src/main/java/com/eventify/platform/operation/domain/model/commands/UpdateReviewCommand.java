package com.eventify.platform.operation.domain.model.commands;

import java.util.Date;

/**
 * Command to update an existing review.
 * Contains all the necessary data to update a review entity.
 *
 * @param ReviewId        the ID of the review to be updated
 * @param content         the updated content of the review
 * @param fullName        the updated full name of the reviewer
 * @param rating          the updated rating of the review (1 to 5)
 * @param socialEventDate the updated date of the social event
 * @param profileId       the updated profile ID associated with the review
 */
public record UpdateReviewCommand(Long ReviewId, String content, String fullName, Integer rating, Date socialEventDate, Long profileId) {
    /**
     * Validates the input data for updating a review.
     *
     * @throws IllegalArgumentException if any field is invalid
     */
    public UpdateReviewCommand {
        if (ReviewId == null || ReviewId <= 0) {
            throw new IllegalArgumentException("Review ID must be a positive number.");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or empty.");
        }
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
    }
}
