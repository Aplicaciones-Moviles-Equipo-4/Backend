package com.eventify.platform.shared.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

/**
 * Abstract base class for entities that require auditing features.
 * Automatically populates and updates the creation and modification timestamps.
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class AuditableModel {
    /**
     * Unique identifier for the entity.
     */
    @Id
    @Getter
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    /**
     * Timestamp indicating when the entity was first persisted.
     */
    @Getter
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    /**
     * Timestamp indicating the last time the entity was updated.
     */
    @Getter
    @LastModifiedDate
    @Column(nullable = false)
    private Date updatedAt;
}
