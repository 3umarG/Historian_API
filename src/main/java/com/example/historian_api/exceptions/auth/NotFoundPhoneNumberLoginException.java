package com.example.historian_api.exceptions.auth;

import org.springframework.security.core.AuthenticationException;

public class NotFoundPhoneNumberLoginException extends AuthenticationException {
    public NotFoundPhoneNumberLoginException(String msg) {
        super(msg);
    }
}
