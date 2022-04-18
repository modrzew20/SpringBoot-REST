package com.example.springbootrest.exceptions;

public class LoginInUseException extends Exception {
    public LoginInUseException(String errorMessage) {
        super(errorMessage);
    }
}
