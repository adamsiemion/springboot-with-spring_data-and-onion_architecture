package com.springboot.onionarch.jpa;

import com.springboot.onionarch.domain.User;
import com.springboot.onionarch.domain.UserRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class UserRepositorySpringData implements UserRepository {

    private final UserRepositorySpringDataJpa repository;

    @Inject
    public UserRepositorySpringData(final UserRepositorySpringDataJpa repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }
}
