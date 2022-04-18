package com.example.springbootrest.model;

import java.util.UUID;

public abstract class User {

    private UUID uuid;
    private String login;
    private String password;
    private Boolean isActive;
    private String accessLevel;

    public User(UUID uuid, String login, String password, Boolean isActive, String accessLevel) {
        this.uuid = uuid;
        this.login = login;
        this.password = password;
        this.isActive = isActive;
        this.accessLevel = accessLevel;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
}
