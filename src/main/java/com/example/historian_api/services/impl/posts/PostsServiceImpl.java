package com.example.historian_api.services.impl.posts;

import com.example.historian_api.dtos.requests.PostRequestDto;
import com.example.historian_api.dtos.responses.PostResponseDto;
import com.example.historian_api.entities.posts.Post;
import com.example.historian_api.entities.posts.PostImages;
import com.example.historian_api.entities.projections.PostWithLikesAndCommentsCountsProjection;
import com.example.historian_api.entities.users.Teacher;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.mappers.PostDtoToPostMapper;
import com.example.historian_api.mappers.PostToPostDtoMapper;
import com.example.historian_api.repositories.posts.BookmarksRepository;
import com.example.historian_api.repositories.posts.LikesRepository;
import com.example.historian_api.repositories.posts.PostsImagesRepository;
import com.example.historian_api.repositories.posts.PostsRepository;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.repositories.users.TeachersRepository;
import com.example.historian_api.services.base.helpers.TimeSinceFormatter;
import com.example.historian_api.services.base.posts.PostsImagesService;
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
    private final ImageUtils imageUtils;
    private final LikesRepository likesRepository;
    private final PostsImagesService postsImagesService;
    private final BookmarksRepository bookmarksRepository;
    private final StudentsRepository studentsRepository;
    private final TimeSinceFormatter timeSinceFormatter;

    @Override
    public List<PostResponseDto> getAll(Integer studentId) {
        var student = studentsRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Student with that id !!"));

        List<PostWithLikesAndCommentsCountsProjection> posts = postsRepository.findAllPosts();

        return posts.stream().map(post -> {

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

        }).toList();


    }

    @Override
    public PostResponseDto getPostById(@NotNull Integer id) throws NotFoundResourceException {

        var postProjection = postsRepository
                .findPostWithLikesAndCommentsCountById(id)
                .orElseThrow(() ->
                        new NotFoundResourceException(ExceptionMessages.NOT_FOUND_EXCEPTION_MSG));

        var images = postsImagesService.findImagesForPost(id);

        return postDtoMapper.apply(postProjection, images, null);

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
    public PostResponseDto updatePostById(@NotNull Integer id, @NotNull PostRequestDto dto) throws
            NotFoundResourceException {

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
