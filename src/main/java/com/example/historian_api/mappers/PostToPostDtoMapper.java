package com.example.historian_api.mappers;

import com.example.historian_api.dtos.responses.PostResponseDto;
import com.example.historian_api.entities.posts.Post;
import com.example.historian_api.entities.posts.PostImages;
import com.example.historian_api.entities.projections.PostWithLikesAndCommentsCountsProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PostToPostDtoMapper implements Function<Post, PostResponseDto> {

    public PostResponseDto apply(Post post, Boolean isStudentLike) {
        return PostResponseDto
                .builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .isLiked(isStudentLike)
                .authorId(post.getTeacher().getId())
                .authorName(post.getTeacher().getName())
                .authorPhotoUrl(post.getTeacher().getPhotoUrl())
                .build();

    }

    @Override
    public PostResponseDto apply(Post post) {
        return PostResponseDto
                .builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdOn(post.getCreationDate())
                .authorId(post.getTeacher().getId())
                .authorName(post.getTeacher().getName())
                .authorPhotoUrl(post.getTeacher().getPhotoUrl())
                .build();
    }

    public PostResponseDto apply(PostWithLikesAndCommentsCountsProjection post, List<String> images, Boolean isStudentLikePost) {
        return PostResponseDto
                .builder()
                .id(post.getId())
                .images(images)
                .numberOfLikes(post.getNumberOfLikes())
                .numberOfComments(post.getNumberOfComments())
                .isLiked(isStudentLikePost)
                .title(post.getTitle())
                .createdOn(post.getCreationDate())
                .content(post.getContent())
                .authorId(post.getTeacherId())
                .authorName(post.getAuthorName())
                .authorPhotoUrl(post.getAuthorPhotoUrl())
                .build();
    }
}
