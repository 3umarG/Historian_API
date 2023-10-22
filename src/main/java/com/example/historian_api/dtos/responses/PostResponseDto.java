package com.example.historian_api.dtos.responses;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PostResponseDto(
        Integer id,
        String title,
        String content,
        Integer authorId,
        Boolean isLiked,
        Boolean isBookmarked,
        Integer numberOfComments,
        Integer numberOfLikes,
        LocalDateTime createdOn,
        String createdSince,
        List<String> images
) {
}
