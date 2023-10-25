package com.example.historian_api.mappers;

import com.example.historian_api.dtos.responses.CommentResponseDto;
import com.example.historian_api.entities.posts.Comment;
import com.example.historian_api.services.base.helpers.TimeSinceFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CommentToCommentResponseDtoMapper implements Function<Comment, CommentResponseDto> {

    private final TimeSinceFormatter timeSinceFormatter;


    @Override
    public CommentResponseDto apply(Comment comment) {
        var createdOn = comment.getCreationDate();
        var createdSince = timeSinceFormatter.formatTimeSince(createdOn);
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                createdOn,
                createdSince,
                comment.getCreator().getId(),
                comment.getCreator().getPhotoUrl(),
                comment.getCreator().getName(),
                comment.getPost().getId()
        );
    }
}
