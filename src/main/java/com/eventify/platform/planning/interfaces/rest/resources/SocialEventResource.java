package com.eventify.platform.planning.interfaces.rest.resources;

import java.time.LocalDate;

/**
 * Resource representing a social event response.
 */

public record SocialEventResource(Long id,
                                  String title,
                                  LocalDate date,
                                  String customerName,
                                  String place,
                                  String status,
                                  Long organizerId
                                  ) {
}
