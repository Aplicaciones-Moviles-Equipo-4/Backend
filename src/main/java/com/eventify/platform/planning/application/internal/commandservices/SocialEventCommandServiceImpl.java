package com.eventify.platform.planning.application.internal.commandservices;

import com.eventify.platform.planning.domain.model.aggregates.SocialEvent;
import com.eventify.platform.planning.domain.model.commands.CreateNewSocialEventCommand;
import com.eventify.platform.planning.domain.model.commands.DeleteSocialEventCommand;
import com.eventify.platform.planning.domain.model.commands.DeleteSocialEventsCommand;
import com.eventify.platform.planning.domain.model.commands.UpdateSocialEventCommand;
import com.eventify.platform.planning.domain.model.valueobjects.*;
import com.eventify.platform.planning.domain.services.SocialEventCommandService;
import com.eventify.platform.planning.infrastructure.persistence.jpa.repositories.SocialEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementation of SocialEventCommandService.
 */
@Service
@Transactional
public class SocialEventCommandServiceImpl implements SocialEventCommandService {

    private final SocialEventRepository socialEventRepository;

    public SocialEventCommandServiceImpl(SocialEventRepository socialEventRepository) {
        this.socialEventRepository = socialEventRepository;
    }

    @Override
    public Optional<SocialEvent> handle(CreateNewSocialEventCommand command) {
        // Check if event with same title already exists
        if (socialEventRepository.existsByTitleTitle(command.title())) {
            throw new IllegalArgumentException("Social event with title '" + command.title() + "' already exists");
        }

        try {
            // Create value objects
            var title = new SocialEventTitle(command.title());
            var place = new Place(command.place());
            var date = new SocialEventDate(command.date());
            var customerName = new CustomerName(command.customerName());
            var status = new SocialEventStatus(command.status());

            // Create and save the aggregate
            var socialEvent = new SocialEvent(title, place, date, customerName, status, command.organizerId());
            var savedSocialEvent = socialEventRepository.save(socialEvent);

            return Optional.of(savedSocialEvent);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error creating social event: " + e.getMessage());
        }
    }

    @Override
    public Optional<SocialEvent> handle(UpdateSocialEventCommand command) {
        // Find existing event
        var existingEvent = socialEventRepository.findById(command.socialEventId());
        if (existingEvent.isEmpty()) {
            throw new IllegalArgumentException("Social event with ID " + command.socialEventId() + " not found");
        }

        try {
            var socialEvent = existingEvent.get();

            // Update details using the aggregate method
            socialEvent.updateDetails(command.title(), command.date(), command.place(), command.customerName() );
            socialEvent.updateStatus(command.status());

            var updatedSocialEvent = socialEventRepository.save(socialEvent);
            return Optional.of(updatedSocialEvent);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error updating social event: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteSocialEventCommand command) {
        if (!socialEventRepository.existsById(command.socialEventId())) {
            throw new IllegalArgumentException("Social event with ID " + command.socialEventId() + " not found");
        }

        socialEventRepository.deleteById(command.socialEventId());
    }

    @Override
    public void handle(DeleteSocialEventsCommand command) {
        // Verify all events exist before deleting any
        for (Long eventId : command.socialEventIds()) {
            if (!socialEventRepository.existsById(eventId)) {
                throw new IllegalArgumentException("Social event with ID " + eventId + " not found");
            }
        }

        // Delete events one by one for now
        for (Long eventId : command.socialEventIds()) {
            socialEventRepository.deleteById(eventId);
        }
    }
}