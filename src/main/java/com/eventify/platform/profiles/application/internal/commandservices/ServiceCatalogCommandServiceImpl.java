package com.eventify.platform.profiles.application.internal.commandservices;

import com.eventify.platform.profiles.domain.model.aggregates.Profile;
import com.eventify.platform.profiles.domain.model.aggregates.ServiceCatalog;
import com.eventify.platform.profiles.domain.model.commands.CreateServiceCatalogCommand;
import com.eventify.platform.profiles.domain.model.commands.UpdateServiceCatalogCommand;
import com.eventify.platform.profiles.domain.model.valueobjects.ProfileType;
import com.eventify.platform.profiles.domain.services.ServiceCatalogCommandService;
import com.eventify.platform.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import com.eventify.platform.profiles.infrastructure.persistence.jpa.repositories.ServiceCatalogRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of ServiceCatalog command service.
 */
@Service
public class ServiceCatalogCommandServiceImpl implements ServiceCatalogCommandService {

    private final ServiceCatalogRepository serviceCatalogRepository;
    private final ProfileRepository profileRepository;

    public ServiceCatalogCommandServiceImpl(ServiceCatalogRepository serviceCatalogRepository, ProfileRepository profileRepository) {
        this.serviceCatalogRepository = serviceCatalogRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Long> handle(CreateServiceCatalogCommand command) {
        var profileOpt = profileRepository.findById(command.profileId());
        if (profileOpt.isEmpty()) return Optional.empty();
        Profile profile = profileOpt.get();
        if (profile.getType() != ProfileType.ORGANIZER) return Optional.empty();
        var catalog = new ServiceCatalog(profile, command.title(), command.description(), command.category(), command.priceFrom(), command.priceTo(), command.imageUrl());
        serviceCatalogRepository.save(catalog);
        return Optional.of(catalog.getId());
    }

    @Override
    public void handle(UpdateServiceCatalogCommand command) {
        var catalogOpt = serviceCatalogRepository.findById(command.serviceCatalogId());
        if (catalogOpt.isEmpty()) return;
        var catalog = catalogOpt.get();
        catalog.update(command.title(), command.description(), command.category(), command.priceFrom(), command.priceTo(), command.imageUrl());
        serviceCatalogRepository.save(catalog);
    }

    @Override
    public void deleteById(Long serviceCatalogId) {
        serviceCatalogRepository.deleteById(serviceCatalogId);
    }
}