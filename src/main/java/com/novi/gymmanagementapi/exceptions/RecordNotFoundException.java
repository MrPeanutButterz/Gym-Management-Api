package com.novi.gymmanagementapi.exceptions;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException() {
        super();
    }

    public RecordNotFoundException(long id) {
        super("Unable to find record ID:" + id);
    }
}