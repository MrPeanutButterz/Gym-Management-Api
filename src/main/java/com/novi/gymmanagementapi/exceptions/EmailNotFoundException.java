package com.novi.gymmanagementapi.exceptions;

public class EmailNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public EmailNotFoundException() {
        super();
    }
    public EmailNotFoundException(String email) {
        super("Cannot find user with email " + email);
    }
}