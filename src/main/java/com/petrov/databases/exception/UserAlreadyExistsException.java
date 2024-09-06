package com.petrov.databases.exception;

public class UserAlreadyExistsException extends AlreadyExistsException {
    public UserAlreadyExistsException() {
        super("User already exists");
    }

    @Override
    public String toString(){
        return this.getMessage();
    }
}
