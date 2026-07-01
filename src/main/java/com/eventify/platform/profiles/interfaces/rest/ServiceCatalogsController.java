package com.eventify.platform.profiles.interfaces.rest;

import com.eventify.platform.profiles.domain.model.commands.CreateServiceCatalogCommand;
import com.eventify.platform.profiles.domain.model.commands.UpdateServiceCatalogCommand;
import com.eventify.platform.profiles.application.internal.outboundservices.ImageStorageService;
import com.eventify.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import com.eventify.platform.profiles.domain.model.queries.GetServiceCatalogByIdQuery;
import com.eventify.platform.profiles.domain.model.queries.GetServiceCatalogsByProfileIdQuery;
import com.eventify.platform.profiles.domain.model.valueobjects.ProfileType;
import com.eventify.platform.profiles.domain.services.ProfileQueryService;
import com.eventify.platform.profiles.domain.services.ServiceCatalogCommandService;
import com.eventify.platform.profiles.domain.services.ServiceCatalogQueryService;
import com.eventify.platform.profiles.interfaces.rest.resources.CreateServiceCatalogResource;
import com.eventify.platform.profiles.interfaces.rest.resources.ImageUploadResource;
import com.eventify.platform.profiles.interfaces.rest.resources.ServiceCatalogResource;
import com.eventify.platform.profiles.interfaces.rest.transform.CreateServiceCatalogCommandFromResourceAssembler;
import com.eventify.platform.profiles.interfaces.rest.transform.ServiceCatalogResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * REST controller for service catalog management.
 */
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/{profileId}/service-catalogs", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Service Catalogs", description = "Service Catalog Management Endpoints")
public class ServiceCatalogsController {

    private final ServiceCatalogCommandService commandService;
    private final ServiceCatalogQueryService queryService;
    private final ProfileQueryService profileQueryService;
    private final ImageStorageService imageStorageService;

    public ServiceCatalogsController(ServiceCatalogCommandService commandService, ServiceCatalogQueryService queryService,
                                     ProfileQueryService profileQueryService, ImageStorageService imageStorageService) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.profileQueryService = profileQueryService;
        this.imageStorageService = imageStorageService;
    }

    @Operation(summary = "Create Service Catalog")
    @PostMapping
    public ResponseEntity<?> createCatalog(@PathVariable Long profileId, @RequestBody CreateServiceCatalogResource resource) {
        CreateServiceCatalogCommand command = CreateServiceCatalogCommandFromResourceAssembler.toCommandFromResource(profileId, resource);
        var catalogId = commandService.handle(command);
        if (catalogId.isEmpty()) return ResponseEntity.badRequest().build();
        var catalog = queryService.handle(new GetServiceCatalogByIdQuery(catalogId.get()));
        if (catalog.isEmpty()) return ResponseEntity.badRequest().build();
        ServiceCatalogResource catalogResource = ServiceCatalogResourceFromEntityAssembler.toResourceFromEntity(catalog.get());
        return new ResponseEntity<>(catalogResource, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Catalogs by Profile")
    @GetMapping
    public ResponseEntity<List<ServiceCatalogResource>> getCatalogsByProfile(@PathVariable Long profileId) {
        var catalogs = queryService.handle(new GetServiceCatalogsByProfileIdQuery(profileId));
        var resources = catalogs.stream().map(ServiceCatalogResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Upload a service catalog cover image and return a public Cloudinary URL.
     * The frontend then stores the returned secureUrl in the catalog's imageUrl on create/update.
     */
    @Operation(summary = "Upload Service Catalog Image")
    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadCatalogImage(@PathVariable Long profileId, @RequestParam("file") MultipartFile file) {
        var profile = profileQueryService.handle(new GetProfileByIdQuery(profileId));
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        if (profile.get().getType() != ProfileType.ORGANIZER) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        var uploadedImage = imageStorageService.uploadAlbumImage(profileId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ImageUploadResource(
                uploadedImage.url(),
                uploadedImage.secureUrl(),
                uploadedImage.publicId()
        ));
    }

    @Operation(summary = "Get Service Catalog")
    @GetMapping("/{catalogId}")
    public ResponseEntity<?> getCatalog(@PathVariable Long profileId, @PathVariable Long catalogId) {
        var catalog = queryService.handle(new GetServiceCatalogByIdQuery(catalogId));
        return catalog.map(value ->
                        value.getProfile().getId().equals(profileId)
                                ? ResponseEntity.ok(ServiceCatalogResourceFromEntityAssembler.toResourceFromEntity(value))
                                : ResponseEntity.status(HttpStatus.FORBIDDEN).build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update Service Catalog")
    @PutMapping("/{catalogId}")
    public ResponseEntity<?> updateCatalog(@PathVariable Long profileId, @PathVariable Long catalogId, @RequestBody CreateServiceCatalogResource resource) {
        var command = new UpdateServiceCatalogCommand(
                catalogId,
                resource.title(),
                resource.description(),
                resource.category(),
                resource.priceFrom(),
                resource.priceTo(),
                resource.imageUrl()
        );
        commandService.handle(command);
        var catalog = queryService.handle(new GetServiceCatalogByIdQuery(catalogId));
        return catalog.map(value ->
                        value.getProfile().getId().equals(profileId)
                                ? ResponseEntity.ok(ServiceCatalogResourceFromEntityAssembler.toResourceFromEntity(value))
                                : ResponseEntity.status(HttpStatus.FORBIDDEN).build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete Service Catalog")
    @DeleteMapping("/{catalogId}")
    public ResponseEntity<?> deleteCatalog(@PathVariable Long profileId, @PathVariable Long catalogId) {
        var catalog = queryService.handle(new GetServiceCatalogByIdQuery(catalogId));
        if (catalog.isEmpty()) return ResponseEntity.notFound().build();
        if (!catalog.get().getProfile().getId().equals(profileId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        commandService.deleteById(catalogId);
        return ResponseEntity.ok().build();
    }
}