package com.eventify.platform.profiles.interfaces.rest.resources;

/**
 * ProfileResource
 *
 * @summary
 * Resource representing a profile.
 * Contains all profile information for REST API responses.
 *
 * @param id Profile ID
 * @param firstName First name
 * @param lastName Last name
 * @param fullName Full name
 * @param email Email address
 * @param street Street address
 * @param number Street number
 * @param city City
 * @param postalCode Postal code
 * @param country Country
 * @param fullAddress Complete address as string
 * @param type Profile type
 * @since 1.0
 */
public record ProfileResource(
        Long id,
        String firstName,
        String lastName,
        String fullName,
        String email,
        String street,
        String number,
        String city,
        String postalCode,
        String country,
        String fullAddress,
        String type,
        String profileImageUrl
) {
}
