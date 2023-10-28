package com.example.historian_api.services.impl.courses;

import com.example.historian_api.dtos.responses.EnrolledCourseResponseDto;
import com.example.historian_api.entities.courses.EnrollmentCourse;
import com.example.historian_api.entities.keys.EnrollmentCourseKey;
import com.example.historian_api.enums.CourseTakenState;
import com.example.historian_api.exceptions.AlreadyEnrolledCourseException;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.CoursesRepository;
import com.example.historian_api.repositories.courses.EnrollmentCoursesRepository;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.services.base.courses.EnrollmentCoursesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentCoursesServiceImpl implements EnrollmentCoursesService {

    private final EnrollmentCoursesRepository enrollmentCoursesRepository;
    private final StudentsRepository studentsRepository;
    private final CoursesRepository coursesRepository;

    @Override
    public EnrolledCourseResponseDto enrollCourseByStudent(Integer courseId, Integer studentId) {
        var student = studentsRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Student with that id !!"));

        var course = coursesRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Course with that id !!"));

        if (enrollmentCoursesRepository.isStudentEnrolledCourses(studentId,courseId))
            throw new AlreadyEnrolledCourseException("Student already enrolled this course !!");

        var enrollmentCourseKey = new EnrollmentCourseKey(courseId, studentId);
        var enrolledCourse = new EnrollmentCourse(enrollmentCourseKey, student, course, CourseTakenState.PENDING);
        enrollmentCoursesRepository.save(enrolledCourse);

        return new EnrolledCourseResponseDto(
                course.getId(),
                course.getTitle(),
                CourseTakenState.PENDING.name(),
                student.getId(),
                student.getName(),
                student.getPhotoUrl()
        );
    }

}
