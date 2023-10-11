package com.example.historian_api.exceptions.auth;

import org.springframework.security.core.AuthenticationException;

public class UsedPhoneRegisterException extends AuthenticationException {
    public UsedPhoneRegisterException(String msg) {
        super(msg);
    }
}
