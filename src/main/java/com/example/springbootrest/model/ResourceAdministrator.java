package com.example.springbootrest.model;

import java.util.UUID;

public class ResourceAdministrator extends User {
    public ResourceAdministrator(UUID uuid, String login, String password, Boolean isActive, String accessLevel) {
        super(uuid, login, password, isActive, accessLevel);
    }
}
