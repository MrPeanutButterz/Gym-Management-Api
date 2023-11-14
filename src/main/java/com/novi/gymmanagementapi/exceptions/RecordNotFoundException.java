package com.novi.gymmanagementapi.exceptions;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException() {
        super();
    }

    public RecordNotFoundException(long membershipID) {
        super("Cannot find membership with id " + membershipID);
    }
}