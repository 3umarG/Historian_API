package com.example.historian_api.mappers;

import com.example.historian_api.dtos.responses.PostResponseDto;
import com.example.historian_api.entities.posts.Post;
import com.example.historian_api.entities.posts.PostImages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PostToPostDtoMapper implements Function<Post, PostResponseDto> {

    private final CommentToCommentResponseDtoMapper commentResponseDtoMapper;
    private final LikeToLikeResponseDtoMapper likeMapper;

    public PostResponseDto apply(Post post , Boolean isStudentLike){
        return PostResponseDto
                .builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .isLiked(isStudentLike)
                .authorId(post.getTeacher().getId())
                .comments(
                        post.getComments() != null
                                ? post.getComments().stream().map(commentResponseDtoMapper).toList()
                                : new ArrayList<>())
                .likes(
                        post.getLikes() != null
                                ? post.getLikes().stream().map(likeMapper).toList()
                                : new ArrayList<>())
                .images(
                        post.getPostImages() != null
                                ? post.getPostImages().stream().map(PostImages::getPhotoUrl).toList()
                                : new ArrayList<>())
                .build();

    }
    @Override
    public PostResponseDto apply(Post post) {
        return PostResponseDto
                .builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorId(post.getTeacher().getId())
                .comments(
                        post.getComments() != null
                                ? post.getComments().stream().map(commentResponseDtoMapper).toList()
                                : new ArrayList<>())
                .likes(
                        post.getLikes() != null
                                ? post.getLikes().stream().map(likeMapper).toList()
                                : new ArrayList<>())
                .images(
                        post.getPostImages() != null
                                ? post.getPostImages().stream().map(PostImages::getPhotoUrl).toList()
                                : new ArrayList<>())
                .build();
    }
}
