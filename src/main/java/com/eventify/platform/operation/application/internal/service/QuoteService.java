package com.eventify.platform.operation.application.internal.service; // Add the package statement

import com.eventify.platform.operation.domain.repository.QuoteRepository; // Import the QuoteRepository
import com.eventify.platform.planning.domain.model.aggregates.Quote;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository; // Add the QuoteRepository dependency

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository; // Initialize the QuoteRepository
    }

    /**
     * Retrieves all quotes.
     *
     * @return List of all quotes.
     */
    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    /**
     * Retrieves quotes filtered by hostId.
     *
     * @param hostId ID of the host to filter quotes.
     * @return List of quotes for the specified host.
     */
    public List<Quote> getQuotesByHostId(Long hostId) {
        return quoteRepository.findByHostId(hostId);
    }
}