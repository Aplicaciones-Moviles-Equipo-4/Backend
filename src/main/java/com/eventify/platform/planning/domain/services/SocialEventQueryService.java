package com.eventify.platform.planning.domain.services;
import com.eventify.platform.planning.domain.model.aggregates.SocialEvent;
import com.eventify.platform.planning.domain.model.queries.GetAllSocialEventQuery;
import com.eventify.platform.planning.domain.model.queries.GetSocialEventByStatusQuery;
import com.eventify.platform.planning.domain.model.queries.GetSocialEventByTitleQuery;
import com.eventify.platform.planning.domain.model.queries.GetSocialEventsByOrganizerQuery;
import com.eventify.platform.planning.domain.model.queries.GetSocialEventsByOrganizerIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling social event queries.
 */


public interface SocialEventQueryService {

    /**
     * Retrieves all social events.
     *
     * @param query the query to get all events
     * @return list of all social events
     */
    List<SocialEvent> handle(GetAllSocialEventQuery query);

    /**
     * Retrieves social events by status.
     *
     * @param query the query containing the status filter
     * @return list of social events with the specified status
     */
    List<SocialEvent> handle(GetSocialEventByStatusQuery query);

    /**
     * Retrieves social events by title.
     *
     * @param query the query containing the title filter
     * @return list of social events with the specified title
     */
    List<SocialEvent> handle(GetSocialEventByTitleQuery query);

    /**
     * Retrieves social events by organizer (customer name).
     *
     * @param query the query containing the customer name filter
     * @return list of social events organized by the specified customer
     */
    List<SocialEvent> handle(GetSocialEventsByOrganizerQuery query);

    /**
     * Retrieves social events owned by a given organizer (profileId).
     *
     * @param query the query containing the organizer id filter
     * @return list of social events owned by the specified organizer
     */
    List<SocialEvent> handle(GetSocialEventsByOrganizerIdQuery query);
}



