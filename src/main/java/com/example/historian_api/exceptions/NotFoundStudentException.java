package com.example.historian_api.exceptions;

public class NotFoundStudentException extends RuntimeException{
    public NotFoundStudentException(String message) {
        super(message);
    }
}
