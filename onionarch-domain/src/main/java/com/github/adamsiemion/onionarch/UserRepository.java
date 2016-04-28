package com.github.adamsiemion.onionarch;

public interface UserRepository {
    Iterable<User> list();

    User get(String id);

    void save(User user);

    void delete(String id);
}
