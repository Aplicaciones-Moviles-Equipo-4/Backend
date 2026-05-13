package com.eventify.platform.operation.domain.services;

import com.eventify.platform.operation.domain.model.aggregates.Review;
import com.eventify.platform.operation.domain.model.commands.CreateReviewCommand;
import com.eventify.platform.operation.domain.model.commands.UpdateReviewCommand;

import java.util.Optional;

public interface ReviewCommandService {
    Long handle(CreateReviewCommand command);
    Optional<Review> handle(UpdateReviewCommand command);
}
