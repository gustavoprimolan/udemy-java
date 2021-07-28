package com.udemy.course.auth;

import com.udemy.course.auth.persistence.UserEntity;
import com.udemy.course.auth.persistence.UserRepository;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

@Singleton
public class JDBCAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(JDBCAuthenticationProvider.class);
    private final UserRepository users;

    public JDBCAuthenticationProvider(UserRepository users) {
        this.users = users;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flowable.create(emitter -> {
            final String identity = (String) authenticationRequest.getIdentity();
            LOG.debug("User {} tries to login", identity);

            Optional<UserEntity> maybeUser = users.findByEmail(identity);

            if(maybeUser.isPresent()) {
                LOG.debug("Found user: {}", maybeUser.get().getEmail());
                String secret = (String) authenticationRequest.getSecret();
                if(maybeUser.get().getPassword().equals(secret)) {
                    LOG.debug("User logged in.");
                    //FIRST NAME = EMAIL
                    // THIRD PARAMETER IS CUSTOM ATTRIBUTES
                    HashMap<String, Object> attributes = new HashMap<>();
                    attributes.put("hair_color", "brown");
                    attributes.put("language", "en");
                    UserDetails userDetail = new UserDetails(
                            identity,
                            Arrays.asList("ROLE_USER"),
                            attributes);
                    emitter.onNext(userDetail);
                    emitter.onComplete();
                    return;
                } else {
                    LOG.debug("Wrong password provider for user {}", identity);
                }
            } else {
                LOG.debug("No user found with email: {}", identity);
            }

            emitter.onError(new AuthenticationException(new AuthenticationFailed("Wrong username or password")));
        }, BackpressureStrategy.ERROR);
    }
}
