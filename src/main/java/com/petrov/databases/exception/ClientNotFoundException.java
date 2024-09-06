package com.petrov.databases.exception;


public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException() {
        super("Client was not found");
    }

    @Override
    public String toString(){
        return this.getMessage();
    }
}
