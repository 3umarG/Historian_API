package com.example.historian_api.services.impl.posts;

import com.example.historian_api.dtos.responses.PostWithLikesResponseDto;
import com.example.historian_api.entities.posts.Like;
import com.example.historian_api.entities.posts.Post;
import com.example.historian_api.entities.users.Student;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.mappers.LikeToLikeResponseDtoMapper;
import com.example.historian_api.mappers.PostToPostDtoMapper;
import com.example.historian_api.repositories.posts.LikesRepository;
import com.example.historian_api.repositories.posts.PostsRepository;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.services.base.posts.LikesService;
import com.example.historian_api.utils.constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;
    private final PostsRepository postsRepository;
    private final PostToPostDtoMapper dtoMapper;
    private final LikeToLikeResponseDtoMapper likeResponseDtoMapper;
    private final StudentsRepository studentsRepository;

    @Override
    public PostWithLikesResponseDto getAllLikesByPostId(Integer postId) {

        Post post = fetchPostWithId(postId);
        List<Like> likesForPostId = likesRepository.findAllByPost_IdOrderByIdDesc(postId);

        PostWithLikesResponseDto responseDto = generatePostLikesResponseDto(post, likesForPostId);

        return responseDto;
    }

    private PostWithLikesResponseDto generatePostLikesResponseDto(Post post, List<Like> likesForPostId) {
        return new PostWithLikesResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getTeacher().getId(),
                likesForPostId
                        .stream()
                        .map(likeResponseDtoMapper)
                        .collect(Collectors.toList()));
    }

    private Post fetchPostWithId(Integer postId) {
        return postsRepository
                .findById(postId)
                .orElseThrow(() -> new NotFoundResourceException(ExceptionMessages.NOT_FOUND_EXCEPTION_MSG));

    }

    @Override
    public boolean addOrRemoveLikeByPostId(Integer postID, Integer studentId) {

        Student student = studentsRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourceException("There is no student with that id !!"));


        Post post = postsRepository.findById(postID)
                .orElseThrow(() -> new NotFoundResourceException(ExceptionMessages.NOT_FOUND_EXCEPTION_MSG));

        Optional<Like> existedLike = likesRepository.getLikeWithPostIdAndAndCreatorId(postID, studentId);

        if (existedLike.isPresent()) {
            likesRepository.deleteById(existedLike.get().getId());
        } else {
            Like newLike = new Like(student, post);
            likesRepository.save(newLike);
        }
        return true;
    }
}
