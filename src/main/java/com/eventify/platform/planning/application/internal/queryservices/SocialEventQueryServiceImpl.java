package com.eventify.platform.planning.application.internal.queryservices;

import com.eventify.platform.planning.domain.model.aggregates.SocialEvent;
import com.eventify.platform.planning.domain.model.queries.GetAllSocialEventQuery;
import com.eventify.platform.planning.domain.model.queries.GetSocialEventByStatusQuery;
import com.eventify.platform.planning.domain.model.queries.GetSocialEventByTitleQuery;
import com.eventify.platform.planning.domain.model.queries.GetSocialEventsByOrganizerQuery;
import com.eventify.platform.planning.domain.model.queries.GetSocialEventsByOrganizerIdQuery;
import com.eventify.platform.planning.domain.services.SocialEventQueryService;
import com.eventify.platform.planning.infrastructure.persistence.jpa.repositories.SocialEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of SocialEventQueryService.
 */
@Service
public class SocialEventQueryServiceImpl implements SocialEventQueryService {

    private final SocialEventRepository socialEventRepository;

    public SocialEventQueryServiceImpl(SocialEventRepository socialEventRepository) {
        this.socialEventRepository = socialEventRepository;
    }

    @Override
    public List<SocialEvent> handle(GetAllSocialEventQuery query) {
        return socialEventRepository.findAll();
    }

    @Override
    public List<SocialEvent> handle(GetSocialEventByStatusQuery query) {
        return socialEventRepository.findByStatusValueStatus(query.status());
    }

    @Override
    public List<SocialEvent> handle(GetSocialEventByTitleQuery query) {
        return socialEventRepository.findByTitleTitleContainingIgnoreCase(query.title());
    }

    @Override
    public List<SocialEvent> handle(GetSocialEventsByOrganizerQuery query) {
        return socialEventRepository.findByCustomerNameCustomerName(query.customerName());
    }

    @Override
    public List<SocialEvent> handle(GetSocialEventsByOrganizerIdQuery query) {
        return socialEventRepository.findByOrganizerId(query.organizerId());
    }
}