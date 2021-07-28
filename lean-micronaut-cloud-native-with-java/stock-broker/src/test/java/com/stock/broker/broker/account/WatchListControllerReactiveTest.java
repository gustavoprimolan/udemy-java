package com.stock.broker.broker.account;

import com.stock.broker.broker.models.Symbol;
import com.stock.broker.broker.models.WatchList;
import com.stock.broker.broker.stores.InMemoryAccountStore;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.reactivex.Single;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public class WatchListControllerReactiveTest {

    private static final Logger LOG = LoggerFactory.getLogger(WatchListControllerReactiveTest.class);
    private static final UUID TEST_ACCOUNT_ID = WatchListControllerReactive.ACCOUNT_ID;
    @Inject
    @Client("/")
    JWTWatchListClient client;

    @Inject
    InMemoryAccountStore store;

    @AfterEach
    public void afterEach() {
        store.deleteWatchList(TEST_ACCOUNT_ID);
    }

    @Test
    void returnsEmpty() {
        Single<WatchList> result = client.retrieveWatchList(getAuthorizationHeader()).singleOrError();

        assertTrue(result.blockingGet().getSymbols().isEmpty());
        assertTrue(store.getWatchList(TEST_ACCOUNT_ID).getSymbols().isEmpty());
    }

    @Test
    void returnsWatchListForAccount() {
        List<Symbol> symbols = Stream.of("APPL", "AMZN", "NFLX").map(Symbol::new).collect(Collectors.toList());
        WatchList watchList = new WatchList(symbols);
        store.updateWatchList(TEST_ACCOUNT_ID, watchList);

        var result = client.retrieveWatchList(getAuthorizationHeader()).singleOrError().blockingGet();

        assertEquals(3, result.getSymbols().size());
        assertEquals(3, store.getWatchList(TEST_ACCOUNT_ID).getSymbols().size());
    }

    @Test
    void returnsWatchListForAccountAsSingle() {
        List<Symbol> symbols = Stream.of("APPL", "AMZN", "NFLX").map(Symbol::new).collect(Collectors.toList());
        WatchList watchList = new WatchList(symbols);
        store.updateWatchList(TEST_ACCOUNT_ID, watchList);

        final WatchList result = client.retrieveWatchListAsSingle(getAuthorizationHeader()).blockingGet();

        assertEquals(3, result.getSymbols().size());
        assertEquals(3, store.getWatchList(TEST_ACCOUNT_ID).getSymbols().size());
    }

    @Test
    void canUpdateWatchListForAccount() {
        List<Symbol> symbols = Stream.of("APPL", "AMZN", "NFLX").map(Symbol::new).collect(Collectors.toList());
        WatchList watchList = new WatchList(symbols);
        store.updateWatchList(TEST_ACCOUNT_ID, watchList);

        HttpResponse<WatchList> added = client.updateWatchList(getAuthorizationHeader(), watchList);

        assertEquals(HttpStatus.OK, added.getStatus());
        assertEquals(watchList, store.getWatchList(TEST_ACCOUNT_ID));
    }

    @Test
    void canDeleteWatchListForAccount() {
        List<Symbol> symbols = Stream.of("APPL", "AMZN", "NFLX").map(Symbol::new).collect(Collectors.toList());
        WatchList watchList = new WatchList(symbols);
        store.updateWatchList(TEST_ACCOUNT_ID, watchList);

        HttpResponse<Object> deleted = client.deleteWatchList(getAuthorizationHeader(), TEST_ACCOUNT_ID);

        assertEquals(HttpStatus.OK, deleted.getStatus());
        assertTrue(store.getWatchList(TEST_ACCOUNT_ID).getSymbols().isEmpty());
    }

    private String getAuthorizationHeader() {
        return "Bearer " + givenMyUserLoggedIn().getAccessToken();
    }

    private BearerAccessRefreshToken givenMyUserLoggedIn() {
        return client.login(new UsernamePasswordCredentials("my-user", "secret"));
    }

}
