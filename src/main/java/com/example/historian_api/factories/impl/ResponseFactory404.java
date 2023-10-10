package com.example.historian_api.factories.impl;

import com.example.historian_api.factories.ResponseFactory;
import com.example.historian_api.models.ApiCustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ResponseFactory404 implements ResponseFactory<Object> {
    @Override
    public ApiCustomResponse<?> createResponse(String message) {
        return ApiCustomResponse.builder()
                .message(message)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .isSuccess(false)
                .build();
    }

    @Override
    public ApiCustomResponse<Object> createResponse(Object data) {
        return ApiCustomResponse.builder()
                .data(data)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .isSuccess(false)
                .build();
    }
}
