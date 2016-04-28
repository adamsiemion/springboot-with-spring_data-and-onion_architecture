package com.github.adamsiemion.onionarch;

public class User {
    private String id;
    private String name;

    User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}