package com.example.historian_api.factories;


import com.example.historian_api.models.ApiCustomResponse;

public interface ResponseFactory<T> {
    ApiCustomResponse<?> createResponse(String message);
    ApiCustomResponse<T> createResponse(T data);
}
