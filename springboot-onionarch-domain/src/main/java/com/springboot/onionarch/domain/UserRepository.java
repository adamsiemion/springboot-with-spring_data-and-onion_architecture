package com.springboot.onionarch.domain;

public interface UserRepository {
    Iterable<User> list();

    User get(Long id);

    void save(User user);

    void delete(Long id);
}
