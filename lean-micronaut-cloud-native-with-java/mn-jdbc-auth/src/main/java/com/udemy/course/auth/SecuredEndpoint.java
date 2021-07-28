package com.udemy.course.auth;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/secured")
public class SecuredEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(SecuredEndpoint.class);

    @Get("/status")
    public List<Object> get(Principal principal) {
        //principal.getName() -> email
        Authentication details = (Authentication) principal;
        Map<String, Object> attributes = details.getAttributes();

        List<Object> response = Arrays.asList(details.getName(), attributes.get("hair_color"), attributes.get("language"));
        LOG.debug("User Details: {}", details.getAttributes());
        return response;
    }
}
