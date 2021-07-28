package com.stock.broker;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;


// ALLOW REQUEST ENDPOINTS WITHOUT AUTHENTICATION
@Secured(SecurityRule.IS_ANONYMOUS)
//@Controller("/hello")
//@Controller("${hello.controller.path:/hello}")
@Controller("${hello.controller.path}")
public class HelloWorldController {

    private final HelloWorldService helloWorldService;
    private final GreetingConfig greetingConfig;

    public HelloWorldController(HelloWorldService helloWorldService, GreetingConfig greetingConfig) {
        this.helloWorldService = helloWorldService;
        this.greetingConfig = greetingConfig;
    }

    @Get("/")
    public String index() {
        return helloWorldService.sayHi();
    }

    @Get("/de")
    public String greetInGerman() {
        return greetingConfig.getDe();
    }

    @Get("en")
    public String greetInEnglish() {
        return greetingConfig.getEn();
    }

    @Get("/json")
    public Greeting json() {
        return new Greeting();
    }

}
