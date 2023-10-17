package com.example.historian_api.services.impl.posts;

import com.example.historian_api.dtos.requests.AddReplyForPostCommentByStudentRequestDto;
import com.example.historian_api.dtos.requests.AddReplyForPostCommentByTeacherRequestDto;
import com.example.historian_api.dtos.responses.CommentResponseDto;
import com.example.historian_api.dtos.responses.PostCommentReplyResponseDto;
import com.example.historian_api.dtos.responses.PostWithCommentsResponseDto;
import com.example.historian_api.entities.posts.Comment;
import com.example.historian_api.entities.posts.CommentReply;
import com.example.historian_api.entities.posts.Post;
import com.example.historian_api.entities.users.Student;
import com.example.historian_api.enums.AuthorType;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.exceptions.auth.NotFoundPhoneNumberLoginException;
import com.example.historian_api.mappers.CommentToCommentResponseDtoMapper;
import com.example.historian_api.repositories.posts.CommentRepliesRepository;
import com.example.historian_api.repositories.posts.CommentsRepository;
import com.example.historian_api.repositories.posts.PostsRepository;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.repositories.users.TeachersRepository;
import com.example.historian_api.services.base.posts.CommentsService;
import com.example.historian_api.utils.constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;
    private final PostsRepository postsRepository;
    private final CommentToCommentResponseDtoMapper commentResponseDtoMapper;
    private final StudentsRepository studentsRepository;
    private final CommentRepliesRepository commentRepliesRepository;
    private final TeachersRepository teachersRepository;

    @Override
    public PostWithCommentsResponseDto getAllCommentsByPostId(Integer postId) {
        Post post = getPostById(postId);

        List<Comment> comments = commentsRepository.findAllByPostIdOrderByCreationDateDesc(postId);

        return generatePostWithCommentsResponseDto(post, comments);
    }

    private PostWithCommentsResponseDto generatePostWithCommentsResponseDto(Post post, List<Comment> comments) {
        return new PostWithCommentsResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getTeacher().getId(),
                comments.stream()
                        .map(commentResponseDtoMapper)
                        .toList()
        );
    }

    private Post getPostById(Integer postId) {
        return postsRepository
                .findById(postId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Post with that ID !!"));
    }

    @Override
    public CommentResponseDto addCommentByPostId(Integer postId, Integer studentId, String content)
            throws NotFoundPhoneNumberLoginException {

        var user = getStudentById(studentId);
        var post = getPostById(postId);

        Comment comment = commentsRepository.save(new Comment(content, user, post));
        CommentResponseDto responseDto = commentResponseDtoMapper.apply(comment);

        return responseDto;
    }

    private Student getStudentById(Integer studentId) {
        var user = studentsRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Student with that id !!"));
        return user;
    }

    @Override
    public CommentResponseDto updateCommentContentById(Integer commentId, String contentUpdated) {

        Comment comment = getCommentById(commentId);

        updateCommentContent(comment, contentUpdated);

        commentsRepository.save(comment);

        return commentResponseDtoMapper.apply(comment);
    }

    private static void updateCommentContent(Comment comment, String contentUpdated) {
        comment.setContent(contentUpdated);
        comment.setCreationDate(LocalDate.now());
    }

    private Comment getCommentById(Integer commentId) {
        Comment comment = commentsRepository
                .findById(commentId)
                .orElseThrow(() -> new NotFoundResourceException(ExceptionMessages.NOT_FOUND_EXCEPTION_MSG));
        return comment;
    }

    @Override
    public CommentResponseDto deleteCommentById(Integer commentId) {
        Comment comment = getCommentById(commentId);

        commentsRepository.deleteById(commentId);

        return commentResponseDtoMapper.apply(comment);
    }

    @Override
    public List<PostCommentReplyResponseDto> getAllRepliesByCommentId(Integer commentId) {
        if (!isExistsCommentId(commentId))
            throw new NotFoundResourceException("There is no Comment with this id !!");

        var replies = commentRepliesRepository.findAllByCommentIdOrderByCreatedAt(commentId);
        return replies.stream().map(reply ->
                new PostCommentReplyResponseDto(
                        reply.getId(),
                        reply.getContent(),
                        reply.getCreatedAt(),
                        determineAuthorId(reply),
                        determineAuthorName(reply),
                        determineAuthorType(reply),
                        commentId
                )).toList();
    }

    @Override
    public PostCommentReplyResponseDto addReplyToCommentForStudent(AddReplyForPostCommentByStudentRequestDto dto) {
        var comment = findComment(dto.commentId());

        var student = studentsRepository.findById(dto.studentId())
                .orElseThrow(() -> new NotFoundResourceException("There is no Student with that id !!"));

        var reply = new CommentReply(
                dto.content(),
                LocalDateTime.now(),
                student,
                comment
        );

        var savedReply = commentRepliesRepository.save(reply);

        return new PostCommentReplyResponseDto(
                savedReply.getId(),
                savedReply.getContent(),
                savedReply.getCreatedAt(),
                dto.studentId(),
                student.getName(),
                AuthorType.STUDENT,
                dto.commentId()
        );
    }

    @Override
    public PostCommentReplyResponseDto addReplyToCommentForTeacher(AddReplyForPostCommentByTeacherRequestDto dto) {
        var comment = findComment(dto.commentId());

        var teacher = teachersRepository.findById(dto.teacherId())
                .orElseThrow(() -> new NotFoundResourceException("There is no Teacher with that id !!"));

        var reply = new CommentReply(
                dto.content(),
                LocalDateTime.now(),
                teacher,
                comment
        );

        var savedReply = commentRepliesRepository.save(reply);

        return new PostCommentReplyResponseDto(
                savedReply.getId(),
                savedReply.getContent(),
                savedReply.getCreatedAt(),
                dto.teacherId(),
                teacher.getName(),
                AuthorType.TEACHER,
                dto.commentId()
        );

    }

    @Override
    public PostCommentReplyResponseDto deleteReplyById(Integer replyId) {
        var reply = commentRepliesRepository.findById(replyId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Reply with that id !!"));

        commentRepliesRepository.deleteById(replyId);

        return new PostCommentReplyResponseDto(
                reply.getId(),
                reply.getContent(),
                reply.getCreatedAt(),
                determineAuthorId(reply),
                determineAuthorName(reply),
                determineAuthorType(reply),
                reply.getComment().getId()
        );
    }

    private Comment findComment(Integer dto) {
        var comment = commentsRepository.findById(dto)
                .orElseThrow(() -> new NotFoundResourceException("There is no Comment with that id !!"));
        return comment;
    }

    private boolean isExistsStudent(Integer id) {
        return studentsRepository.existsById(id);
    }

    private static String determineAuthorName(CommentReply reply) {
        return reply.getStudent() == null
                ? reply.getTeacher().getName()
                : reply.getStudent().getName();
    }

    private static AuthorType determineAuthorType(CommentReply reply) {
        return reply.getStudent() == null
                ? AuthorType.TEACHER
                : AuthorType.STUDENT;
    }

    private static Integer determineAuthorId(CommentReply reply) {
        return reply.getStudent() == null
                ? reply.getTeacher().getId()
                : reply.getStudent().getId();
    }

    private boolean isExistsCommentId(Integer commentId) {
        return commentsRepository.existsById(commentId);
    }
}
