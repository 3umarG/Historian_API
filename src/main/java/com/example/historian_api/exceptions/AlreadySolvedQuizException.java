package com.example.historian_api.exceptions;

public class AlreadySolvedQuizException extends RuntimeException{
    public AlreadySolvedQuizException(String message) {
        super(message);
    }
}
