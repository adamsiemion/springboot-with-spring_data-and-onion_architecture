package com.springboot.onionarch;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRest {
    @Inject
    private UserRepository userJpaRepository;

    @Inject
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> list() {
        Iterable<User> users = userJpaRepository.findAll();
        List<User> ret = new ArrayList<>();
        users.forEach(ret::add);
        return ret;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody User user) {
        userJpaRepository.save(user);
    }

    @RequestMapping(value = "upp", method = RequestMethod.PUT)
    public void uppercase() {
        userService.uppercaseAllUserNames();
    }
}
