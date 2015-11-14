package com.springboot.onionarch.domain;

public interface UserRepository {
    Iterable<User> findAll();

    void save(User user);
}
