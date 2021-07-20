package com.stock.broker.broker;

import com.stock.broker.broker.error.CustomError;
import com.stock.broker.broker.models.Quote;
import com.stock.broker.broker.stores.InMemoryStore;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;

import java.util.Optional;

@Controller("/quotes")
public class QuoteController {

    private final InMemoryStore store;

    public QuoteController(InMemoryStore store) {
        this.store = store;
    }

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
