package com.stock.broker.broker;

import com.stock.broker.broker.error.CustomError;
import com.stock.broker.broker.models.Quote;
import com.stock.broker.broker.stores.InMemoryStore;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/quotes")
public class QuoteController {

    private final InMemoryStore store;

    public QuoteController(InMemoryStore store) {
        this.store = store;
    }

    @Operation(summary = "Returns a quote for the given symbol.")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @ApiResponse(responseCode = "400", description = "Invalid symbol specified")
    @Tag(name = "quotes")
    //PATH PARAM
    @Get("/{symbol}")
    public HttpResponse<?> getQuote(@PathVariable String symbol) {
        Optional<Quote> quote = store.fetchQuotes(symbol);
        if(quote.isEmpty()) {
            final CustomError notFound = CustomError.builder()
                    .status(HttpStatus.NOT_FOUND.getCode())
                    .error(HttpStatus.NOT_FOUND.name())
                    .message("Quote for symbol not avaiable")
                    .path("/quotes/" + symbol)
                    .build();
            return HttpResponse.notFound(notFound);
        }
        return HttpResponse.ok(quote.get());
    }
}
