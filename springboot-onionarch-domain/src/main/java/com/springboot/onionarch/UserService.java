package com.springboot.onionarch;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class UserService {

    @Inject private UserRepository repository;

    public void uppercaseAllUserNames() {
        Iterable<User> users = repository.findAll();
        for(User user : users) {
            User uppercasedUser = new User(user.getId(), user.getName().toUpperCase());
            repository.save(uppercasedUser);
        }
    }
}
