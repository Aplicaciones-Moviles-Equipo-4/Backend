package com.eventify.platform.profiles.interfaces.rest;

import com.eventify.platform.profiles.application.internal.outboundservices.ImageStorageService;
import com.eventify.platform.profiles.domain.model.commands.UpdateProfileCommand;
import com.eventify.platform.profiles.domain.model.queries.GetAllProfilesQuery;
import com.eventify.platform.profiles.domain.model.queries.GetProfileByEmailQuery;
import com.eventify.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import com.eventify.platform.profiles.domain.services.ProfileCommandService;
import com.eventify.platform.profiles.domain.services.ProfileQueryService;
import com.eventify.platform.profiles.interfaces.rest.resources.CreateProfileResource;
import com.eventify.platform.profiles.interfaces.rest.resources.ImageUploadResource;
import com.eventify.platform.profiles.interfaces.rest.resources.ProfileResource;
import com.eventify.platform.profiles.interfaces.rest.resources.UpdateProfileResource;
import com.eventify.platform.profiles.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import com.eventify.platform.profiles.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import com.eventify.platform.profiles.interfaces.rest.transform.UpdateProfileCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ProfilesController
 *
 * @summary
 * REST controller for Profile aggregate.
 * Provides endpoints for profile management operations.
 *
 * @since 1.0
 */
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profile Management Endpoints")
public class ProfilesController {

    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;
    private final ImageStorageService imageStorageService;

    public ProfilesController(ProfileCommandService profileCommandService, ProfileQueryService profileQueryService,
                              ImageStorageService imageStorageService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
        this.imageStorageService = imageStorageService;
    }

    /**
     * Create a new profile
     *
     * @param createProfileResource CreateProfileResource with profile data
     * @return ProfileResource if successful, error message otherwise
     */
    @Operation(summary = "Create New Profile",
            description = "Creates a new profile with the provided data. Returns the created profile resource.")
    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody CreateProfileResource createProfileResource) {
        var createProfileCommand = CreateProfileCommandFromResourceAssembler.toCommandFromResource(createProfileResource);
        var profileId = profileCommandService.handle(createProfileCommand);
        if (profileId.isEmpty()) return ResponseEntity.badRequest().build();
        var getProfileByIdQuery = new GetProfileByIdQuery(profileId.get());
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty()) return ResponseEntity.badRequest().build();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return new ResponseEntity<>(profileResource, HttpStatus.CREATED);
    }

    /**
     * Get profile by ID
     *
     * @param profileId Profile ID
     * @return ProfileResource if found, 404 otherwise
     */
    @GetMapping("/{profileId}")
    @Operation(summary = "Get Profile by ID", description = "Retrieves a profile by its unique ID. Returns the profile resource if found.")
    public ResponseEntity<ProfileResource> getProfileById(@PathVariable Long profileId) {
        var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    /**
     * Get profile by email
     *
     * @param email Email address
     * @return ProfileResource if found, 404 otherwise
     */
    @GetMapping("/email/{email}")
    @Operation(summary = "Get Profile by Email",
            description = "Retrieves a profile by its email address. Returns the profile resource if found.")
    public ResponseEntity<ProfileResource> getProfileByEmail(@PathVariable String email) {
        var getProfileByEmailQuery = new GetProfileByEmailQuery(email);
        var profile = profileQueryService.handle(getProfileByEmailQuery);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    /**
     * Get all profiles
     *
     * @return List of ProfileResource
     */
    @Operation(summary = "Get All Profiles",
            description = "Retrieves a list of all profiles. Returns a list of profile resources.")
    @GetMapping
    public ResponseEntity<List<ProfileResource>> getAllProfiles() {
        var getAllProfilesQuery = new GetAllProfilesQuery();
        var profiles = profileQueryService.handle(getAllProfilesQuery);
        var profileResources = profiles.stream()
                .map(ProfileResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(profileResources);
    }

    @PutMapping("/{profileId}")
    @Operation(summary = "Update Profile", description = "Updates profile contact, address and image URL.")
    public ResponseEntity<ProfileResource> updateProfile(@PathVariable Long profileId,
                                                         @RequestBody UpdateProfileResource updateProfileResource) {
        var updateProfileCommand = UpdateProfileCommandFromResourceAssembler.toCommandFromResource(profileId, updateProfileResource);
        var updatedProfileId = profileCommandService.handle(updateProfileCommand);
        if (updatedProfileId.isEmpty()) return ResponseEntity.notFound().build();
        var profile = profileQueryService.handle(new GetProfileByIdQuery(updatedProfileId.get()));
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get()));
    }

    @PostMapping(value = "/{profileId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload Profile Image", description = "Uploads a profile image and stores its Cloudinary URL in the profile.")
    public ResponseEntity<?> uploadProfileImage(@PathVariable Long profileId, @RequestParam("file") MultipartFile file) {
        var profile = profileQueryService.handle(new GetProfileByIdQuery(profileId));
        if (profile.isEmpty()) return ResponseEntity.notFound().build();

        var uploadedImage = imageStorageService.uploadProfileImage(profileId, file);
        var updateProfileCommand = new UpdateProfileCommand(
                profileId,
                profile.get().getName().firstName(),
                profile.get().getName().lastName(),
                profile.get().getEmail().address(),
                profile.get().getAddress().street(),
                profile.get().getAddress().number(),
                profile.get().getAddress().city(),
                profile.get().getAddress().postalCode(),
                profile.get().getAddress().country(),
                uploadedImage.secureUrl()
        );
        profileCommandService.handle(updateProfileCommand);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ImageUploadResource(
                uploadedImage.url(),
                uploadedImage.secureUrl(),
                uploadedImage.publicId()
        ));
    }
}
