package com.eventify.platform.planning.domain.model.queries;

/**
 * Query to retrieve the social events owned by a given organizer (profileId).
 *
 * @param organizerId the id of the organizer that owns the events
 */
public record GetSocialEventsByOrganizerIdQuery(Long organizerId) {
}
