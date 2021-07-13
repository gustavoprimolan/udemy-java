package com.stock.broker;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

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

}
