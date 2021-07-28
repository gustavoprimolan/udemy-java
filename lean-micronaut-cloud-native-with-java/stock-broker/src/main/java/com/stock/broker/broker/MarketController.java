package com.stock.broker.broker;

import com.stock.broker.broker.models.Symbol;
import com.stock.broker.broker.stores.InMemoryStore;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/markets")
public class MarketController {

    private final InMemoryStore store;

    public MarketController(InMemoryStore store) {
        this.store = store;
    }

    @Operation(summary = "Returns all available markets")
    @ApiResponse(
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Tag(name = "markets")
    @Get
    public List<Symbol> all() {
        return store.getAllSymbols();
    }
}
