package com.eventify.platform.profiles.domain.services;

import com.eventify.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.eventify.platform.profiles.domain.model.commands.UpdateProfileCommand;

import java.util.Optional;

/**
 * ProfileCommandService
 *
 * @summary
 * Service interface for handling Profile commands.
 * Defines all command operations for Profile aggregate.
 *
 * @since 1.0
 */
public interface ProfileCommandService {

    /**
     * Handle CreateProfileCommand
     *
     * @param command CreateProfileCommand to handle
     * @return Optional containing the created profile ID if successful
     */
    Optional<Long> handle(CreateProfileCommand command);

    Optional<Long> handle(UpdateProfileCommand command);
}
