package com.example.historian_api.mappers;

import com.example.historian_api.dtos.responses.PostResponseDto;
import com.example.historian_api.entities.projections.PostWithLikesAndCommentsCountsProjection;
import com.example.historian_api.repositories.posts.BookmarksRepository;
import com.example.historian_api.repositories.posts.LikesRepository;
import com.example.historian_api.services.base.helpers.TimeSinceFormatter;
import com.example.historian_api.services.base.posts.PostsImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostProjectionToPostResponseDtoMapper {

    private final LikesRepository likesRepository;
    private final BookmarksRepository bookmarksRepository;
    private final TimeSinceFormatter timeSinceFormatter;
    private final PostsImagesService postsImagesService;


    public PostResponseDto apply(PostWithLikesAndCommentsCountsProjection post, Integer studentId) {

        var images = postsImagesService.findImagesForPost(post.getId());

        boolean isStudentLikePost = likesRepository.isStudentLikePost(studentId, post.getId());
        boolean isStudentBookmarksPost = bookmarksRepository.isStudentBookmarksPost(studentId, post.getId());

        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getTeacherId(),
                post.getAuthorName(),
                post.getAuthorPhotoUrl(),
                isStudentLikePost,
                isStudentBookmarksPost,
                post.getNumberOfComments(),
                post.getNumberOfLikes(),
                post.getCreationDate(),
                timeSinceFormatter.formatTimeSince(post.getCreationDate()),
                images
        );

    }
}
