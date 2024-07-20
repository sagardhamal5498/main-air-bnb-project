package com.main.airbnb.exception;

public class CountryAlreadyRegisteredException extends RuntimeException{
    public CountryAlreadyRegisteredException(String message) {
        super(message);
    }
}
