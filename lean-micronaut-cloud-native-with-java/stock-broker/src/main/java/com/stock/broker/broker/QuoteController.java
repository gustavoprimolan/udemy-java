package com.stock.broker.broker;

import com.stock.broker.broker.models.Quote;
import com.stock.broker.broker.stores.InMemoryStore;
import io.micronaut.http.HttpResponse;
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
    public HttpResponse<Quote> getQuote(@PathVariable String symbol) {
        Optional<Quote> quote = store.fetchQuotes(symbol);
        return HttpResponse.ok(quote.get());
    }
}
