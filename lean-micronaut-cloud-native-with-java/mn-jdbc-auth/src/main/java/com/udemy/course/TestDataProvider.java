package com.udemy.course;

import com.udemy.course.auth.persistence.UserEntity;
import com.udemy.course.auth.persistence.UserRepository;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;

import javax.inject.Singleton;

@Singleton
public class TestDataProvider {

    private final UserRepository users;

    public TestDataProvider(final UserRepository users) {
        this.users = users;
    }

    @EventListener
    public void init(StartupEvent event) {
        if(users.findByEmail("alice@example.com").isEmpty()) {
            final UserEntity alice = new UserEntity();
            alice.setEmail("alice@example.com");
            alice.setPassword("secret");
            users.save(alice);
        }
    }

}
