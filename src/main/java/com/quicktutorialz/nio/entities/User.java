package com.quicktutorialz.nio.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    private final String id;
    private final String name;
    private final String date;

    public User(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.date = LocalDateTime.now().toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
