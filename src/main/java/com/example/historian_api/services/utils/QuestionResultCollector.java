package com.example.historian_api.services.utils;

import com.example.historian_api.entities.courses.quizzes.QuestionSolution;
import com.example.historian_api.mappers.QuizWithQuestionsWrapper;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestionResultCollector<T extends QuestionSolution> {
    private final int totalQuestions;

    public QuestionResultCollector(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    @Getter
    private final List<T> questionsSolutions = new ArrayList<>();

    @Getter
    private final List<QuizWithQuestionsWrapper> questionsSolutionsWrappers = new ArrayList<>();
    private final AtomicInteger succeededQuestionsCounter = new AtomicInteger();


    public void incrementSucceededQuestionsCounter() {
        succeededQuestionsCounter.getAndIncrement();
    }

    public void addQuestionSolutionWrapper(QuizWithQuestionsWrapper wrapper){
        questionsSolutionsWrappers.add(wrapper);
    }

    public void addQuestionSolution(T questionSolution){
        questionsSolutions.add(questionSolution);
    }

    public int getSucceededQuestionsCounter(){
        return succeededQuestionsCounter.get();
    }

    public int getTotalQuestionsCounter(){
        return totalQuestions;
    }

}

