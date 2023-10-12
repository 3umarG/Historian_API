package com.example.historian_api.services.base.posts;


import com.example.historian_api.dtos.responses.PostWithLikesResponseDto;

public interface LikesService {
    PostWithLikesResponseDto getAllLikesByPostId(Integer postId);

    boolean addOrRemoveLikeByPostId(Integer postID , Integer studentId);
}
