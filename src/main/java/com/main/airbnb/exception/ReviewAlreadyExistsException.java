package com.main.airbnb.exception;

public class ReviewAlreadyExistsException extends  RuntimeException{
    public ReviewAlreadyExistsException(String message) {
        super(message);
    }
}
