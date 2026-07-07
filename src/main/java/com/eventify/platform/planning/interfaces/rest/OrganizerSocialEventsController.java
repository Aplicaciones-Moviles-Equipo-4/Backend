package com.eventify.platform.planning.interfaces.rest;

import com.eventify.platform.planning.domain.model.queries.GetSocialEventsByOrganizerIdQuery;
import com.eventify.platform.planning.domain.services.SocialEventQueryService;
import com.eventify.platform.planning.interfaces.rest.resources.SocialEventResource;
import com.eventify.platform.planning.interfaces.rest.transform.SocialEventResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller exposing the social events owned by a given organizer (profileId).
 */
@RestController
@RequestMapping(value = "/api/v1/organizers/{organizerId}/social-events", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Organizer Social Events", description = "Social events owned by an organizer")
public class OrganizerSocialEventsController {

    private final SocialEventQueryService socialEventQueryService;

    public OrganizerSocialEventsController(SocialEventQueryService socialEventQueryService) {
        this.socialEventQueryService = socialEventQueryService;
    }

    @GetMapping
    @Operation(summary = "Get social events of an organizer",
            description = "Returns the social events created by the organizer with the given profileId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Social events retrieved successfully")
    })
    public ResponseEntity<List<SocialEventResource>> getSocialEventsForOrganizer(@PathVariable Long organizerId) {
        var query = new GetSocialEventsByOrganizerIdQuery(organizerId);
        var socialEvents = socialEventQueryService.handle(query);
        var resources = socialEvents.stream()
                .map(SocialEventResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
