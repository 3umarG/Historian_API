package com.example.historian_api.services.impl.posts;

import com.example.historian_api.dtos.requests.PostRequestDto;
import com.example.historian_api.dtos.responses.CommentResponseDto;
import com.example.historian_api.dtos.responses.LikeResponseDto;
import com.example.historian_api.dtos.responses.PostResponseDto;
import com.example.historian_api.entities.posts.Post;
import com.example.historian_api.entities.posts.PostImages;
import com.example.historian_api.entities.users.Teacher;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.mappers.CommentToCommentResponseDtoMapper;
import com.example.historian_api.mappers.LikeToLikeResponseDtoMapper;
import com.example.historian_api.mappers.PostDtoToPostMapper;
import com.example.historian_api.mappers.PostToPostDtoMapper;
import com.example.historian_api.repositories.posts.PostsRepository;
import com.example.historian_api.repositories.posts.PostsImagesRepository;
import com.example.historian_api.repositories.users.TeachersRepository;
import com.example.historian_api.services.base.posts.PostsService;
import com.example.historian_api.utils.ImageUtils;
import com.example.historian_api.utils.constants.ExceptionMessages;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {

    private final PostsRepository postsRepository;
    private final PostToPostDtoMapper postDtoMapper;
    private final PostDtoToPostMapper postMapper;
    private final PostsImagesRepository imagesRepository;
    private final TeachersRepository teachersRepository;
    private final LikeToLikeResponseDtoMapper likeMapper;
    private final CommentToCommentResponseDtoMapper commentResponseDtoMapper;
    private final ImageUtils imageUtils;

    // TODO : will change ...
    @Override
    public List<PostResponseDto> getAll(Integer studentId) {

        List<Post> posts = postsRepository.findAllPosts();
        return posts.stream().map(post -> {

            List<LikeResponseDto> likes = getLikeResponseDtos(post);

            boolean isStudentLikePost = isStudentLikesPost(studentId, likes);

            return postDtoMapper.apply(post, isStudentLikePost);
        }).toList();

    }

    private static boolean isStudentLikesPost(Integer studentId, List<LikeResponseDto> likes) {
        return likes
                .stream()
                .map(LikeResponseDto::userId)
                .anyMatch(id -> id.equals(studentId));
    }

    private List<CommentResponseDto> getCommentResponseDtos(Post post) {
        List<CommentResponseDto>
                comments = post.getComments() != null
                ? post.getComments().stream().map(commentResponseDtoMapper).toList()
                : new ArrayList<>();
        return comments;
    }

    private List<LikeResponseDto> getLikeResponseDtos(Post post) {
        List<LikeResponseDto> likes =
                post.getLikes() != null
                        ? post.getLikes().stream().map(likeMapper).toList()
                        : new ArrayList<>();
        return likes;
    }

    @Override
    public PostResponseDto getPostById(@NotNull Integer id) throws NotFoundResourceException {
        return postDtoMapper.apply(
                postsRepository
                        .findPostById(id)
                        .orElseThrow(() ->
                                new NotFoundResourceException(ExceptionMessages.NOT_FOUND_EXCEPTION_MSG))
        );
    }

    @Override
    public PostResponseDto addPost(@NotNull PostRequestDto dto) throws NotFoundResourceException {

        Post post = postMapper.apply(dto);

        Teacher teacher = fetchTeacherWithID(dto.authorId());
        post.setTeacher(teacher);


        List<PostImages> postImages = new ArrayList<>();
        if (dto.photos() != null) {
            generatePostImages(Arrays.stream(dto.photos()).toList(), post, postImages);
        }

        if (!postImages.isEmpty()) {
            post.setPostImages(postImages);
        }

        post.setComments(new ArrayList<>());
        post.setLikes(new ArrayList<>());

        Post result = postsRepository.save(post);

        return postDtoMapper.apply(result);
    }

    private void generatePostImages(List<MultipartFile> photos, Post post, List<PostImages> postImages) {
        photos.stream()
                .map(imageFile -> {
                    try {
                        String imageTitle = imageFile.getName()
                                            + "-"
                                            + UUID.randomUUID();

                        return new PostImages(
                                post,
                                imageUtils.compressImage(imageFile.getBytes()),
                                imageTitle,
                                imageUtils.generateImagePath("posts/images", imageTitle));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).forEach(postImages::add);
    }

    private Teacher fetchTeacherWithID(Integer id) {
        return teachersRepository
                .findById(id)
                .orElseThrow(() ->
                        new NotFoundResourceException(ExceptionMessages.NOT_FOUND_EXCEPTION_MSG));
    }

    @Override
    public PostResponseDto updatePostById(@NotNull Integer id, @NotNull PostRequestDto dto) throws NotFoundResourceException {

        // check for the post
        Post post = fetchPostWithId(id);

        post.setTitle(dto.title());
        post.setContent(dto.content());

        return postDtoMapper.apply(postsRepository.save(post));
    }

    private Post fetchPostWithId(Integer id) {
        return postsRepository
                .findById(id)
                .orElseThrow(() ->
                        new NotFoundResourceException(ExceptionMessages.NOT_FOUND_EXCEPTION_MSG));
    }

    @Override
    public PostResponseDto deletePostById(Integer id) throws NotFoundResourceException {
        PostResponseDto post = getPostById(id);
        postsRepository.deleteById(id);
        return post;
    }


    @Override
    public byte[] downloadPostImage(String title) {
        PostImages dbUserImage = imagesRepository
                .findByTitle(title)
                .orElseThrow(() -> new NotFoundResourceException(ExceptionMessages.NOT_FOUND_EXCEPTION_MSG));

        return imageUtils.decompressImage(dbUserImage.getData());
    }
}
