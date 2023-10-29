package com.example.historian_api.services.impl.courses.quizzes;

import com.example.historian_api.dtos.responses.GradeQuizResponseDto;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.quizzes.GradeQuizzesRepository;
import com.example.historian_api.repositories.grades.StudentGradesRepository;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.services.base.courses.quizzes.QuizzesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizzesServiceImpl implements QuizzesService {

    private final StudentsRepository studentsRepository;
    private final StudentGradesRepository gradesRepository;
    private final GradeQuizzesRepository quizzesRepository;

    @Override
    public List<GradeQuizResponseDto> getGradeQuizzesForStudent(Integer gradeId, Integer studentId) {

        if (isNotFoundStudent(studentId)) {
            throw new NotFoundResourceException("There is no Student with that id !!");
        }

        if (!gradesRepository.existsById(gradeId)) {
            throw new NotFoundResourceException("There is no Grade with that id !!");
        }

        var quizzes = quizzesRepository.getAllQuizzesForStudentByGradeId(gradeId, studentId);

        return quizzes.stream()
                .map(quiz -> new GradeQuizResponseDto(
                        quiz.getId(),
                        quiz.getTitle(),
                        quiz.getDescription(),
                        quiz.getIsSolvedOrNot(),
                        quiz.getIsFinalOrNot(),
                        quiz.getGradeId()
                ))
                .toList();


    }

    private boolean isNotFoundStudent(Integer studentId) {
        return !studentsRepository.existsById(studentId);
    }
}
