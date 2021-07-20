package com.stock.broker.broker;

import com.stock.broker.broker.error.CustomError;
import com.stock.broker.broker.models.Quote;
import com.stock.broker.broker.models.Symbol;
import com.stock.broker.broker.stores.InMemoryStore;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public class QuoteControllerTest {

    private static final Logger LOG = LoggerFactory.getLogger(QuoteControllerTest.class);

    @Inject
    @Client("/")
    RxHttpClient client;

    @Inject
    InMemoryStore store;

    @Test
    void returnsQuotePerSymbol() {
        Quote apple = initRandomQuote("APPL");
        store.update(apple);

        Quote amazon = initRandomQuote("AMZN");
        store.update(amazon);

        final Quote appleResult = client.toBlocking().retrieve(HttpRequest.GET("/quotes/APPL"), Quote.class);
        LOG.debug("Result: {}", appleResult);
        Assertions.assertThat(apple).isEqualToComparingFieldByField(appleResult);

        final Quote amazonResult = client.toBlocking().retrieve(HttpRequest.GET("/quotes/AMZN"), Quote.class);
        LOG.debug("Result: {}", amazonResult);
        Assertions.assertThat(amazon).isEqualToComparingFieldByField(amazonResult);
    }

    @Test
    void returnsNotFoundOnUnsupportedSymbol() {
        try{
            //NEED TO PASS THE TYPE TO MICRONAUT UNDERSTAND THE OBJECT
            //MICRONAUT WAITS 2 KINDS OF ARGUMENTS TO CONVERT
            client.toBlocking().retrieve(HttpRequest.GET("/quotes/UNSUPPORTED"), Argument.of(Quote.class), Argument.of(CustomError.class));
        } catch(HttpClientResponseException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getResponse().getStatus());
            LOG.debug("Body: {}", e.getResponse().getBody(CustomError.class));
            Optional<CustomError> customError = e.getResponse().getBody(CustomError.class);
            assertTrue(customError.isPresent());
            assertEquals(404, customError.get().getStatus());
            assertEquals("NOT_FOUND", customError.get().getError());
            assertEquals("Quote for symbol not avaiable", customError.get().getMessage());
            assertEquals("/quotes/UNSUPPORTED", customError.get().getPath());
        }
    }


    private Quote initRandomQuote(String symbolValue) {
        return Quote.builder()
                .symbol(new Symbol(symbolValue))
                .bid(randomValue())
                .ask(randomValue())
                .lastPrice(randomValue())
                .volume(randomValue())
                .build();
    }

    private BigDecimal randomValue() {
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 100));
    }

}
