package com.eventify.platform.profiles.application.internal.commandservices;

import com.eventify.platform.profiles.domain.model.aggregates.Profile;
import com.eventify.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.eventify.platform.profiles.domain.model.commands.UpdateProfileCommand;
import com.eventify.platform.profiles.domain.services.ProfileCommandService;
import com.eventify.platform.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * ProfileCommandServiceImpl
 *
 * @summary
 * Implementation of ProfileCommandService.
 * Handles all commands related to Profile aggregate.
 *
 * @since 1.0
 */
@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {

    private final ProfileRepository profileRepository;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * Handle CreateProfileCommand
     *
     * @param command CreateProfileCommand with profile data
     * @return Optional containing the profile ID if successful
     * @throws IllegalArgumentException if email already exists
     */
    @Override
    public Optional<Long> handle(CreateProfileCommand command) {
        if (profileRepository.existsByEmail_Address(command.email())) {
            throw new IllegalArgumentException("Profile with email %s already exists".formatted(command.email()));
        }

        var profile = new Profile(command);
        try {
            profileRepository.save(profile);
            return Optional.of(profile.getId());
        } catch (Exception exception) {
            throw new IllegalArgumentException("Error while saving profile: %s".formatted(exception.getMessage()));
        }
    }

    @Override
    public Optional<Long> handle(UpdateProfileCommand command) {
        var profile = profileRepository.findById(command.profileId());
        if (profile.isEmpty()) return Optional.empty();

        var existingByEmail = profileRepository.findByEmail_Address(command.email());
        if (existingByEmail.isPresent() && !existingByEmail.get().getId().equals(command.profileId())) {
            throw new IllegalArgumentException("Profile with email %s already exists".formatted(command.email()));
        }

        profile.get().updateInformation(
                command.firstName(),
                command.lastName(),
                command.email(),
                command.street(),
                command.number(),
                command.city(),
                command.postalCode(),
                command.country(),
                command.profileImageUrl()
        );
        profileRepository.save(profile.get());
        return Optional.of(profile.get().getId());
    }
}
