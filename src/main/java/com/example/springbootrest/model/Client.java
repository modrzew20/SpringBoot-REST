package com.example.springbootrest.model;

import java.util.UUID;

public class Client extends User {
    public Client(UUID uuid, String login, String password, Boolean isActive, String accessLevel) {
        super(uuid, login, password, isActive, accessLevel);
    }
}
