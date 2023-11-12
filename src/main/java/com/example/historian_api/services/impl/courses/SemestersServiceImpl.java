package com.example.historian_api.services.impl.courses;

import com.example.historian_api.dtos.responses.CourseResponseDto;
import com.example.historian_api.dtos.responses.EnrolledSemesterResponseDto;
import com.example.historian_api.dtos.responses.SemesterResponseDto;
import com.example.historian_api.entities.courses.GradeSemester;
import com.example.historian_api.entities.courses.SubscribedSemester;
import com.example.historian_api.entities.keys.SubscribedSemesterKey;
import com.example.historian_api.entities.users.Student;
import com.example.historian_api.enums.CourseTakenState;
import com.example.historian_api.enums.SubscriptionPaymentMethod;
import com.example.historian_api.exceptions.AlreadyEnrolledCourseException;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.exceptions.NotValidSubscriptionCardException;
import com.example.historian_api.repositories.cards.SubscriptionCardRepository;
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
    private final SubscriptionCardRepository subscriptionCardsRepository;

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

        if (!semestersRepository.existsById(semesterId)) {
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
        var student = findStudentById(studentId);
        var semester = findSemesterById(semesterId);

        if (isStudentAlreadySubscribedSemester(studentId, semesterId)) {
            throw new AlreadyEnrolledCourseException(ExceptionMessages.ALREADY_SUBSCRIBED_SEMESTER_VODAFONE_CASH);
        }

        var savedSubscribedSemester = saveSubscribedSemesterByVodafoneCashToDb(semester, student);

        return new EnrolledSemesterResponseDto(
                semester.getId(),
                semester.getName(),
                savedSubscribedSemester.getSubscriptionState().name(),
                student.getId(),
                student.getName(),
                student.getPhotoUrl()
        );
    }

    private SubscribedSemester saveSubscribedSemesterByVodafoneCashToDb(GradeSemester semester, Student student) {
        var subscribedSemesterKey = new SubscribedSemesterKey(semester.getId(), student.getId());
        var subscribedSemester = new SubscribedSemester(subscribedSemesterKey,
                student,
                semester,
                CourseTakenState.PENDING,
                SubscriptionPaymentMethod.VODAFONE_CASH);

        return subscribedSemestersRepository.save(subscribedSemester);
    }

    private Boolean isStudentAlreadySubscribedSemester(Integer studentId, Integer semesterId) {
        return subscribedSemestersRepository.existsBySemester_IdAndStudent_Id(semesterId, studentId);
    }

    private GradeSemester findSemesterById(Integer semesterId) {
        return semestersRepository.findById(semesterId)
                .orElseThrow(() -> new NotFoundResourceException(ExceptionMessages.getNotFoundResourceMessage("Semester")));
    }

    private Student findStudentById(Integer studentId) {
        return studentsRepositoryUtils.getStudentByIdOrThrowNotFound(studentId);
    }

    @Override
    public EnrolledSemesterResponseDto subscribeStudentInSemesterByCode(Integer studentId, Integer semesterId, String code) {
        var student = findStudentById(studentId);
        var semester = findSemesterById(semesterId);

        if (isStudentAlreadySubscribedSemester(studentId, semesterId)) {
            throw new AlreadyEnrolledCourseException(ExceptionMessages.ALREADY_SUBSCRIBED_SEMESTER_CODE);
        }

        if (isNotValidCode(code)) {
            throw new NotValidSubscriptionCardException(ExceptionMessages.NOT_VALID_SUBSCRIPTION_CODE);
        }

        var savedSubscribedSemester = saveSubscribedSemesterByCodeCardToDb(semester, student);
        invalidateCardWithCode(code);

        return new EnrolledSemesterResponseDto(semesterId,
                semester.getName(),
                savedSubscribedSemester.getSubscriptionState().name(),
                studentId,
                student.getName(),
                student.getPhotoUrl());
    }

    private void invalidateCardWithCode(String code) {
        subscriptionCardsRepository.invalidateCardByCode(code);
    }

    private SubscribedSemester saveSubscribedSemesterByCodeCardToDb(GradeSemester semester, Student student) {
        var subscribedSemesterKey = new SubscribedSemesterKey(semester.getId(), student.getId());
        var subscribedSemester = new SubscribedSemester(subscribedSemesterKey, student, semester, CourseTakenState.APPROVED, SubscriptionPaymentMethod.CODE_CARD);
        return subscribedSemestersRepository.save(subscribedSemester);
    }

    private boolean isNotValidCode(String code) {
        return !subscriptionCardsRepository.isActiveCardCode(code);
    }
}
