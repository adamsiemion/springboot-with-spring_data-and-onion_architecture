package com.springboot.onionarch;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class UserRepositorySpringData implements UserRepository {

    @Inject private UserRepositorySpringDataJpa repository;

    @Override
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }
}
