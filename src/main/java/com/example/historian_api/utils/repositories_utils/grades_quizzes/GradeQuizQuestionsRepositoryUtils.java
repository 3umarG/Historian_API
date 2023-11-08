package com.example.historian_api.utils.repositories_utils.grades_quizzes;

import com.example.historian_api.entities.courses.quizzes.grades.GradeQuizQuestion;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.quizzes.grades.GradeQuizQuestionsRepository;
import com.example.historian_api.utils.constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GradeQuizQuestionsRepositoryUtils {

    private final GradeQuizQuestionsRepository gradeQuizQuestionsRepository;

    public GradeQuizQuestion getQuestionByIdOrThrowNotFound(Integer questionId) {
        return gradeQuizQuestionsRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundResourceException(ExceptionMessages.getNotFoundResourceMessage("Question")));

    }
}
