package com.springboot.onionarch;

import org.springframework.data.repository.CrudRepository;

public interface UserRepositorySpringDataJpa extends CrudRepository<User, Long> {
}
