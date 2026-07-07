package com.eventify.platform.planning.domain.model.aggregates;



import com.eventify.platform.planning.domain.model.valueobjects.*;
import com.eventify.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;

/*
*SocialEvent Aggregate Root
*
*/
@Entity
@Table(name = "social_events")
public class SocialEvent extends AuditableAbstractAggregateRoot<SocialEvent>
{

    @Embedded
    private SocialEventTitle title;

    @Embedded
    private SocialEventDate date;

    @Embedded
    private CustomerName customerName;

    @Embedded
    private Place place;

    @Embedded
    private SocialEventStatus status;

    /**
     * Id of the organizer (profileId) that owns this event. Nullable for legacy rows created
     * before ownership was tracked.
     */
    @Column(name = "organizer_id")
    private Long organizerId;




    /**
     * Constructor with full value objects.
     *
     * @param title        the title of the event
     * @param place        the location of the event
     * @param date         the scheduled date
     * @param customerName the customer full name
     * @param status       the current status of the event
     * @param organizerId  the id of the organizer (profileId) that owns this event
     */
    public SocialEvent(SocialEventTitle title, Place place, SocialEventDate date,
                       CustomerName customerName, SocialEventStatus status, Long organizerId) {
        this.title = title;
        this.date = date;
        this.customerName = customerName;
        this.place = place;
        this.status = status;
        this.organizerId = organizerId;
    }

    protected SocialEvent() {}

    // Getters for each embedded value object

    public String getTitle() {
        return title.title();
    }

    public String getPlace() {
        return place.place();
    }

    public String getCustomerName() {
        return customerName.customerName();
    }

    public java.time.LocalDate getDate() {
        return date.eventDate();
    }

    public String getEventStatus() {
        return status.valueStatus();
    }

    public Long getOrganizerId() {
        return organizerId;
    }


    /**
     * Allows updating the event's status.
     *
     * @param newStatus the new status to set
     */
    public void updateStatus(String newStatus) {
        this.status = new SocialEventStatus(newStatus);


    }

    /**
     * Allows updating basic event details.
     *
     * @param newTitle new title
     * @param newPlace new place
     * @param newDate  new date
     */
    public void updateDetails(String newTitle, java.time.LocalDate newDate, String newPlace, String newCustomerName ) {
        this.title = new SocialEventTitle(newTitle);
        this.date = new SocialEventDate(newDate);
        this.customerName = new CustomerName(newCustomerName); // Assuming customer name remains unchanged
        this.place = new Place(newPlace);

    }
}



