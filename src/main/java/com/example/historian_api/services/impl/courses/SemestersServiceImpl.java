package com.example.historian_api.services.impl.courses;

import com.example.historian_api.dtos.responses.CourseResponseDto;
import com.example.historian_api.dtos.responses.EnrolledSemesterResponseDto;
import com.example.historian_api.dtos.responses.SemesterResponseDto;
import com.example.historian_api.entities.courses.SubscribedSemester;
import com.example.historian_api.entities.keys.SubscribedSemesterKey;
import com.example.historian_api.enums.CourseTakenState;
import com.example.historian_api.enums.SubscriptionPaymentMethod;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.CoursesRepository;
import com.example.historian_api.repositories.courses.GradesSemestersRepository;
import com.example.historian_api.repositories.courses.SubscribedSemestersRepository;
import com.example.historian_api.repositories.grades.StudentGradesRepository;
import com.example.historian_api.services.base.courses.SemestersService;
import com.example.historian_api.services.base.courses.UnitsService;
import com.example.historian_api.utils.constants.ExceptionMessages;
import com.example.historian_api.utils.repositories_utils.students.StudentsRepositoryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemestersServiceImpl implements SemestersService {

    private final StudentsRepositoryUtils studentsRepositoryUtils;
    private final StudentGradesRepository gradesRepository;
    private final GradesSemestersRepository semestersRepository;
    private final CoursesRepository coursesRepository;
    private final UnitsService unitsService;
    private final SubscribedSemestersRepository subscribedSemestersRepository;


    @Override
    public List<SemesterResponseDto> getAllSemestersInGradeForStudent(Integer gradeId, Integer studentId) {
        if (studentsRepositoryUtils.isNotFoundStudent(studentId))
            throw new NotFoundResourceException(ExceptionMessages.getNotFoundResourceMessage("Student"));

        if (!gradesRepository.existsById(gradeId))
            throw new NotFoundResourceException(ExceptionMessages.getNotFoundResourceMessage("Grade"));

        var semesters = semestersRepository.findAllSemestersByGradeIdForStudentId(gradeId, studentId);
        return semesters.stream().map(s -> new SemesterResponseDto(s.getId(), s.getName(), s.getState())).toList();

    }

    @Override
    public List<CourseResponseDto> getCoursesInSemester(Integer semesterId) {

        if (!semestersRepository.existsById(semesterId)){
            throw new NotFoundResourceException(ExceptionMessages.getNotFoundResourceMessage("Semester"));
        }

        var courses = coursesRepository.findCoursesBySemesterId(semesterId);

        return courses.stream().map(course -> {
            var units = unitsService.getAllUnitsByCourseId(course.getCourseId());
            return new CourseResponseDto(
                    course.getCourseId(),
                    course.getCourseTitle(),
                    null,
                    units
            );
        }).toList();

    }

    @Override
    public EnrolledSemesterResponseDto subscribeStudentInSemesterByVodafoneCash(Integer studentId, Integer semesterId) {
        var student = studentsRepositoryUtils.getStudentByIdOrThrowNotFound(studentId);
        var semester = semestersRepository.findById(semesterId)
                .orElseThrow(() -> new NotFoundResourceException(ExceptionMessages.getNotFoundResourceMessage("Semester")));

        var subscribedSemesterKey = new SubscribedSemesterKey(semesterId,studentId);
        var subscribedSemester = new SubscribedSemester(subscribedSemesterKey,
                student,
                semester,
                CourseTakenState.PENDING,
                SubscriptionPaymentMethod.VODAFONE_CASH);

        var savedSubscribedSemester = subscribedSemestersRepository.save(subscribedSemester);

        return new EnrolledSemesterResponseDto(
                semesterId,
                semester.getName(),
                savedSubscribedSemester.getSubscriptionState().name(),
                studentId,
                student.getName(),
                student.getPhotoUrl()
        );
    }

    @Override
    public EnrolledSemesterResponseDto subscribeStudentInSemesterByCode(Integer studentId, Integer semesterId, String code) {
        return null;
    }
}
