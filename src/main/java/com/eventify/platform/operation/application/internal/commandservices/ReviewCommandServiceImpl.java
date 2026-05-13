package com.eventify.platform.operation.application.internal.commandservices;

import com.eventify.platform.operation.application.internal.outboundservices.acl.ExternalProfileService;
import com.eventify.platform.operation.domain.model.aggregates.Review;
import com.eventify.platform.operation.domain.model.commands.CreateReviewCommand;
import com.eventify.platform.operation.domain.model.commands.UpdateReviewCommand;
import com.eventify.platform.operation.domain.services.ReviewCommandService;
import com.eventify.platform.operation.infrastructure.persistence.jpa.repositories.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ReviewCommandServiceImpl implements ReviewCommandService {
    private final ReviewRepository reviewRepository;
    private final ExternalProfileService externalProfileService;

    public ReviewCommandServiceImpl(ReviewRepository reviewRepository, ExternalProfileService externalProfileService){
        this.reviewRepository = reviewRepository;
        this.externalProfileService = externalProfileService;
    }

    @Override
    public Long handle(CreateReviewCommand command) {
        // Fetch profile from an external service by full name
        var fullName = externalProfileService.fetchFullNameByProfileId(command.profileId());
        if (fullName.isEmpty()) {
            throw new IllegalArgumentException("Profile not found with full name: " + command.fullName());
        }
        // Fetch social event from an external service by social event name and date
        // Put logic here


        var review = new Review(command);
        try{
            reviewRepository.save(review);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create review: " + e.getMessage(), e);
        }
        return review.getId();
    }

    @Override
    public Optional<Review> handle(UpdateReviewCommand command) {
        // Fetch the existing review by ID
        var review = reviewRepository.findById(command.ReviewId());
        if (review.isEmpty()) {
            throw new IllegalArgumentException("Review not found with ID: " + command.ReviewId());
        }
        try {
            var updatedReview = review.get();
            updatedReview.updateInformation(command.content(), command.fullName(), command.socialEventDate(), command.rating(), command.profileId());
            reviewRepository.save(updatedReview);
            return Optional.of(updatedReview);
        }catch (Exception e){
            throw new RuntimeException("Failed to update review: " + e.getMessage(), e);
        }
    }
}
