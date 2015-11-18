package com.springboot.onionarch.jpa;

import com.springboot.onionarch.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDaoSpringData extends CrudRepository<User, Long> {
}
