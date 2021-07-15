package com.stock.broker.broker;

import com.stock.broker.broker.models.Symbol;
import com.stock.broker.broker.stores.InMemoryStore;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.List;

@Controller("/markets")
public class MarketController {

    private final InMemoryStore store;

    public MarketController(InMemoryStore store) {
        this.store = store;
    }

    @Get
    public List<Symbol> all() {
        return store.getAllSymbols();
    }
}
