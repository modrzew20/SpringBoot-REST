package com.example.springbootrest.model;

import java.util.UUID;

public class Administrator extends User {

    public Administrator(UUID uuid, String login, String password, Boolean isActive, String accessLevel) {
        super(uuid, login, password, isActive, accessLevel);
    }
}
