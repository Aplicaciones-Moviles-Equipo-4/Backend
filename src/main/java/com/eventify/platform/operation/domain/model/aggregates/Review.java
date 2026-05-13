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
     * Default constructor for JPA
     */
    public Review () {}

    public Review(CreateReviewCommand command){
        this();
        this.content = command.content();
        this.rating = command.rating();
        this.fullName = command.fullName();
        this.socialEventDate = command.socialEventDate();
        this.profileId = new ProfileId(command.profileId());
        this.socialEventId = new SocialEventId(command.socialEventId());
    }

    public Long getProfileId() {
        return this.profileId.profileId();
    }

    public Long getSocialEventId() {
        return this.socialEventId.socialEventId();
    }

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
