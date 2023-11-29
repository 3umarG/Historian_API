package com.example.historian_api.services.impl.courses;

import com.example.historian_api.dtos.requests.LessonCommentRequestDto;
import com.example.historian_api.dtos.responses.CourseResponseDto;
import com.example.historian_api.dtos.responses.EnrolledCourseResponseDto;
import com.example.historian_api.dtos.responses.LessonResponseDto;
import com.example.historian_api.dtos.responses.VideoCommentResponseDto;
import com.example.historian_api.entities.courses.VideoComment;
import com.example.historian_api.enums.AuthorType;
import com.example.historian_api.enums.CourseTakenState;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.*;
import com.example.historian_api.repositories.grades.StudentGradesRepository;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.services.base.courses.CoursesService;
import com.example.historian_api.services.base.courses.EnrollmentCoursesService;
import com.example.historian_api.services.base.courses.LessonsService;
import com.example.historian_api.services.base.courses.UnitsService;
import com.example.historian_api.services.base.helpers.TimeSinceFormatter;
import com.example.historian_api.utils.constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursesServiceImpl implements CoursesService {

    private final LessonsService lessonsService;
    private final CoursesRepository coursesRepository;
    private final UnitsService unitsService;
    private final StudentsRepository studentsRepository;
    private final StudentGradesRepository gradesRepository;
    private final UnitsRepository unitsRepository;
    private final VideosCommentsRepository videosCommentsRepository;
    private final TimeSinceFormatter timeSinceFormatter;
    private final EnrollmentCoursesService enrollmentCoursesService;
    private final LessonsRepository lessonsRepository;
    private final GradesSemestersRepository semestersRepository;


    @Override
    public List<CourseResponseDto> getAllCoursesByGradeIdForStudent(Integer semesterId, Integer studentId) {

        checkForExistedSemester(semesterId);

        checkForExistedStudent(studentId);

        var coursesProjection = coursesRepository.getAllCoursesBySemesterIdWithEnrollmentStateForStudent(semesterId, studentId);

        return coursesProjection.stream().map(course -> {
            var unitsForCourse = unitsService.getAllUnitsByCourseId(course.getCourseId());
            return new CourseResponseDto(
                    course.getCourseId(),
                    course.getCourseTitle(),
                    course.getCourseEnrollmentState(),
                    unitsForCourse
            );
        }).toList();
    }

    private void checkForExistedSemester(Integer semesterId) {
        if (!isExistedSemester(semesterId)) {
            throw new NotFoundResourceException(ExceptionMessages.getNotFoundResourceMessage("Semester"));
        }
    }

    private void checkForExistedStudent(Integer studentId) {
        if (isNotFoundStudent(studentId)) {
            throw new NotFoundResourceException("There is no Student with that id !!");
        }
    }

    @Override
    public List<CourseResponseDto> getAllSubscribedCoursesForStudent(Integer studentId) {

        checkForExistedStudent(studentId);

        var courses = coursesRepository.getAllCoursesWithStateByStudentId(studentId, CourseTakenState.APPROVED.name());

        return courses.stream().map(course -> {
            var units = unitsService.getAllUnitsByCourseId(course.getCourseId());
            return new CourseResponseDto(
                    course.getCourseId(),
                    course.getCourseTitle(),
                    course.getCourseEnrollmentState(),
                    units
            );
        }).toList();
    }

    @Override
    public List<LessonResponseDto> getUnitLessonsForStudent(Integer unitId, Integer studentId) {

        checkForExistedStudent(studentId);

        checkForExistedUnit(unitId);

        return lessonsService.getLessonsInUnitForStudent(unitId, studentId);
    }

    @Override
    public EnrolledCourseResponseDto enrollCourseByStudent(Integer courseId, Integer studentId) {
        return enrollmentCoursesService.enrollCourseByStudent(courseId, studentId);
    }

    @Override
    public List<VideoCommentResponseDto> getCommentsByLessonId(Integer lessonId) {

        if (isNotFoundLesson(lessonId)){
            throw new NotFoundResourceException("There is no Lesson with that id !!");
        }

        var comments = videosCommentsRepository.getCommentsByLessonId(lessonId);

        return comments.stream().map(comment -> new VideoCommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                timeSinceFormatter.formatTimeSince(comment.getCreatedAt()),
                comment.getAuthorId(),
                comment.getAuthorName(),
                comment.getAuthorType(),
                comment.getAuthorPhotoUrl(),
                comment.getLessonId()
        )).toList();
    }

    @Override
    public VideoCommentResponseDto addCommentToLesson(LessonCommentRequestDto requestDto) {

        var student = studentsRepository.findById(requestDto.studentId())
                .orElseThrow(() -> new NotFoundResourceException("There is no Student with that id !!"));

        var lesson = lessonsRepository.findById(requestDto.lessonId())
                .orElseThrow(() -> new NotFoundResourceException("There is no Lesson with that id !!"));

        var comment = new VideoComment(requestDto.content(), LocalDateTime.now(),student,lesson);
        var savedComment = videosCommentsRepository.save(comment);

        return new VideoCommentResponseDto(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getCreationDate(),
                timeSinceFormatter.formatTimeSince(savedComment.getCreationDate()),
                student.getId(),
                student.getName(),
                AuthorType.STUDENT.name(),
                student.getPhotoUrl(),
                lesson.getId()
        );
    }

    private boolean isNotFoundLesson(Integer lessonId) {
        return !lessonsRepository.existsById(lessonId);
    }

    private void checkForExistedUnit(Integer unitId) {
        if (isNotFoundUnit(unitId)) {
            throw new NotFoundResourceException("There is no Unit with that id !!");
        }
    }

    private boolean isNotFoundStudent(Integer studentId) {
        return !studentsRepository.existsById(studentId);
    }

    private boolean isNotFoundUnit(Integer unitId) {
        return !unitsRepository.existsById(unitId);
    }

    private boolean isExistedSemester(Integer semesterId) {
        return semestersRepository.existsById(semesterId);
    }
}
