package com.pact.stubber.exceptions;

public class StubberServerException extends RuntimeException {

    //private String message;
    public StubberServerException(String message){
        super(message);
    }
}
