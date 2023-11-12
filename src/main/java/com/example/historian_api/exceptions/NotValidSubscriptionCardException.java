package com.example.historian_api.exceptions;

public class NotValidSubscriptionCardException extends RuntimeException{
    public NotValidSubscriptionCardException(String message) {
        super(message);
    }
}
