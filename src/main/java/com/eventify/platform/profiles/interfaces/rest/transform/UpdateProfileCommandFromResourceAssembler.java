package com.eventify.platform.profiles.interfaces.rest.transform;

import com.eventify.platform.profiles.domain.model.commands.UpdateProfileCommand;
import com.eventify.platform.profiles.interfaces.rest.resources.UpdateProfileResource;

public class UpdateProfileCommandFromResourceAssembler {

    public static UpdateProfileCommand toCommandFromResource(Long profileId, UpdateProfileResource resource) {
        return new UpdateProfileCommand(
                profileId,
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.street(),
                resource.number(),
                resource.city(),
                resource.postalCode(),
                resource.country(),
                resource.profileImageUrl()
        );
    }
}
