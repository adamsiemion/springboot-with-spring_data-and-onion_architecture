package com.github.adamsiemion.onionarch;

import com.github.adamsiemion.onionarch.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Adam Siemion
 */
public interface UserDaoMongo extends MongoRepository<User, String> {
}
