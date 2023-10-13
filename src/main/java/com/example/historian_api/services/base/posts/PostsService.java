package com.example.historian_api.services.base.posts;


import com.example.historian_api.dtos.requests.PostRequestDto;
import com.example.historian_api.dtos.responses.PostResponseDto;
import com.example.historian_api.exceptions.NotFoundResourceException;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostsService {

    List<PostResponseDto> getAll(Integer studentId);
    PostResponseDto getPostById(@NotNull Integer id) throws NotFoundResourceException;
    PostResponseDto addPost(@NotNull PostRequestDto dto ) throws NotFoundResourceException;
    PostResponseDto updatePostById(@NotNull Integer id , @NotNull PostRequestDto dto) throws NotFoundResourceException;
    PostResponseDto deletePostById(@NotNull Integer id) throws NotFoundResourceException;
    byte[] downloadPostImage(String title);
}
