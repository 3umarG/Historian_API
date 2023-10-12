package com.example.historian_api.dtos.responses;

import lombok.Builder;

import java.util.List;

// TODO : modify this for only the count of likes and comments.
@Builder
public record PostResponseDto(
        Integer id,
        String title,
        String content,
        Integer authorId,
        Boolean isLiked,
        List<CommentResponseDto> comments,
        List<LikeResponseDto> likes,
        List<String> images
) {
}
