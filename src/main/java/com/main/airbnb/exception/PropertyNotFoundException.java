package com.main.airbnb.exception;

public class PropertyNotFoundException extends  RuntimeException{

    public PropertyNotFoundException(String message) {
        super(message);
    }
}
