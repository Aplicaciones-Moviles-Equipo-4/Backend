package com.eventify.platform.planning.domain.services;

import com.eventify.platform.planning.domain.model.aggregates.Quote;
import com.eventify.platform.planning.domain.model.queries.ExistsByQuoteIdQuery;
import com.eventify.platform.planning.domain.model.queries.GetAllQuotesByHostIdQuery;
import com.eventify.platform.planning.domain.model.queries.GetAllQuotesByOrganizerIdQuery;
import com.eventify.platform.planning.domain.model.queries.GetQuoteByQuoteIdQuery;
import com.eventify.platform.planning.domain.model.queries.GetAllQuotesByCustomerIdQuery;

import java.util.List;
import java.util.Optional;

public interface QuoteQueryService {

    List<Quote> handle(GetAllQuotesByOrganizerIdQuery query);

    List<Quote> handle(GetAllQuotesByHostIdQuery query);

    Optional<Quote> handle(GetQuoteByQuoteIdQuery query);

    boolean handle(ExistsByQuoteIdQuery query);

    List<Quote> handle(GetAllQuotesByCustomerIdQuery query);
}