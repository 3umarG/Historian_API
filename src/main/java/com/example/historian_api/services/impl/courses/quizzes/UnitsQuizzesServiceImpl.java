package com.example.historian_api.services.impl.courses.quizzes;

import com.example.historian_api.dtos.requests.AddQuizScoreRequestDto;
import com.example.historian_api.dtos.requests.QuestionAnswerRequestDto;
import com.example.historian_api.dtos.requests.SavedUnitFinalRevisionQuizResultDto;
import com.example.historian_api.dtos.responses.QuizResultWithQuestionsResponseDto;
import com.example.historian_api.dtos.responses.QuizWithQuestionsResponseDto;
import com.example.historian_api.entities.courses.quizzes.units.FinalRevisionQuestion;
import com.example.historian_api.entities.courses.quizzes.units.FinalRevisionQuestionSolution;
import com.example.historian_api.entities.courses.quizzes.units.FinalRevisionResult;
import com.example.historian_api.entities.keys.FinalRevisionQuestionSolutionKey;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.mappers.QuizWithQuestionsWrapper;
import com.example.historian_api.repositories.courses.quizzes.units.FinalRevisionQuestionsRepository;
import com.example.historian_api.services.base.courses.quizzes.QuizSolver;
import com.example.historian_api.services.base.courses.quizzes.UnitsQuizzesService;
import com.example.historian_api.services.utils.QuestionResultCollector;
import com.example.historian_api.services.utils.StudentsRepositoryUtils;
import com.example.historian_api.services.utils.UnitsFinalRevisionQuestionsRepositoryUtils;
import com.example.historian_api.services.utils.UnitsRepositoryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitsQuizzesServiceImpl implements UnitsQuizzesService {

    @Autowired
    @Qualifier("UnitQuizSolver")
    private QuizSolver quizSolver;
    private final StudentsRepositoryUtils studentsRepositoryUtils;
    private final UnitsRepositoryUtils unitsRepositoryUtils;
    private final FinalRevisionQuestionsRepository questionsRepository;
    private final UnitsFinalRevisionQuestionsRepositoryUtils questionsRepositoryUtils;


    @Override
    public QuizWithQuestionsResponseDto getUnitQuizQuestions(Integer unitId, Integer studentId) {

        if (studentsRepositoryUtils.isNotFoundStudent(studentId)) {
            throw new NotFoundResourceException("There is no Student with that id !!");
        }

        if (unitsRepositoryUtils.isNotFoundUnitById(unitId)) {
            throw new NotFoundResourceException("There is no Unit with that id !!");
        }

        var questions = questionsRepository.findAllQuestionsByUnitId(unitId, studentId);
        var quizWithQuestionsWrapper = new QuizWithQuestionsWrapper(questions);


        return new QuizWithQuestionsResponseDto(
                false,
                quizWithQuestionsWrapper.reformatQuestionsWithAnswers()
        );

    }

    @Override
    public QuizResultWithQuestionsResponseDto solveUnitQuiz(Integer studentId, Integer unitId, AddQuizScoreRequestDto dto) {

        var student = studentsRepositoryUtils.getStudentByIdOrThrowNotFound(studentId);

        var unit = unitsRepositoryUtils.getUnitByIdOrThrowNotFound(unitId);

        quizSolver.ensureStudentHasNotSolvedQuiz(studentId, unitId);

        var questionResultCollector = new QuestionResultCollector<FinalRevisionQuestionSolution>(dto.questions().size());

        dto.questions().forEach(studentAnswerToQuestion -> {

            var question = questionsRepositoryUtils.getQuestionByIdOrThrowNotFound(studentAnswerToQuestion.questionId());

            boolean isStudentSucceeded = quizSolver.isStudentAnswerCorrect(studentAnswerToQuestion, question);

            if (isStudentSucceeded) {
                questionResultCollector.incrementSucceededQuestionsCounter();
            }

            QuizWithQuestionsWrapper questionWrapperDto = createQuizQuestionWrapper(studentId, studentAnswerToQuestion, question, isStudentSucceeded);
            FinalRevisionQuestionSolution questionResult = createQuestionSolution(studentId, studentAnswerToQuestion, question, isStudentSucceeded);

            questionResultCollector.addQuestionSolutionWrapper(questionWrapperDto);
            questionResultCollector.addQuestionSolution(questionResult);
        });

        quizSolver.saveQuestionsSolutionsToDb(questionResultCollector.getQuestionsSolutions());

        var savedQuizResultDto = new SavedUnitFinalRevisionQuizResultDto(
                dto.time(),
                questionResultCollector.getSucceededQuestionsCounter(),
                questionResultCollector.getTotalQuestionsCounter(),
                student,
                unit);

        FinalRevisionResult savedQuizResult = (FinalRevisionResult) quizSolver.saveQuizResult(savedQuizResultDto);

        return createQuizResultWithQuestionsResponseDto(savedQuizResult, questionResultCollector.getQuestionsSolutionsWrappers());
    }

    private QuizResultWithQuestionsResponseDto createQuizResultWithQuestionsResponseDto(
            FinalRevisionResult savedQuizResult,
            List<QuizWithQuestionsWrapper> questionsSolutionsWrappers) {

        return new QuizResultWithQuestionsResponseDto(
                savedQuizResult.getSolutionPercentage(),
                savedQuizResult.getTakenTimeToSolveInSeconds(),
                savedQuizResult.getTotalScore(),
                savedQuizResult.getActualScore(),
                questionsSolutionsWrappers);
    }

    private FinalRevisionQuestionSolution createQuestionSolution(Integer studentId, QuestionAnswerRequestDto studentAnswerToQuestion, FinalRevisionQuestion question, boolean isStudentSucceeded) {
        var finalRevisionSolutionKey = new FinalRevisionQuestionSolutionKey(
                studentAnswerToQuestion.questionId(),
                studentId
        );

        String actualAnswer = question.getAnswers().get(question.getCorrectAnswerIndex());

        return new FinalRevisionQuestionSolution(
                finalRevisionSolutionKey,
                studentsRepositoryUtils.getStudentByIdOrThrowNotFound(studentId),
                question,
                question.getCorrectAnswerIndex(),
                studentAnswerToQuestion.answerIndex(),
                actualAnswer,
                studentAnswerToQuestion.answer(),
                isStudentSucceeded);
    }

    private QuizWithQuestionsWrapper createQuizQuestionWrapper(Integer studentId, QuestionAnswerRequestDto studentAnswerToQuestion, FinalRevisionQuestion question, boolean isStudentSucceeded) {
        return new QuizWithQuestionsWrapper(
                question.getId(),
                question.getQuestion(),
                question.getAnswers(),
                question.getCorrectAnswerIndex(),
                question.getAnswers().get(question.getCorrectAnswerIndex()),
                question.getCorrectAnswerDescription(),
                question.getPhotoUrl(),
                question.getIsCheckedAnswer(),
                studentId,
                studentAnswerToQuestion.answer(),
                studentAnswerToQuestion.answerIndex(),
                isStudentSucceeded);
    }
}
