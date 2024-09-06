package com.petrov.databases.exception;

public class AlreadyExistsException extends RuntimeException {
    AlreadyExistsException(String message) {
        super(message);
    }
}
