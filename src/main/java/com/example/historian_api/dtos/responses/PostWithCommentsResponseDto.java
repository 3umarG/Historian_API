package com.example.historian_api.dtos.responses;

import lombok.Builder;

import java.util.List;

@Builder
public record PostWithCommentsResponseDto(
        Integer postId,
        String postTitle,
        String postContent,
        Integer authorId,
        List<CommentResponseDto> comments
) {

}
