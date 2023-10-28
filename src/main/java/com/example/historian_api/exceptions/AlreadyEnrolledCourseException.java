package com.example.historian_api.exceptions;

public class AlreadyEnrolledCourseException extends RuntimeException{
    public AlreadyEnrolledCourseException(String message) {
        super(message);
    }
}
