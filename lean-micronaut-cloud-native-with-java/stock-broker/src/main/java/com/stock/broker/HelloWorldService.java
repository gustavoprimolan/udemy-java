package com.stock.broker;

import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Value;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public class HelloWorldService {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldService.class);

    //All strings in annotations can come from configuration file
//    @Value("Hello from service")
//    @Value("${hello.service.greeting}")
    @Property(name = "hello.service.greeting", defaultValue = "default value greeting")
    private String greeting;

    public String sayHi() {
        return greeting;
    }

    //ITS CALLED WHEN STARTUP
    @EventListener
    public void onStartup(StartupEvent startupEvent) {
        //LEVEL DEBUG
        LOG.debug("Startup: {}", HelloWorldService.class.getSimpleName());
    }

}
