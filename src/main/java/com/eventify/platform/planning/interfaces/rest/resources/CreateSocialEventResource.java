package com.eventify.platform.planning.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateSocialEventResource(
        String title,
        String place,
        LocalDate date,
        String customerName,
        String status,
        Long organizerId
) {
    public CreateSocialEventResource {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (place == null || place.isBlank()) {
            throw new IllegalArgumentException("Place cannot be null or empty");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
    }
}
