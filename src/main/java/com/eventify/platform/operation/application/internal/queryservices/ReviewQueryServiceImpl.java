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

@Service
public class ReviewQueryServiceImpl implements ReviewQueryService {
    private ReviewRepository reviewRepository;

    public ReviewQueryServiceImpl(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Optional<Review> handle(GetReviewByIdQuery query) {
        return reviewRepository.findById(query.reviewId());
    }

    @Override
    public List<Review> handle(GetReviewByProfileIdQuery query) { return reviewRepository.findByProfileId(query.profileId());}

    @Override
    public List<Review> handle(GetAllReviewsQuery query) {
        return reviewRepository.findAll();
    }

    @Override
    public boolean handle(ExistByProfileIdQuery query) { return reviewRepository.existsByProfileId(query.profileId());}
}
