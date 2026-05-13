package com.eventify.platform.operation.domain.services;

import com.eventify.platform.operation.domain.model.aggregates.Review;
import com.eventify.platform.operation.domain.model.queries.ExistByProfileIdQuery;
import com.eventify.platform.operation.domain.model.queries.GetAllReviewsQuery;
import com.eventify.platform.operation.domain.model.queries.GetReviewByIdQuery;
import com.eventify.platform.operation.domain.model.queries.GetReviewByProfileIdQuery;

import java.util.List;
import java.util.Optional;

public interface ReviewQueryService {
    Optional<Review> handle(GetReviewByIdQuery query);
    List<Review> handle(GetReviewByProfileIdQuery query);
    List<Review> handle(GetAllReviewsQuery query);
    boolean handle(ExistByProfileIdQuery query);
}
