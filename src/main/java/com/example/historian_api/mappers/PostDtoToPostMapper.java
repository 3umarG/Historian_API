package com.example.historian_api.mappers;

import com.example.historian_api.dtos.requests.PostRequestDto;
import com.example.historian_api.entities.posts.Post;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Function;

@Service
public class PostDtoToPostMapper implements Function<PostRequestDto, Post> {
    @Override
    public Post apply(PostRequestDto dto) {
        return Post.builder()
                .title(dto.title())
                .content(dto.content())
                .creationDate(LocalDate.now())
                .build();
    }
}
