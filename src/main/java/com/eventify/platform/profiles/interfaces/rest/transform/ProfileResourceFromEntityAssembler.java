package com.eventify.platform.profiles.interfaces.rest.transform;

import com.eventify.platform.profiles.domain.model.aggregates.Profile;
import com.eventify.platform.profiles.interfaces.rest.resources.ProfileResource;

/**
 * ProfileResourceFromEntityAssembler
 *
 * @summary
 * Assembler to convert Profile entity to ProfileResource.
 * Transforms domain entity into REST resource.
 *
 * @since 1.0
 */
public class ProfileResourceFromEntityAssembler {

    /**
     * Convert Profile entity to ProfileResource
     *
     * @param entity Profile entity to convert
     * @return ProfileResource with the entity data
     */
    public static ProfileResource toResourceFromEntity(Profile entity) {
        return new ProfileResource(
                entity.getId(),
                entity.getName().firstName(),
                entity.getName().lastName(),
                entity.getFullName(),
                entity.getEmail().address(),
                entity.getAddress().street(),
                entity.getAddress().number(),
                entity.getAddress().city(),
                entity.getAddress().postalCode(),
                entity.getAddress().country(),
                entity.getStreetAddress(),
                entity.getType().name(),
                entity.getProfileImageUrl()
        );
    }
}
