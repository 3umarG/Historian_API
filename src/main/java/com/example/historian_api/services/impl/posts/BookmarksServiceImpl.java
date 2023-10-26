package com.example.historian_api.services.impl.posts;

import com.example.historian_api.dtos.responses.BookmarkResponseDto;
import com.example.historian_api.dtos.responses.PostResponseDto;
import com.example.historian_api.entities.keys.PostsBookmarksKey;
import com.example.historian_api.entities.posts.Bookmark;
import com.example.historian_api.entities.posts.Post;
import com.example.historian_api.entities.posts.PostImages;
import com.example.historian_api.entities.projections.PostWithLikesAndCommentsCountsProjection;
import com.example.historian_api.entities.users.Student;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.mappers.PostProjectionToPostResponseDtoMapper;
import com.example.historian_api.repositories.posts.BookmarksRepository;
import com.example.historian_api.repositories.posts.LikesRepository;
import com.example.historian_api.repositories.posts.PostsImagesRepository;
import com.example.historian_api.repositories.posts.PostsRepository;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.services.base.helpers.TimeSinceFormatter;
import com.example.historian_api.services.base.posts.BookmarksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarksServiceImpl implements BookmarksService {

    private final BookmarksRepository bookmarksRepository;
    private final StudentsRepository studentsRepository;
    private final PostsRepository postsRepository;
    private final PostProjectionToPostResponseDtoMapper postProjectionMapper;


    @Override
    public BookmarkResponseDto addOrDeletePostBookMarkForStudent(Integer postId, Integer studentId) {

        var student = findStudentById(studentId);

        var post = findPostById(postId);

        Bookmark bookmark = getBookmarkIfExists(student, post);

        return generateBookmarkWithoutImagesResponseDto(bookmark);
    }

    private BookmarkResponseDto generateBookmarkWithoutImagesResponseDto(Bookmark bookmark) {
        return new BookmarkResponseDto(
                bookmark.getPost().getId(),
                bookmark.getPost().getTitle(),
                bookmark.getPost().getContent(),
                bookmark.getStudent().getId(),
                getImagesFromPost(bookmark.getPost().getPostImages()),
                bookmark.getSavedOn()
        );
    }

    private List<String> getImagesFromPost(List<PostImages> postImages) {
        if (postImages == null)
            return new ArrayList<>();

        return postImages.stream()
                .map(PostImages::getPhotoUrl)
                .toList();
    }

    private Bookmark getBookmarkIfExists(Student student, Post post) {
        var existedBookmark = findBookmarkWithPostIdAndStudentId(post.getId(), student.getId());

        if (existedBookmark.isPresent()) {
            // student has a bookmark with that post, then delete it
            bookmarksRepository.delete(existedBookmark.get());
            return existedBookmark.get();
        } else {
            // student doesn't have this post as bookmark, then add new one
            var key = new PostsBookmarksKey(post.getId(), student.getId());
            var bookmark = new Bookmark(key, student, post, LocalDate.now());
            return bookmarksRepository.save(bookmark);
        }
    }

    private Optional<Bookmark> findBookmarkWithPostIdAndStudentId(Integer postId, Integer studentId) {
        var existedBookmark = bookmarksRepository.findByPost_IdAndStudent_Id(postId, studentId);
        return existedBookmark;
    }

    private Post findPostById(Integer postId) {
        var post = postsRepository.findById(postId)
                .orElseThrow(() -> new NotFoundResourceException("There is no post with that id !!"));
        return post;
    }

    private Student findStudentById(Integer studentId) {
        var student = studentsRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourceException("There is no student with that id !!"));
        return student;
    }

    @Override
    public List<PostResponseDto> getBookMarksForStudentId(Integer studentId) {

        var student = studentsRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Student with that id !!"));

        List<PostWithLikesAndCommentsCountsProjection> posts = postsRepository.findBookmarkedPostsByStudentId(studentId);

        return posts
                .stream()
                .map(post -> postProjectionMapper.apply(post, studentId))
                .toList();

    }


}
