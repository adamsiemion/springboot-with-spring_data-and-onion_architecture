package com.springboot.onionarch.jooq;

import com.springboot.onionarch.domain.User;
import com.springboot.onionarch.domain.UserRepository;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.stream.Collectors;

@Component
public class UserRepositoryJooq implements UserRepository {

    @Inject private DSLContext dslContext;

    @Override
    public Iterable<User> list() {
        return dslContext.select(DSL.fieldByName("id"), DSL.fieldByName("name")).from("user")
            .stream()
            .map(r -> new User(r.getValue("id", Long.class), r.getValue("name", String.class)))
            .collect(Collectors.toList());
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public void save(User user) {
        dslContext.insertInto(DSL.tableByName("user")).columns(DSL.fieldByName("name")).values(user.getName());
    }

    @Override
    public void delete(Long id) {
        dslContext.delete(DSL.tableByName("user")).where(DSL.fieldByName("id").eq(id));
    }
}
