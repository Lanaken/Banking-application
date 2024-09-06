package com.petrov.databases.exception;

public class DebitAccountAlreadyExistsException extends AlreadyExistsException {
    public DebitAccountAlreadyExistsException() {
        super("Debit Account Already Exists");
    }

    @Override
    public String toString(){
        return this.getMessage();
    }
}
