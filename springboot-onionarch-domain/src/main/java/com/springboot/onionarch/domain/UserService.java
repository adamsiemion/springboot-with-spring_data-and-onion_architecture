package com.springboot.onionarch.domain;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserService {

    private final UserRepository repository;

    @Inject
    public UserService(final UserRepository repository) {
        this.repository = repository;
    }

    public void upperCaseAllUserNames() {
        final Iterable<User> users = repository.list();
        for(User user : users) {
            User userWithUpperCasedName = new User(user.getId(), user.getName().toUpperCase());
            repository.save(userWithUpperCasedName);
        }
    }
}
