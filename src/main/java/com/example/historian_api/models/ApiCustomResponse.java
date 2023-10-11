package com.example.historian_api.models;

import lombok.Builder;

@Builder
public record ApiCustomResponse<T>(String message, int statusCode, Boolean isSuccess, T data) {
}
