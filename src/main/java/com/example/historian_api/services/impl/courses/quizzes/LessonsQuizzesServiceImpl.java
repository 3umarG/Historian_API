package com.example.historian_api.services.impl.courses.quizzes;

import com.example.historian_api.dtos.requests.AddQuizScoreRequestDto;
import com.example.historian_api.dtos.requests.QuestionAnswerRequestDto;
import com.example.historian_api.dtos.requests.SavedLessonQuizResultDto;
import com.example.historian_api.dtos.responses.QuizResultWithQuestionsResponseDto;
import com.example.historian_api.dtos.responses.QuizWithQuestionsResponseDto;
import com.example.historian_api.entities.courses.quizzes.lessons.LessonQuestion;
import com.example.historian_api.entities.courses.quizzes.lessons.LessonQuestionSolution;
import com.example.historian_api.entities.courses.quizzes.lessons.LessonQuizResult;
import com.example.historian_api.entities.keys.LessonQuestionSolutionKey;
import com.example.historian_api.entities.keys.LessonQuizResultKey;
import com.example.historian_api.exceptions.AlreadyEnrolledCourseException;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.mappers.QuizWithQuestionsWrapper;
import com.example.historian_api.repositories.courses.quizzes.lessons.LessonQuestionsRepository;
import com.example.historian_api.repositories.courses.quizzes.lessons.LessonQuestionsSolutionsRepository;
import com.example.historian_api.repositories.courses.quizzes.lessons.LessonQuizResultsRepository;
import com.example.historian_api.services.base.courses.quizzes.LessonsQuizzesService;
import com.example.historian_api.services.utils.LessonsRepositoryUtils;
import com.example.historian_api.services.utils.StudentsRepositoryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class LessonsQuizzesServiceImpl implements LessonsQuizzesService {

    private final LessonQuestionsRepository lessonQuestionsRepository;
    private final LessonQuestionsSolutionsRepository questionsSolutionsRepository;
    private final LessonQuizResultsRepository lessonQuizResultsRepository;
    private final StudentsRepositoryUtils studentsRepositoryUtils;
    private final LessonsRepositoryUtils lessonsRepositoryUtils;


    @Override
    public QuizWithQuestionsResponseDto getLessonQuestions(Integer lessonId, Integer studentId) {

        if (lessonsRepositoryUtils.isNotFoundLesson(lessonId)) {
            throw new NotFoundResourceException("There is no Lesson with that id !!");
        }
        if (studentsRepositoryUtils.isNotFoundStudent(studentId)) {
            throw new NotFoundResourceException("There is no Student with that id !!");
        }

        var questions = lessonQuestionsRepository.findAllQuestionsByLessonId(lessonId, studentId);
        var quizWithQuestionsWrapper = new QuizWithQuestionsWrapper(questions);


        return new QuizWithQuestionsResponseDto(
                false,
                quizWithQuestionsWrapper.reformatQuestionsWithAnswers()
        );

    }

    @Override
    public QuizResultWithQuestionsResponseDto solveLessonQuiz(Integer studentId, Integer lessonId, AddQuizScoreRequestDto dto) {

        var student = studentsRepositoryUtils.getStudentByIdOrThrowNotFound(studentId);

        var lesson = lessonsRepositoryUtils.getLessonByIdOrThrowNotFound(lessonId);

        ensureStudentHasNotSolvedQuiz(studentId, lessonId);

        var totalQuestionsScore = dto.questions().size();
        var actualQuestionsScore = new AtomicInteger();

        var questionsSolutions = new ArrayList<LessonQuestionSolution>();
        var questionsResultsWrappers = new ArrayList<QuizWithQuestionsWrapper>();


        dto.questions().forEach(studentAnswerToQuestion -> {

            var question = getQuestionByIdOrThrowNotFound(studentAnswerToQuestion.questionId());

            boolean isStudentSucceeded = isStudentAnswerCorrect(studentAnswerToQuestion, question);

            if (isStudentSucceeded) {
                actualQuestionsScore.getAndIncrement();
            }

            QuizWithQuestionsWrapper questionWrapperDto = createQuizQuestionWrapper(studentId, studentAnswerToQuestion, question, isStudentSucceeded);
            LessonQuestionSolution questionResult = createQuestionSolution(studentId, studentAnswerToQuestion, question, isStudentSucceeded);

            questionsResultsWrappers.add(questionWrapperDto);
            questionsSolutions.add(questionResult);
        });

        saveQuestionsSolutionsToDb(questionsSolutions);

        var savedQuizResultDto = new SavedLessonQuizResultDto(
                dto.time(),
                actualQuestionsScore.get(),
                totalQuestionsScore,
                student,
                lesson);
        LessonQuizResult savedQuizResult = saveQuizResult(savedQuizResultDto);


        return createQuizResultWithQuestionsResponseDto(savedQuizResult, questionsResultsWrappers);

    }

    private void saveQuestionsSolutionsToDb(ArrayList<LessonQuestionSolution> questionsResults) {
        questionsSolutionsRepository.saveAll(questionsResults);
    }

    private void ensureStudentHasNotSolvedQuiz(Integer studentId, Integer lessonId) {
        if (lessonQuizResultsRepository.existsLessonQuizResultByStudent_IdAndLesson_Id(studentId, lessonId)) {
            throw new AlreadyEnrolledCourseException("Student already solved this lesson quiz !!");
        }
    }

    private LessonQuestion getQuestionByIdOrThrowNotFound(Integer questionId) {
        return lessonQuestionsRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Question with that id !!"));
    }

    private boolean isStudentAnswerCorrect(QuestionAnswerRequestDto studentAnswerToQuestion, LessonQuestion question) {
        return Objects.equals(studentAnswerToQuestion.answerIndex(), question.getCorrectAnswerIndex());
    }

    private LessonQuestionSolution createQuestionSolution(Integer studentId, QuestionAnswerRequestDto studentAnswerToQuestion, LessonQuestion question, boolean isStudentSucceeded) {
        LessonQuestionSolutionKey questionResultKey = new LessonQuestionSolutionKey(studentAnswerToQuestion.questionId(), studentId);
        String actualAnswer = extractActualAnswer(question);

        return new LessonQuestionSolution(
                questionResultKey,
                studentsRepositoryUtils.getStudentByIdOrThrowNotFound(studentId),
                question,
                question.getCorrectAnswerIndex(),
                studentAnswerToQuestion.answerIndex(),
                actualAnswer,
                studentAnswerToQuestion.answer(),
                isStudentSucceeded);
    }

    private QuizWithQuestionsWrapper createQuizQuestionWrapper(Integer studentId, QuestionAnswerRequestDto studentAnswerToQuestion, LessonQuestion question, boolean isStudentSucceeded) {
        return new QuizWithQuestionsWrapper(
                question.getId(),
                question.getQuestion(),
                question.getAnswers(),
                question.getCorrectAnswerIndex(),
                extractActualAnswer(question),
                question.getCorrectAnswerDescription(),
                question.getPhotoUrl(),
                question.getIsCheckedAnswer(),
                studentId,
                studentAnswerToQuestion.answer(),
                studentAnswerToQuestion.answerIndex(),
                isStudentSucceeded);
    }

    private QuizResultWithQuestionsResponseDto createQuizResultWithQuestionsResponseDto(LessonQuizResult savedQuizResult, List<QuizWithQuestionsWrapper> questionsResultsWrappers) {
        return new QuizResultWithQuestionsResponseDto(
                savedQuizResult.getSolutionPercentage(),
                savedQuizResult.getTakenTimeToSolveInSeconds(),
                savedQuizResult.getTotalScore(),
                savedQuizResult.getActualScore(),
                questionsResultsWrappers);
    }

    private LessonQuizResult saveQuizResult(SavedLessonQuizResultDto dto) {
        var quizResultKey = new LessonQuizResultKey(
                dto.lesson().getId(),
                dto.student().getId());

        var solutionPercentage = ((float) dto.actualQuestionsScore() / (float) dto.totalQuestionsScore()) * 100;

        var quizResult = new LessonQuizResult(quizResultKey,
                dto.student(),
                dto.lesson(),
                BigDecimal.valueOf(solutionPercentage),
                dto.time(),
                BigDecimal.valueOf(dto.totalQuestionsScore()),
                BigDecimal.valueOf(dto.actualQuestionsScore())
        );
        return lessonQuizResultsRepository.save(quizResult);
    }

    private static String extractActualAnswer(LessonQuestion question) {
        return question.getAnswers().get(question.getCorrectAnswerIndex());
    }


}
