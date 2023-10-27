package com.example.historian_api.services.impl.courses;

import com.example.historian_api.dtos.responses.CourseResponseDto;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.CoursesRepository;
import com.example.historian_api.repositories.grades.StudentGradesRepository;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.services.base.courses.CoursesService;
import com.example.historian_api.services.base.courses.UnitsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursesServiceImpl implements CoursesService {

    private final CoursesRepository coursesRepository;
    private final UnitsService unitsService;
    private final StudentsRepository studentsRepository;
    private final StudentGradesRepository gradesRepository;

    @Override
    public List<CourseResponseDto> getAllCoursesByGradeIdForStudent(Integer gradeId, Integer studentId) {
        if(!isExistedGrade(gradeId)){
            throw new NotFoundResourceException("There is no Grade with that id !!");
        }

        if(!isExistedStudent(studentId)){
            throw new NotFoundResourceException("There is no Student with that id !!");
        }

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

    private boolean isExistedStudent(Integer studentId) {
        return studentsRepository.existsById(studentId);
    }

    private boolean isExistedGrade(Integer gradeId) {
        return gradesRepository.existsById(gradeId);
    }
}
