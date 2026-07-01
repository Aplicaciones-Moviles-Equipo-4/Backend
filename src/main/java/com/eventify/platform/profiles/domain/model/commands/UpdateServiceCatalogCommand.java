package com.eventify.platform.profiles.domain.model.commands;

/**
 * Command to update an existing service catalog entry.
 *
 * @param serviceCatalogId catalog identifier
 * @param title new title
 * @param description new description
 * @param category new category
 * @param priceFrom lower bound of price range
 * @param priceTo upper bound of price range
 */
public record UpdateServiceCatalogCommand(
        Long serviceCatalogId,
        String title,
        String description,
        String category,
        Double priceFrom,
        Double priceTo,
        String imageUrl) {

    public UpdateServiceCatalogCommand {
        if (serviceCatalogId == null || serviceCatalogId <= 0) {
            throw new IllegalArgumentException("Catalog id is required");
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