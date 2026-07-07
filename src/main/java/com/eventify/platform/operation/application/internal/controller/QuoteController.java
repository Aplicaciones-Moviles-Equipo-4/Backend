package com.eventify.platform.operation.application.internal.controller;

import com.eventify.platform.operation.application.internal.service.QuoteService;
import com.eventify.platform.planning.domain.model.aggregates.Quote;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    /**
     * Retrieves quotes filtered by hostId if provided.
     *
     * @param hostId Optional host ID to filter quotes.
     * @return List of quotes matching the filter criteria.
     */
    @GetMapping
    public ResponseEntity<List<Quote>> getQuotes(@RequestParam(required = false) Long hostId) {
        List<Quote> quotes;
        if (hostId != null) {
            quotes = quoteService.getQuotesByHostId(hostId);
        } else {
            quotes = quoteService.getAllQuotes();
        }
        return ResponseEntity.ok(quotes);
    }
}