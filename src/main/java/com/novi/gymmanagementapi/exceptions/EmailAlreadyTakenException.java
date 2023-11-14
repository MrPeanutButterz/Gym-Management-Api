package com.novi.gymmanagementapi.exceptions;

public class EmailAlreadyTakenException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmailAlreadyTakenException() {
        super();
    }

    public EmailAlreadyTakenException(String email) {
        super("Email address " + email + " already in use");
    }
}
