package com.example.historian_api.dtos.responses;

import com.example.historian_api.entities.posts.Comment;
import com.example.historian_api.entities.posts.Post;
import com.example.historian_api.mappers.CommentToCommentResponseDtoMapper;
import com.example.historian_api.mappers.PostToPostDtoMapper;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record PostWithCommentsResponseDto(
        PostResponseDto post,
        List<CommentResponseDto> comments
) {

    private static PostToPostDtoMapper postDtoMapper;
    private static CommentToCommentResponseDtoMapper commentResponseDtoMapper;

    public static PostWithCommentsResponseDto generatePostWithComments(Post post, List<Comment> comments) {
        return PostWithCommentsResponseDto
                .builder()
                .post(postDtoMapper.apply(post))
                .comments(comments
                        .stream()
                        .map(commentResponseDtoMapper)
                        .collect(Collectors.toList()))
                .build();
    }
}
