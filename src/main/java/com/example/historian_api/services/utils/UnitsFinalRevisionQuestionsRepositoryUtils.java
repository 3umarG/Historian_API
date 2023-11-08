package com.example.historian_api.services.utils;

import com.example.historian_api.entities.courses.quizzes.units.FinalRevisionQuestion;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.courses.quizzes.units.FinalRevisionQuestionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitsFinalRevisionQuestionsRepositoryUtils {

    private final FinalRevisionQuestionsRepository questionsRepository;

    public FinalRevisionQuestion getQuestionByIdOrThrowNotFound(Integer questionId){
        return questionsRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Question with that id !!"));
    }
}
