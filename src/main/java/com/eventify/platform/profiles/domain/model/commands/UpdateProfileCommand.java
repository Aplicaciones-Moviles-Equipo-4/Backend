package com.eventify.platform.profiles.domain.model.commands;

public record UpdateProfileCommand(
        Long profileId,
        String firstName,
        String lastName,
        String email,
        String street,
        String number,
        String city,
        String postalCode,
        String country,
        String profileImageUrl
) {
    public UpdateProfileCommand {
        if (profileId == null || profileId <= 0) {
            throw new IllegalArgumentException("Profile id is required");
        }
    }
}
