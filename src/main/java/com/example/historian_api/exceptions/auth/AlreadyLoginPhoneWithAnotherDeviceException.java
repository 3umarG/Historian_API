package com.example.historian_api.exceptions.auth;

import org.springframework.security.core.AuthenticationException;

public class AlreadyLoginPhoneWithAnotherDeviceException extends AuthenticationException {
    public AlreadyLoginPhoneWithAnotherDeviceException(String msg) {
        super(msg);
    }
}
