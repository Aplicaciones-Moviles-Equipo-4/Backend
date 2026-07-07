package com.eventify.platform.planning.interfaces.rest.transform;

import com.eventify.platform.planning.domain.model.aggregates.SocialEvent;
import com.eventify.platform.planning.interfaces.rest.resources.SocialEventResource;

/**
 * Assembler to convert SocialEvent entity to SocialEventResource.
 */
public class SocialEventResourceFromEntityAssembler {
    /**
     * Converts a SocialEvent entity to a SocialEventResource.
     *
     * @param entity the entity to convert
     * @return the corresponding resource
     */
    public static SocialEventResource toResourceFromEntity(SocialEvent entity) {
        return new SocialEventResource(
                entity.getId(),
                entity.getTitle(),
                entity.getDate(),
                entity.getCustomerName(),
                entity.getPlace(),
                entity.getEventStatus(),
                entity.getOrganizerId()
        );
    }


}
