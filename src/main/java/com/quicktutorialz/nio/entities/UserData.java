package com.quicktutorialz.nio.entities;

public class UserData {
    private final String name;
    private final String surname;

    public UserData(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
