package com.example.historian_api.services.impl.courses;

import com.example.historian_api.dtos.responses.CourseResponseDto;
import com.example.historian_api.dtos.responses.EnrolledCourseResponseDto;
import com.example.historian_api.dtos.responses.LessonResponseDto;
import com.example.historian_api.enums.CourseTakenState;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.CoursesRepository;
import com.example.historian_api.repositories.courses.UnitsRepository;
import com.example.historian_api.repositories.grades.StudentGradesRepository;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.services.base.courses.CoursesService;
import com.example.historian_api.services.base.courses.EnrollmentCoursesService;
import com.example.historian_api.services.base.courses.LessonsService;
import com.example.historian_api.services.base.courses.UnitsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    private final EnrollmentCoursesService enrollmentCoursesService;


    @Override
    public List<CourseResponseDto> getAllCoursesByGradeIdForStudent(Integer gradeId, Integer studentId) {
        if (!isExistedGrade(gradeId)) {
            throw new NotFoundResourceException("There is no Grade with that id !!");
        }

        checkForExistedStudent(studentId);

        var coursesProjection = coursesRepository.getAllCoursesByGradeIdWithEnrollmentStateForStudent(gradeId, studentId);

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

    private boolean isExistedGrade(Integer gradeId) {
        return gradesRepository.existsById(gradeId);
    }
}
