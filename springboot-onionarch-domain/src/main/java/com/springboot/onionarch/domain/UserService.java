package com.springboot.onionarch.domain;

import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserService {

    private final UserRepository repository;

    @Inject
    public UserService(final UserRepository repository) {
        this.repository = repository;
    }

    public void uppercaseAllUserNames() {
        Iterable<User> users = repository.list();
        for(User user : users) {
            User uppercasedUser = new User(user.getId(), user.getName().toUpperCase());
            repository.save(uppercasedUser);
        }
    }
}
