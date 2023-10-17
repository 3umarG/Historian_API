package com.example.historian_api.services.base.posts;


import com.example.historian_api.dtos.requests.AddReplyForPostCommentByStudentRequestDto;
import com.example.historian_api.dtos.requests.AddReplyForPostCommentByTeacherRequestDto;
import com.example.historian_api.dtos.responses.CommentResponseDto;
import com.example.historian_api.dtos.responses.PostCommentReplyResponseDto;
import com.example.historian_api.dtos.responses.PostWithCommentsResponseDto;

import java.util.List;

public interface CommentsService {

    PostWithCommentsResponseDto getAllCommentsByPostId(Integer postId);

    CommentResponseDto addCommentByPostId(Integer postId , Integer studentId , String content);

    CommentResponseDto updateCommentContentById(Integer commentId , String contentUpdated);
    CommentResponseDto deleteCommentById(Integer commentId );

    List<PostCommentReplyResponseDto> getAllRepliesByCommentId(Integer commentId);

    PostCommentReplyResponseDto addReplyToCommentForStudent(AddReplyForPostCommentByStudentRequestDto dto);

    PostCommentReplyResponseDto addReplyToCommentForTeacher(AddReplyForPostCommentByTeacherRequestDto dto);
}
