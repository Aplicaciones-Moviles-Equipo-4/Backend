package com.eventify.platform.profiles.domain.model.commands;

/**
 * Command to create a new service catalog entry for a profile.
 *
 * @param profileId owner profile ID
 * @param title catalog title
 * @param description catalog description
 * @param category catalog category
 * @param priceFrom lower bound of price range
 * @param priceTo upper bound of price range
 * @param imageUrl optional public cover image URL
 */
public record CreateServiceCatalogCommand(
        Long profileId,
        String title,
        String description,
        String category,
        Double priceFrom,
        Double priceTo,
        String imageUrl) {

    public CreateServiceCatalogCommand {
        if (profileId == null || profileId <= 0) {
            throw new IllegalArgumentException("Profile id is required");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Category is required");
        }
        if (priceFrom == null || priceTo == null) {
            throw new IllegalArgumentException("Price range is required");
        }
        if (priceFrom < 0 || priceTo < 0 || priceFrom > priceTo) {
            throw new IllegalArgumentException("Invalid price range");
        }
    }
}