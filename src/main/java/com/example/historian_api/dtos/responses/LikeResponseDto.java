package com.example.historian_api.dtos.responses;

import lombok.Builder;

@Builder
public record LikeResponseDto(
        Integer likeId,
        Integer userId ,
        String userName ,
        String authorPhotoUrl) {
}
