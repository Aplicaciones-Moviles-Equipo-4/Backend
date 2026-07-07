package com.eventify.platform.operation.application.internal.outboundservices.acl;

import com.eventify.platform.operation.domain.model.valueobjects.ProfileId;
import com.eventify.platform.profiles.interfaces.acl.ProfilesContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class responsible for interacting with the ProfilesContextFacade
 * to fetch profile-related information.
 */
@Service
public class ExternalProfileService {
    private final ProfilesContextFacade profilesContextFacade;

    /**
     * Constructs an ExternalProfileService with the specified ProfilesContextFacade.
     *
     * @param profilesContextFacade the facade used to interact with the profiles context
     */
    public ExternalProfileService(ProfilesContextFacade profilesContextFacade){
        this.profilesContextFacade = profilesContextFacade;
    }

    /**
     * Fetches the full name of a profile based on the given profile ID.
     *
     * @param profileId the ID of the profile whose full name is to be fetched
     * @return an Optional containing the full name if found, or an empty Optional if not
     */
    public Optional<String> fetchFullNameByProfileId(Long profileId) {
        var fullName = profilesContextFacade.fetchFullNameByProfileId(profileId);
        return fullName == null ? Optional.empty() : Optional.of(fullName);
    }
}
