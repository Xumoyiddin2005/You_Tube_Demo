package com.company.exceptions;

public class ProfileAlreadyExists extends RuntimeException {
    public ProfileAlreadyExists(String message) {
        super(message);
    }
}
