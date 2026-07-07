package com.eventify.platform.planning.interfaces.rest;

import com.eventify.platform.planning.domain.model.queries.GetAllQuotesByCustomerIdQuery;
import com.eventify.platform.planning.domain.services.QuoteQueryService;
import com.eventify.platform.planning.interfaces.rest.resources.QuoteResource;
import com.eventify.platform.planning.interfaces.rest.transform.QuoteResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/customers/{customerId}/quotes", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Customer")
public class CustomerQuotesController {

    private final QuoteQueryService quoteQueryService;

    public CustomerQuotesController(QuoteQueryService quoteQueryService) {
        this.quoteQueryService = quoteQueryService;
    }

    @GetMapping
    @Operation(summary = "Get quotes of a customer", description = "Get quotes where the hostId matches the customer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quotes retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Quotes not found")
    })
    public ResponseEntity<List<QuoteResource>> getQuotesForCustomerWithCustomerId(@PathVariable Long customerId) {
        var getAllQuotesByCustomerId = new GetAllQuotesByCustomerIdQuery(customerId);
        var quotes = quoteQueryService.handle(getAllQuotesByCustomerId); // Ensure this method exists in QuoteQueryService
        var quoteResources = quotes.stream()
                                    .map(QuoteResourceFromEntityAssembler::toResourceFromEntity)
                                    .collect(Collectors.toList()); // Use Collectors.toList() to collect the stream
        return ResponseEntity.ok(quoteResources);
    }
}
