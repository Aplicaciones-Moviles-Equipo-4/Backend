package com.eventify.platform.operation.interfaces.rest;

import com.eventify.platform.operation.domain.model.commands.UpdateReviewCommand;
import com.eventify.platform.operation.domain.model.queries.ExistByProfileIdQuery;
import com.eventify.platform.operation.domain.model.queries.GetAllReviewsQuery;
import com.eventify.platform.operation.domain.model.queries.GetReviewByIdQuery;
import com.eventify.platform.operation.domain.model.queries.GetReviewByProfileIdQuery;
import com.eventify.platform.operation.domain.model.valueobjects.ProfileId;
import com.eventify.platform.operation.domain.services.ReviewCommandService;
import com.eventify.platform.operation.domain.services.ReviewQueryService;
import com.eventify.platform.operation.interfaces.rest.resources.CreateReviewResource;
import com.eventify.platform.operation.interfaces.rest.resources.ReviewResource;
import com.eventify.platform.operation.interfaces.rest.resources.UpdateReviewResource;
import com.eventify.platform.operation.interfaces.rest.transform.CreateReviewCommandFromResourceAssembler;
import com.eventify.platform.operation.interfaces.rest.transform.ReviewResourceFromEntityAssembler;
import com.eventify.platform.operation.interfaces.rest.transform.UpdateReviewCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value="/api/v1/reviews", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Reviews", description = "Available Review Endpoints")
public class ReviewsController {
    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    public ReviewsController(ReviewCommandService reviewCommandService, ReviewQueryService reviewQueryService) {
        this.reviewCommandService = reviewCommandService;
        this.reviewQueryService = reviewQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new review", description = "Crate a new review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review created"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input"),
            @ApiResponse(responseCode = "404", description = "Review not found")})
    public ResponseEntity<ReviewResource> createReview(@RequestBody CreateReviewResource resource) {
        var createReviewCommand = CreateReviewCommandFromResourceAssembler.toCommandFromResource(resource);
        var reviewId = reviewCommandService.handle(createReviewCommand);
        if( reviewId == null || reviewId == 0L) return ResponseEntity.badRequest().build();
        var getReviewByIdQuery = new GetReviewByIdQuery(reviewId);
        var review = reviewQueryService.handle(getReviewByIdQuery);
        if (review.isEmpty()) return ResponseEntity.notFound().build();
        var reviewEntity = review.get();
        var reviewResource = ReviewResourceFromEntityAssembler.toResourceFromEntity(reviewEntity);
        return new ResponseEntity<>(reviewResource, HttpStatus.CREATED);
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "Get review by id", description = "Get review by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review found"),
            @ApiResponse(responseCode = "404", description = "Review not found")})
    public ResponseEntity<ReviewResource> getReviewById(@PathVariable Long reviewId){
        var getReviewByIdQuery = new GetReviewByIdQuery(reviewId);
        var review = reviewQueryService.handle(getReviewByIdQuery);
        if (review.isEmpty()) return ResponseEntity.notFound().build();
        var reviewEntity = review.get();
        var reviewResource = ReviewResourceFromEntityAssembler.toResourceFromEntity(reviewEntity);
        return ResponseEntity.ok(reviewResource);
    }

    @GetMapping("/profile/{profileId}")
    @Operation(summary = "Get reviews by profile id", description = "Get reviews by profile id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviews found"),
            @ApiResponse(responseCode = "404", description = "No reviews found")})
    public ResponseEntity<List<ReviewResource>> getReviewsByProfileId(@PathVariable Long profileId) {
        var assignedProfileId = new ProfileId(profileId);
        var existByProfileIdQuery = new ExistByProfileIdQuery(assignedProfileId);
        if (!reviewQueryService.handle(existByProfileIdQuery)) return ResponseEntity.notFound().build();
        var getReviewByProfileIdQuery = new GetReviewByProfileIdQuery(assignedProfileId);
        var reviews = reviewQueryService.handle(getReviewByProfileIdQuery);
        var reviewResources = reviews.stream().map(ReviewResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(reviewResources);
    }


    @GetMapping
    @Operation(summary = "Get all reviews", description = "Get all reviews")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviews found"),
            @ApiResponse(responseCode = "404", description = "No reviews found")})
    public ResponseEntity<List<ReviewResource>> getAllReviews(){
        var reviews = reviewQueryService.handle(new GetAllReviewsQuery());
        if (reviews.isEmpty()) return ResponseEntity.notFound().build();
        var reviewResources = reviews.stream()
                .map(ReviewResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(reviewResources);
    }

    @PutMapping("/{reviewId}")
    @Operation(summary = "Update a review", description = "Update a review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review updated"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input"),
            @ApiResponse(responseCode = "404", description = "Review not found")})
    public ResponseEntity<ReviewResource> updateReview(@PathVariable Long reviewId, @RequestBody UpdateReviewResource resource) {
        var updateReviewCommand = UpdateReviewCommandFromResourceAssembler.toCommandFromResource(reviewId, resource);
        var updatedReview = reviewCommandService.handle(updateReviewCommand);
        if (updatedReview.isEmpty()) return ResponseEntity.notFound().build();
        var updatedReviewEntity = updatedReview.get();
        var updatedReviewResource = ReviewResourceFromEntityAssembler.toResourceFromEntity(updatedReviewEntity);
        return ResponseEntity.ok(updatedReviewResource);
    }

}
