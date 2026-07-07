package com.eventify.platform.planning.domain.model.queries;

public class GetAllQuotesByCustomerIdQuery {

    private final Long customerId;

    public GetAllQuotesByCustomerIdQuery(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
