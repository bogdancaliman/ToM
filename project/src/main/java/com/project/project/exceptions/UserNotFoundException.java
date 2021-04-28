package com.project.project.exceptions;

public class UserNotFoundException extends LogInException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
