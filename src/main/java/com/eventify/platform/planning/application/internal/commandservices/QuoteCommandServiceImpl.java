package com.eventify.platform.planning.application.internal.commandservices;

import com.eventify.platform.planning.domain.model.aggregates.Quote;
import com.eventify.platform.planning.domain.model.commands.*;
import com.eventify.platform.planning.domain.model.valueobjects.QuoteId;
import com.eventify.platform.planning.domain.services.QuoteCommandService;
import com.eventify.platform.planning.infrastructure.persistence.jpa.repositories.QuoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class QuoteCommandServiceImpl implements QuoteCommandService {
    private final QuoteRepository quoteRepository;

    public QuoteCommandServiceImpl(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Override
    public String handle(CreateQuoteCommand command){
        if(quoteRepository.existsByTitle(command.title())) throw new IllegalArgumentException("Quote already exists");
        var quote = new Quote(command);
        var createdQuote = quoteRepository.save(quote);
        return createdQuote.getQuoteId();
    }

    @Override
    public Optional<Quote> handle(UpdateQuoteCommand command){

        var quote = quoteRepository.findByQuoteId(command.quoteId())
                .orElseThrow(() -> new IllegalArgumentException("Quote not found with id " + command.quoteId()));
        try{
            var updatedQuote = quote.updateInformation(command.title(),command.eventType(),command.guestQuantity(),command.location(),command.totalPrice(),command.eventDate());
            quoteRepository.save(updatedQuote);
            return Optional.of(updatedQuote);
        }catch(Exception e){
            throw new RuntimeException("Error updating quote: "+ e.getMessage(),e);
        }
    }

    @Override
    public void handle(DeleteQuoteCommand command){
        verifyIfQuoteExistsById(command.quoteId());
        try{
            quoteRepository.deleteById(command.quoteId());
        }catch(Exception e){
            throw new RuntimeException("Error deleting quote: "+ e.getMessage(),e);
        }
    }

    private void verifyIfQuoteExistsById(QuoteId quoteId){
        if(!quoteRepository.existsById(quoteId)){
            throw new IllegalArgumentException("Quote with id " + quoteId + " does not exist");
        }
    }

    public String handle(ConfirmQuoteCommand command){
         return quoteRepository.findById(command.quoteId()).map(quote ->{
            quote.accept();
            quoteRepository.save(quote);
            return quote.getQuoteId();
        }).orElseThrow(() -> new IllegalArgumentException("Quote not found with id " + command.quoteId()));

    }

    public String handle(RejectQuoteCommand command){
        return quoteRepository.findById(command.quoteId()).map(quote ->{
            quote.reject();
            quoteRepository.save(quote);
            return quote.getQuoteId();
        }).orElseThrow(() -> new IllegalArgumentException("Quote not found with id " + command.quoteId()));

    }
}
