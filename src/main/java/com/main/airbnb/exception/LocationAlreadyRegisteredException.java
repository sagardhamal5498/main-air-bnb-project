package com.main.airbnb.exception;

public class LocationAlreadyRegisteredException extends RuntimeException{
    public LocationAlreadyRegisteredException(String message) {
        super(message);
    }
}
