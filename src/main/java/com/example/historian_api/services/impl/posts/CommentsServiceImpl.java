package com.example.historian_api.services.impl.posts;

import com.example.historian_api.dtos.requests.AddReplyForPostCommentByStudentRequestDto;
import com.example.historian_api.dtos.requests.AddReplyForPostCommentByTeacherRequestDto;
import com.example.historian_api.dtos.responses.CommentResponseDto;
import com.example.historian_api.dtos.responses.PostCommentReplyResponseDto;
import com.example.historian_api.dtos.responses.PostWithCommentsResponseDto;
import com.example.historian_api.entities.posts.Comment;
import com.example.historian_api.entities.posts.CommentReply;
import com.example.historian_api.entities.projections.PostProjection;
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
import com.example.historian_api.services.base.helpers.TimeSinceFormatter;
import com.example.historian_api.services.base.posts.CommentsService;
import com.example.historian_api.utils.constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    private final TimeSinceFormatter timeSinceFormatter;

    @Override
    public PostWithCommentsResponseDto getAllCommentsByPostId(Integer postId) {
        var post = getPostById(postId);

        List<Comment> comments = commentsRepository.findAllByPostIdOrderByCreationDateDesc(postId);

        return generatePostWithCommentsResponseDto(post, comments);
    }

    private PostWithCommentsResponseDto generatePostWithCommentsResponseDto(PostProjection post, List<Comment> comments) {
        return new PostWithCommentsResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getTeacherId(),
                comments.stream()
                        .map(commentResponseDtoMapper)
                        .toList()
        );
    }

    private PostProjection getPostById(Integer postId) {
        return postsRepository
                .findPostById(postId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Post with that ID !!"));
    }

    @Override
    public CommentResponseDto addCommentByPostId(Integer postId, Integer studentId, String content)
            throws NotFoundPhoneNumberLoginException {

        var user = getStudentById(studentId);
        var post = postsRepository.findById(postId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Post with that id !!"));

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
        comment.setCreationDate(LocalDateTime.now());
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
        return replies.stream().map(reply -> {

            var createdSince = timeSinceFormatter.formatTimeSince(reply.getCreatedAt());

            return new PostCommentReplyResponseDto(
                    reply.getId(),
                    reply.getContent(),
                    reply.getCreatedAt(),
                    createdSince,
                    reply.getAuthorId(),
                    reply.getAuthorName(),
                    reply.getAuthorType(),
                    reply.getAuthorPhotoUrl(),
                    reply.getCommentId()
            );
        }).toList();
    }

    private String determineAuthorPhotoUrl(CommentReply reply) {
        return reply.getStudent() == null
                ? reply.getTeacher().getPhotoUrl()
                : reply.getStudent().getPhotoUrl();
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
                timeSinceFormatter.formatTimeSince(savedReply.getCreatedAt()),
                dto.studentId(),
                student.getName(),
                AuthorType.STUDENT.name(),
                student.getPhotoUrl(),
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
                null,
                dto.teacherId(),
                teacher.getName(),
                AuthorType.TEACHER.name(),
                teacher.getPhotoUrl(),
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
                null,
                determineAuthorId(reply),
                determineAuthorName(reply),
                determineAuthorType(reply).name(),
                determineAuthorPhotoUrl(reply),
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
