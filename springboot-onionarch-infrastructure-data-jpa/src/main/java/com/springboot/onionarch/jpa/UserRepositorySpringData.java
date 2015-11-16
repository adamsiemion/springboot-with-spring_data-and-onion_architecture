package com.springboot.onionarch.jpa;

import com.springboot.onionarch.domain.User;
import com.springboot.onionarch.domain.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserRepositorySpringData implements UserRepository {

    private final UserRepositorySpringDataJpa repository;

    @Inject
    public UserRepositorySpringData(final UserRepositorySpringDataJpa repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<User> list() {
        return repository.findAll();
    }

    @Override
    public User get(Long id) {
        return repository.findOne(id);
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public void delete(Long aLong) {
        repository.delete(aLong);
    }
}
