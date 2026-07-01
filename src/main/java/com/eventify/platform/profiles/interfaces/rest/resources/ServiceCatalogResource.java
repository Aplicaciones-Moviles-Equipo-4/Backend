package com.eventify.platform.profiles.interfaces.rest.resources;

/**
 * Service catalog resource representation.
 *
 * @param id catalog id
 * @param profileId owner profile id
 * @param title catalog title
 * @param description catalog description
 * @param category catalog category
 * @param priceFrom price range from
 * @param priceTo price range to
 */
public record ServiceCatalogResource(
        Long id,
        Long profileId,
        String title,
        String description,
        String category,
        Double priceFrom,
        Double priceTo,
        String imageUrl) {
}