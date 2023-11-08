package com.example.historian_api.utils.repositories_utils.units_final_revision;

import com.example.historian_api.entities.courses.quizzes.units.FinalRevisionQuestion;
import com.example.historian_api.entities.projections.GradeQuizQuestionProjection;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.quizzes.units.FinalRevisionQuestionsRepository;
import com.example.historian_api.utils.constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitsFinalRevisionQuestionsRepositoryUtils {

    private final FinalRevisionQuestionsRepository questionsRepository;


    public FinalRevisionQuestion getQuestionByIdOrThrowNotFound(Integer questionId){
        return questionsRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundResourceException(ExceptionMessages.getNotFoundResourceMessage("Question")));
    }

    public List<GradeQuizQuestionProjection> findAllQuestionsByUnitId(Integer unitId, Integer studentId) {

        return questionsRepository.findAllQuestionsByUnitId(unitId, studentId);
    }
}
