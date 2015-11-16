package com.springboot.onionarch.rest;

import com.springboot.onionarch.SpringbootOnionArchitectureApplication;
import com.springboot.onionarch.domain.DomainConfig;
import com.springboot.onionarch.domain.User;
import com.springboot.onionarch.jooq.JooqConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringbootOnionArchitectureApplication.class)
@WebAppConfiguration
public class UserRestTest {

	@Inject
	private UserRest userRest;

	@Before
	public void deleteAllUsers() {
		for(User user : userRest.list()) {
			userRest.delete(user.getId());
		}
	}

	@Test
	public void shouldCreateJohnUser() {
		// given
		User user = new User("John Smith");
        userRest.create(user);
		// when
        List<User> users = userRest.list();
        // then
        assertTrue(users.stream().filter(u -> "John Smith".equals(u.getName())).findFirst().isPresent());
	}

}
