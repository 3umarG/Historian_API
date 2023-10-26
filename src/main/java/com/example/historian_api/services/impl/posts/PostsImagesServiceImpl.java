package com.example.historian_api.services.impl.posts;

import com.example.historian_api.repositories.posts.PostsImagesRepository;
import com.example.historian_api.services.base.posts.PostsImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsImagesServiceImpl implements PostsImagesService {

    private final PostsImagesRepository postsImagesRepository;


    @Override
    public List<String> findImagesForPost(Integer post) {
        return postsImagesRepository
                .findAllPhotoUrlsByPostId(post);
    }
}
