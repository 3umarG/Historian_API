package com.example.historian_api.services.base.posts;


import com.example.historian_api.dtos.responses.CommentResponseDto;
import com.example.historian_api.dtos.responses.PostWithCommentsResponseDto;

public interface CommentsService {

    PostWithCommentsResponseDto getAllCommentsByPostId(Integer postId);

    CommentResponseDto addCommentByPostId(Integer postId , Integer studentId , String content);

    CommentResponseDto updateCommentContentById(Integer commentId , String contentUpdated);
    CommentResponseDto deleteCommentById(Integer commentId );

}
