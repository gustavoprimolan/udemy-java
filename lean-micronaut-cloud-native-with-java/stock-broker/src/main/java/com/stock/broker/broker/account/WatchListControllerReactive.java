package com.stock.broker.broker.account;

import com.stock.broker.broker.models.WatchList;
import com.stock.broker.broker.stores.InMemoryAccountStore;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Controller("/account/watchlist-reactive")
public class WatchListControllerReactive {

    private static final Logger LOG = LoggerFactory.getLogger(WatchListControllerReactive.class);
    public static final UUID ACCOUNT_ID = UUID.randomUUID();

    private final InMemoryAccountStore store;
    private final Scheduler scheduler;

    public WatchListControllerReactive(InMemoryAccountStore store, @Named(TaskExecutors.IO) ExecutorService executorService) {
        this.store = store;
        this.scheduler = Schedulers.from(executorService);
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public WatchList get() {
        LOG.debug("getWatchlist Reactive- {}", Thread.currentThread().getName());
        return store.getWatchList(ACCOUNT_ID);
    }

//    @Get(value="/single", produces = MediaType.APPLICATION_JSON)
//    public Single<WatchList> getAsSingle() {
//        return Single.fromCallable(() -> {
//            LOG.debug("getWatchlist Single Reactive- {}", Thread.currentThread().getName());
//            return store.getWatchList(ACCOUNT_ID);
//        }).subscribeOn(scheduler);
//    }

    @Get(value="/single", produces = MediaType.APPLICATION_JSON)
    public Flowable<WatchList> getAsSingle() {
        return Single.fromCallable(() -> {
            LOG.debug("getWatchlist Single Reactive- {}", Thread.currentThread().getName());
            return store.getWatchList(ACCOUNT_ID);
        }).toFlowable().subscribeOn(scheduler);
    }

    @Put(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public WatchList update(@Body WatchList watchList) {
        return store.updateWatchList(ACCOUNT_ID, watchList);
    }

    @Delete(value="/{accountId}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public void delete(@PathVariable UUID accountId) {
        store.deleteWatchList(accountId);
    }


}
