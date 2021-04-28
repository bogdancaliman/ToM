package com.project.project.exceptions;

import java.io.IOException;

public class FileStorageException extends Exception {
    public FileStorageException(String message, IOException e) {
        super(message, e);
    }
}
