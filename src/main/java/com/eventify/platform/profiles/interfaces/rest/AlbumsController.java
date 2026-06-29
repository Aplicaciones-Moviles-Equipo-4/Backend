package com.eventify.platform.profiles.interfaces.rest;

import com.eventify.platform.profiles.domain.model.commands.CreateAlbumCommand;
import com.eventify.platform.profiles.domain.model.commands.UpdateAlbumCommand;
import com.eventify.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import com.eventify.platform.profiles.domain.model.valueobjects.ProfileType;
import com.eventify.platform.profiles.domain.services.ProfileQueryService;
import com.eventify.platform.profiles.application.internal.outboundservices.ImageStorageService;
import com.eventify.platform.profiles.domain.model.queries.GetAlbumByIdQuery;
import com.eventify.platform.profiles.domain.model.queries.GetAlbumsByProfileIdQuery;
import com.eventify.platform.profiles.domain.services.AlbumCommandService;
import com.eventify.platform.profiles.domain.services.AlbumQueryService;
import com.eventify.platform.profiles.interfaces.rest.resources.AlbumResource;
import com.eventify.platform.profiles.interfaces.rest.resources.CreateAlbumResource;
import com.eventify.platform.profiles.interfaces.rest.resources.ImageUploadResource;
import com.eventify.platform.profiles.interfaces.rest.transform.AlbumResourceFromEntityAssembler;
import com.eventify.platform.profiles.interfaces.rest.transform.CreateAlbumCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * REST controller for album management.
 */
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/{profileId}/albums", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Albums", description = "Album Management Endpoints")
public class AlbumsController {

    private final AlbumCommandService albumCommandService;
    private final AlbumQueryService albumQueryService;
    private final ProfileQueryService profileQueryService;
    private final ImageStorageService imageStorageService;

    public AlbumsController(AlbumCommandService albumCommandService, AlbumQueryService albumQueryService,
                            ProfileQueryService profileQueryService, ImageStorageService imageStorageService) {
        this.albumCommandService = albumCommandService;
        this.albumQueryService = albumQueryService;
        this.profileQueryService = profileQueryService;
        this.imageStorageService = imageStorageService;
    }

    /**
     * Create an album for an organizer profile.
     */
    @Operation(summary = "Create Album")
    @PostMapping
    public ResponseEntity<?> createAlbum(@PathVariable Long profileId, @RequestBody CreateAlbumResource resource) {
        CreateAlbumCommand command = CreateAlbumCommandFromResourceAssembler.toCommandFromResource(profileId, resource);
        var albumId = albumCommandService.handle(command);
        if (albumId.isEmpty()) return ResponseEntity.badRequest().build();
        var album = albumQueryService.handle(new GetAlbumByIdQuery(albumId.get()));
        if (album.isEmpty()) return ResponseEntity.badRequest().build();
        AlbumResource albumResource = AlbumResourceFromEntityAssembler.toResourceFromEntity(album.get());
        return new ResponseEntity<>(albumResource, HttpStatus.CREATED);
    }

    /**
     * Get albums by profile id.
     */
    @Operation(summary = "Get Albums by Profile")
    @GetMapping
    public ResponseEntity<List<AlbumResource>> getAlbumsByProfile(@PathVariable Long profileId) {
        var albums = albumQueryService.handle(new GetAlbumsByProfileIdQuery(profileId));
        var resources = albums.stream().map(AlbumResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Upload an album image and return a public Cloudinary URL.
     */
    @Operation(summary = "Upload Album Image")
    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAlbumImage(@PathVariable Long profileId, @RequestParam("file") MultipartFile file) {
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

    /**
     * Get a single album by id.
     */
    @Operation(summary = "Get Album")
    @GetMapping("/{albumId}")
    public ResponseEntity<?> getAlbum(@PathVariable Long profileId, @PathVariable Long albumId) {
        var album = albumQueryService.handle(new GetAlbumByIdQuery(albumId));
        return album.map(value ->
                        value.getProfile().getId().equals(profileId)
                                ? ResponseEntity.ok(AlbumResourceFromEntityAssembler.toResourceFromEntity(value))
                                : ResponseEntity.status(HttpStatus.FORBIDDEN).build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Update an album.
     */
    @Operation(summary = "Update Album")
    @PutMapping("/{albumId}")
    public ResponseEntity<?> updateAlbum(@PathVariable Long profileId, @PathVariable Long albumId,
                                         @RequestBody CreateAlbumResource resource) {
        var command = new UpdateAlbumCommand(
                albumId,
                resource.title(),
                resource.description(),
                resource.photos()
        );
        albumCommandService.handle(command);
        var album = albumQueryService.handle(new GetAlbumByIdQuery(albumId));
        return album.map(value ->
                        value.getProfile().getId().equals(profileId)
                                ? ResponseEntity.ok(AlbumResourceFromEntityAssembler.toResourceFromEntity(value))
                                : ResponseEntity.status(HttpStatus.FORBIDDEN).build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Delete an album.
     */
    @Operation(summary = "Delete Album")
    @DeleteMapping("/{albumId}")
    public ResponseEntity<?> deleteAlbum(@PathVariable Long profileId, @PathVariable Long albumId) {
        var album = albumQueryService.handle(new GetAlbumByIdQuery(albumId));
        if (album.isEmpty()) return ResponseEntity.notFound().build();
        if (!album.get().getProfile().getId().equals(profileId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        albumCommandService.deleteById(albumId);
        return ResponseEntity.ok().build();
    }
}
