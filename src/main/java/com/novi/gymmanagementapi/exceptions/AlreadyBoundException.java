package com.novi.gymmanagementapi.exceptions;

public class AlreadyBoundException extends RuntimeException {


    public AlreadyBoundException() {
        super();
    }

    public AlreadyBoundException(String message) {
        super(message);
    }
}
