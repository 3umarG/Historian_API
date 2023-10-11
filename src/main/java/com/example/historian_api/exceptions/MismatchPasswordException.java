package com.example.historian_api.exceptions;

public class MismatchPasswordException extends RuntimeException {

    public MismatchPasswordException(String message) {
        super(message);
    }
}
