package com.springboot.onionarch;

public interface UserRepository {
    Iterable<User> findAll();

    void save(User user);
}
