package com.example.historian_api.mappers;

import com.example.historian_api.dtos.responses.LikeResponseDto;
import com.example.historian_api.entities.posts.Like;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class LikeToLikeResponseDtoMapper implements Function<Like, LikeResponseDto> {
    @Override
    public LikeResponseDto apply(Like like) {
        return new LikeResponseDto(
                like.getId(),
                like.getCreator().getId(),
                like.getCreator().getName(),
                like.getCreator().getPhotoUrl());
    }
}
