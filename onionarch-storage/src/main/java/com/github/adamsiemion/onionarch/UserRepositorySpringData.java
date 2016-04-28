package com.github.adamsiemion.onionarch;

import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class UserRepositorySpringData implements UserRepository {

    private final UserDaoMongo dao;

    @Inject
    public UserRepositorySpringData(final UserDaoMongo dao) {
        this.dao = dao;
    }

    @Override
    public Iterable<User> list() {
        return dao.findAll();
    }

    @Override
    public User get(String id) {
        return dao.findOne(id);
    }

    @Override
    public void save(User user) {
        dao.save(user);
    }

    @Override
    public void delete(String id) {
        dao.delete(id);
    }
}