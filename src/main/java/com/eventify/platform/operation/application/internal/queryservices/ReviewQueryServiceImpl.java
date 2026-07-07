package com.eventify.platform.operation.application.internal.queryservices;

import com.eventify.platform.operation.domain.model.aggregates.Review;
import com.eventify.platform.operation.domain.model.queries.ExistByProfileIdQuery;
import com.eventify.platform.operation.domain.model.queries.GetAllReviewsQuery;
import com.eventify.platform.operation.domain.model.queries.GetReviewByIdQuery;
import com.eventify.platform.operation.domain.model.queries.GetReviewByProfileIdQuery;
import com.eventify.platform.operation.domain.services.ReviewQueryService;
import com.eventify.platform.operation.infrastructure.persistence.jpa.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the ReviewQueryService interface.
 * Provides methods to handle queries related to reviews.
 */
@Service
public class ReviewQueryServiceImpl implements ReviewQueryService {
    private ReviewRepository reviewRepository;

    /**
     * Constructs a ReviewQueryServiceImpl with the specified ReviewRepository.
     *
     * @param reviewRepository the repository used to access review data
     */
    public ReviewQueryServiceImpl(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    /**
     * Handles the query to retrieve a review by its ID.
     *
     * @param query the query containing the review ID
     * @return an Optional containing the review if found, or an empty Optional if not
     */
    @Override
    public Optional<Review> handle(GetReviewByIdQuery query) {
        return reviewRepository.findById(query.reviewId());
    }

    /**
     * Handles the query to retrieve all reviews associated with a specific profile ID.
     *
     * @param query the query containing the profile ID
     * @return a list of reviews associated with the profile ID
     */
    @Override
    public List<Review> handle(GetReviewByProfileIdQuery query) {
        return reviewRepository.findByProfileId(query.profileId());
    }

    /**
     * Handles the query to retrieve all reviews.
     *
     * @param query the query to retrieve all reviews
     * @return a list of all reviews
     */
    @Override
    public List<Review> handle(GetAllReviewsQuery query) {
        return reviewRepository.findAll();
    }

    /**
     * Handles the query to check if a review exists for a specific profile ID.
     *
     * @param query the query containing the profile ID
     * @return true if a review exists for the profile ID, false otherwise
     */
    @Override
    public boolean handle(ExistByProfileIdQuery query) {
        return reviewRepository.existsByProfileId(query.profileId());
    }
}
