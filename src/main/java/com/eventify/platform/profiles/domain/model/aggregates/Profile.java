package com.eventify.platform.profiles.domain.model.aggregates;

import com.eventify.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.eventify.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.eventify.platform.profiles.domain.model.valueobjects.PersonName;
import com.eventify.platform.profiles.domain.model.valueobjects.ProfileType;
import com.eventify.platform.profiles.domain.model.valueobjects.StreetAddress;
import com.eventify.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * Profile aggregate root
 *
 * @summary
 * Profile aggregate root represents a user profile in the system.
 * It contains personal information like name, email, and address.
 *
 * @since 1.0
 */
@Getter
@Entity
@Table(name = "profiles")
public class Profile extends AuditableAbstractAggregateRoot<Profile> {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name", nullable = false, length = 50)),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name", nullable = false, length = 50))
    })
    private PersonName name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "email_address", nullable = false, unique = true, length = 255))
    })
    private EmailAddress email;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile_type", nullable = false, length = 20)
    private ProfileType type;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "street_address", length = 255)),
            @AttributeOverride(name = "number", column = @Column(name = "street_number", length = 10)),
            @AttributeOverride(name = "city", column = @Column(name = "city", length = 100)),
            @AttributeOverride(name = "postalCode", column = @Column(name = "postal_code", length = 20)),
            @AttributeOverride(name = "country", column = @Column(name = "country", length = 100))
    })
    private StreetAddress address;

    @Column(name = "profile_image_url", length = 1024)
    private String profileImageUrl;

    public Profile() {
        // Required by JPA
    }

    /**
     * Constructor for Profile
     *
     * @param command CreateProfileCommand with the profile data
     */
    public Profile(CreateProfileCommand command) {
        this();
        this.name = new PersonName(command.firstName(), command.lastName());
        this.email = new EmailAddress(command.email());
        this.address = new StreetAddress(command.street(), command.number(),
                command.city(), command.postalCode(), command.country());
        this.type = command.type();
    }

    /**
     * Constructor for Profile
     *
     * @param firstName First name
     * @param lastName Last name
     * @param email Email address
     * @param street Street address
     * @param number Street number
     * @param city City
     * @param postalCode Postal code
     * @param country Country
     */
    public Profile(String firstName, String lastName, String email, String street,
                   String number, String city, String postalCode, String country,
                   ProfileType type) {
        this();
        this.name = new PersonName(firstName, lastName);
        this.email = new EmailAddress(email);
        this.address = new StreetAddress(street, number, city, postalCode, country);
        this.type = type;
    }

    /**
     * Update profile information
     *
     * @param firstName First name
     * @param lastName Last name
     * @param email Email address
     * @param street Street address
     * @param number Street number
     * @param city City
     * @param postalCode Postal code
     * @param country Country
     * @return Updated profile
     */
    public Profile updateInformation(String firstName, String lastName, String email, String street,
                                     String number, String city, String postalCode, String country) {
        this.name = new PersonName(firstName, lastName);
        this.email = new EmailAddress(email);
        this.address = new StreetAddress(street, number, city, postalCode, country);
        return this;
    }

    public Profile updateInformation(String firstName, String lastName, String email, String street,
                                     String number, String city, String postalCode, String country,
                                     String profileImageUrl) {
        updateInformation(firstName, lastName, email, street, number, city, postalCode, country);
        this.profileImageUrl = profileImageUrl;
        return this;
    }

    /**
     * Get the full name
     *
     * @return Full name as string
     */
    public String getFullName() {
        return this.name.getFullName();
    }

    /**
     * Get the email address
     *
     * @return Email address as string
     */
    public String getEmailAddress() {
        return this.email.address();
    }

    /**
     * Get the street address
     *
     * @return Street address as string
     */
    public String getStreetAddress() {
        return this.address.getFullAddress();
    }
}
