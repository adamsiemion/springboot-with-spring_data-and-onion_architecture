package com.springboot.onionarch.jpa;

import com.springboot.onionarch.domain.User;
import com.springboot.onionarch.domain.UserRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class UserRepositorySpringData implements UserRepository {

    private final UserDaoSpringData dao;

    @Inject
    public UserRepositorySpringData(final UserDaoSpringData dao) {
        this.dao = dao;
    }

    @Override
    public Iterable<User> list() {
        return dao.findAll();
    }

    @Override
    public User get(Long id) {
        return dao.findOne(id);
    }

    @Override
    public void save(User user) {
        dao.save(user);
    }

    @Override
    public void delete(Long aLong) {
        dao.delete(aLong);
    }
}
