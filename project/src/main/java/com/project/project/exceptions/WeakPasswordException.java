package com.project.project.exceptions;

public class WeakPasswordException extends SignUpException {
    public WeakPasswordException(String message) {
        super(message);
    }
}