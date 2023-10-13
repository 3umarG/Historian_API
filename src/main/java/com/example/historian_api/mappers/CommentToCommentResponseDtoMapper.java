package com.example.historian_api.mappers;

import com.example.historian_api.dtos.responses.CommentResponseDto;
import com.example.historian_api.entities.posts.Comment;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CommentToCommentResponseDtoMapper implements Function<Comment, CommentResponseDto> {
    @Override
    public CommentResponseDto apply(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getCreationDate(),
                comment.getCreator().getId(),
                comment.getCreator().getPhotoUrl(),
                comment.getCreator().getName(),
                comment.getPost().getId()
        );
    }
}
