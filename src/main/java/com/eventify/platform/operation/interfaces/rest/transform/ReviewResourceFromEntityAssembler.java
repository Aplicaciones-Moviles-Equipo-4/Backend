package com.eventify.platform.operation.interfaces.rest.transform;

import com.eventify.platform.operation.domain.model.aggregates.Review;
import com.eventify.platform.operation.interfaces.rest.resources.ReviewResource;

public class ReviewResourceFromEntityAssembler {
    public static ReviewResource toResourceFromEntity(Review entity){
        return new ReviewResource(entity.getId(), entity.getContent(), entity.getFullName(), entity.getSocialEventDate(), entity.getRating(), entity.getProfileId());
    }
}
