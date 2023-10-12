package com.example.historian_api.dtos.responses;

import lombok.Builder;

import java.util.List;

// TODO : will change this model later
@Builder
public record PostWithLikesResponseDto(
        PostResponseDto post, List<LikeResponseDto> usersLikes) {
}
