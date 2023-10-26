package com.example.historian_api.services.base.posts;

import java.util.List;

public interface PostsImagesService {
    List<String> findImagesForPost(Integer postId);
}
