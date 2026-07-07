package com.eventify.platform.operation.domain.repository;

import com.eventify.platform.planning.domain.model.aggregates.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    // ...existing code...

    /**
     * Finds quotes by host ID.
     *
     * @param hostId ID of the host.
     * @return List of quotes for the specified host.
     */
    List<Quote> findByHostId(Long hostId);

    // ...existing code...
}