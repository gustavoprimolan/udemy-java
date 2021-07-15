package com.stock.broker.broker;

import com.stock.broker.broker.models.Quote;
import com.stock.broker.broker.models.Symbol;
import com.stock.broker.broker.stores.InMemoryStore;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

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
