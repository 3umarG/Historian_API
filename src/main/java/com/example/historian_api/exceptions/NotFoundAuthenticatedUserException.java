package com.example.historian_api.exceptions;

import org.springframework.security.core.AuthenticationException;

public class NotFoundAuthenticatedUserException extends AuthenticationException {
    public NotFoundAuthenticatedUserException(String msg) {
        super(msg);
    }
}
