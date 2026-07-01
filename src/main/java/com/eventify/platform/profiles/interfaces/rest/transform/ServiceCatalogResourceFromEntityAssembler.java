package com.eventify.platform.profiles.interfaces.rest.transform;

import com.eventify.platform.profiles.domain.model.aggregates.ServiceCatalog;
import com.eventify.platform.profiles.interfaces.rest.resources.ServiceCatalogResource;

/**
 * Assembler to convert ServiceCatalog entity to ServiceCatalogResource.
 */
public class ServiceCatalogResourceFromEntityAssembler {

    public static ServiceCatalogResource toResourceFromEntity(ServiceCatalog entity) {
        return new ServiceCatalogResource(
                entity.getId(),
                entity.getProfile().getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getCategory(),
                entity.getPriceFrom(),
                entity.getPriceTo(),
                entity.getImageUrl()
        );
    }
}