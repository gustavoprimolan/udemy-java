package com.stock.broker.broker.account;

import com.stock.broker.broker.models.Symbol;
import com.stock.broker.broker.models.WatchList;
import com.stock.broker.broker.stores.InMemoryAccountStore;
import io.micronaut.http.*;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class WatchListControllerTest {

    private static final Logger LOG = LoggerFactory.getLogger(WatchListControllerTest.class);
    private static final UUID TEST_ACCOUNT_ID = WatchListController.ACCOUNT_ID;
    public static final String ACCOUNT_WATCHLIST = "/account/watchlist";

    @Inject
    @Client("/")
    RxHttpClient client;

    @Inject
    InMemoryAccountStore store;

    @AfterEach
    public void afterEach() {
        store.deleteWatchList(TEST_ACCOUNT_ID);
    }

    @Test
    void unauthorizedAccessIsForbidden() {
        try{
            client.toBlocking().retrieve(ACCOUNT_WATCHLIST);
            fail("Should fail if no exception is thrown!");
        } catch(HttpClientResponseException e) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.getStatus());
        }
    }


    @Test
    void returnsEmpty() {
        BearerAccessRefreshToken token = givenMyUserIsLoggedIn();

        var request = HttpRequest.GET(ACCOUNT_WATCHLIST)
                .bearerAuth(token.getAccessToken())
                .accept(MediaType.APPLICATION_JSON);
        final WatchList result = client.toBlocking().retrieve(request, WatchList.class);
        assertTrue(result.getSymbols().isEmpty());
        assertTrue(store.getWatchList(TEST_ACCOUNT_ID).getSymbols().isEmpty());
    }

    @Test
    void returnsWatchListForAccount() {
        BearerAccessRefreshToken token = givenMyUserIsLoggedIn();
        List<Symbol> symbols = Stream.of("APPL", "AMZN", "NFLX").map(Symbol::new).collect(Collectors.toList());
        WatchList watchList = new WatchList(symbols);
        store.updateWatchList(TEST_ACCOUNT_ID, watchList);

        var request = HttpRequest.GET(ACCOUNT_WATCHLIST)
                .bearerAuth(token.getAccessToken())
                .accept(MediaType.APPLICATION_JSON);
        final WatchList result = client.toBlocking().retrieve(request, WatchList.class);

        assertEquals(3, result.getSymbols().size());
        assertEquals(3, store.getWatchList(TEST_ACCOUNT_ID).getSymbols().size());
    }

    @Test
    void canUpdateWatchListForAccount() {
        BearerAccessRefreshToken token = givenMyUserIsLoggedIn();
        List<Symbol> symbols = Stream.of("APPL", "AMZN", "NFLX").map(Symbol::new).collect(Collectors.toList());
        WatchList watchList = new WatchList(symbols);
        store.updateWatchList(TEST_ACCOUNT_ID, watchList);

        var request = HttpRequest.PUT(ACCOUNT_WATCHLIST, watchList)
                .bearerAuth(token.getAccessToken())
                .accept(MediaType.APPLICATION_JSON);
        HttpResponse<Object> added = client.toBlocking().exchange(request);

        assertEquals(HttpStatus.OK, added.getStatus());
        assertEquals(watchList, store.getWatchList(TEST_ACCOUNT_ID));
    }

    @Test
    void canDeleteWatchListForAccount() {
        BearerAccessRefreshToken token = givenMyUserIsLoggedIn();
        List<Symbol> symbols = Stream.of("APPL", "AMZN", "NFLX").map(Symbol::new).collect(Collectors.toList());
        WatchList watchList = new WatchList(symbols);
        store.updateWatchList(TEST_ACCOUNT_ID, watchList);
        var request = HttpRequest.DELETE(ACCOUNT_WATCHLIST + TEST_ACCOUNT_ID)
                .bearerAuth(token.getAccessToken())
                .accept(MediaType.APPLICATION_JSON);
        HttpResponse<Object> deleted = client.toBlocking().exchange(request);

        assertEquals(HttpStatus.OK, deleted.getStatus());
        assertTrue(store.getWatchList(TEST_ACCOUNT_ID).getSymbols().isEmpty());
    }

    private BearerAccessRefreshToken givenMyUserIsLoggedIn() {
        final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("my-user", "secret");
        var login = HttpRequest.POST("/login", credentials);
        var response = client.toBlocking().exchange(login, BearerAccessRefreshToken.class);

        assertEquals(HttpStatus.OK, response.getStatus());

        final BearerAccessRefreshToken token = response.body();

        assertNotNull(token);
        assertEquals("my-user", token.getUsername());
        LOG.debug("Login Bearer Token: {} expires in {}", token.getAccessToken(), token.getExpiresIn());
        return token;
    }


}
