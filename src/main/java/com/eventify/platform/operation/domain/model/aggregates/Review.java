package com.eventify.platform.operation.domain.model.aggregates;

import com.eventify.platform.operation.domain.model.commands.CreateReviewCommand;
import com.eventify.platform.operation.domain.model.valueobjects.ProfileId;
import com.eventify.platform.operation.domain.model.valueobjects.ReviewRating;
import com.eventify.platform.operation.domain.model.valueobjects.SocialEventId;
import com.eventify.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.Date;
/**
 * Represents a review entity in the system.
 * A review is associated with a profile and a social event.
 */
@Getter
@Entity
public class Review extends AuditableAbstractAggregateRoot<Review> {

    private String content;
    private Integer rating;
    private String fullName;
    private Date socialEventDate;

    @Embedded
    private ProfileId profileId;

    @Embedded
    private SocialEventId socialEventId;

    /**
     * Default constructor for JPA.
     */
    public Review () {}

    /**
     * Constructs a Review entity using the provided CreateReviewCommand.
     *
     * @param command the command containing the data to create a review
     */
    public Review(CreateReviewCommand command){
        this();
        this.content = command.content();
        this.rating = command.rating();
        this.fullName = command.fullName();
        this.socialEventDate = command.socialEventDate();
        this.profileId = new ProfileId(command.profileId());
        this.socialEventId = new SocialEventId(command.socialEventId());
    }

    /**
     * Retrieves the profile ID associated with this review.
     *
     * @return the profile ID
     */
    public Long getProfileId() {
        return this.profileId.profileId();
    }

    /**
     * Retrieves the social event ID associated with this review.
     *
     * @return the social event ID
     */
    public Long getSocialEventId() {
        return this.socialEventId.socialEventId();
    }

    /**
     * Updates the review information with the provided data.
     *
     * @param content         the updated content of the review
     * @param fullName        the updated full name of the reviewer
     * @param socialEventDate the updated date of the social event
     * @param rating          the updated rating of the review
     * @param profileId       the updated profile ID
     * @return the updated Review instance
     */
    public Review updateInformation(String content, String fullName, Date socialEventDate, Integer rating, Long profileId){
        if (content != null && !content.isBlank()) {
            this.content = content;
        }
        if (fullName != null && !fullName.isBlank()) {
            this.fullName = fullName;
        }
        if (socialEventDate != null) {
            this.socialEventDate = socialEventDate;
        }
        if (rating != null && rating >= 1 && rating <= 5) {
            this.rating = rating;
        }
        if (profileId != null) {
            this.profileId = new ProfileId(profileId);
        }
        return this;
    }


}
