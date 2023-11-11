package com.novi.gymmanagementapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Object> exception(RecordNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyBoundException.class)
    public ResponseEntity<Object> exception(AlreadyBoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<Object> exception(EmailAlreadyTakenException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
