package com.eventify.platform.profiles.domain.model.aggregates;

import com.eventify.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * ServiceCatalog aggregate root within Profile context.
 */
@Getter
@Entity
@Table(name = "service_catalogs")
public class ServiceCatalog extends AuditableAbstractAggregateRoot<ServiceCatalog> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(name = "price_from", nullable = false)
    private Double priceFrom;

    @Column(name = "price_to", nullable = false)
    private Double priceTo;

    @Column(name = "image_url", length = 1024)
    private String imageUrl;

    protected ServiceCatalog() {
        // Required by JPA
    }

    public ServiceCatalog(Profile profile, String title, String description, String category, Double priceFrom, Double priceTo, String imageUrl) {
        this();
        this.profile = profile;
        this.title = title;
        this.description = description;
        this.category = category;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.imageUrl = imageUrl;
    }

    public ServiceCatalog update(String title, String description, String category, Double priceFrom, Double priceTo, String imageUrl) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.imageUrl = imageUrl;
        return this;
    }
}