package com.project.project.exceptions;

public class InvalidTokenException extends LogInException{
    public InvalidTokenException(String message) {
        super(message);
    }
}