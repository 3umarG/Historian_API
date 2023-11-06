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
import com.example.historian_api.mappers.QuizQuestionWrapper;
import com.example.historian_api.repositories.courses.LessonsRepository;
import com.example.historian_api.repositories.courses.quizzes.lessons.LessonQuestionsRepository;
import com.example.historian_api.repositories.courses.quizzes.lessons.LessonQuestionsSolutionsRepository;
import com.example.historian_api.repositories.courses.quizzes.lessons.LessonQuizResultsRepository;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.services.base.courses.quizzes.LessonsQuizzesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class LessonsQuizzesServiceImpl implements LessonsQuizzesService {

    private final StudentsRepository studentsRepository;
    private final LessonsRepository lessonsRepository;
    private final LessonQuestionsRepository lessonQuestionsRepository;
    private final LessonQuestionsSolutionsRepository questionsSolutionsRepository;
    private final LessonQuizResultsRepository lessonQuizResultsRepository;


    @Override
    public QuizWithQuestionsResponseDto getLessonQuestions(Integer lessonId, Integer studentId) {

        if (isNotFoundLesson(lessonId)) {
            throw new NotFoundResourceException("There is no Lesson with that id !!");
        }
        if (isNotFoundStudent(studentId)) {
            throw new NotFoundResourceException("There is no Student with that id !!");
        }

        var questions = lessonQuestionsRepository.findAllQuestionsByLessonId(lessonId, studentId);
        var quizWithQuestionsWrapper = new QuizQuestionWrapper(questions);


        return new QuizWithQuestionsResponseDto(
                false,
                quizWithQuestionsWrapper.reformatQuestionsWithAnswers()
        );

    }

    private boolean isNotFoundLesson(Integer lessonId) {
        return !lessonsRepository.existsById(lessonId);
    }

    private boolean isNotFoundStudent(Integer studentId) {
        return !studentsRepository.existsById(studentId);
    }

    @Override
    public QuizResultWithQuestionsResponseDto solveLessonQuiz(Integer studentId, Integer lessonId, AddQuizScoreRequestDto dto) {

        var student = studentsRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Student with that id !!"));

        var lesson = lessonsRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Lesson with that id !!"));

        if (lessonQuizResultsRepository.existsLessonQuizResultByStudent_IdAndLesson_Id(studentId, lessonId)) {
            throw new AlreadyEnrolledCourseException("Student already solved this lesson quiz !!");
        }

        int totalQuestionsScore = dto.questions().size();

        var questionsResults = new ArrayList<LessonQuestionSolution>();
        var questionsResultsWrappers = new ArrayList<QuizQuestionWrapper>();

        /**
         Lambda Expressions and forEach(s) are not thread safe,
         they don't support synchronization.
         So I use AtomicInteger to handle un-synchronized work
         */
        AtomicInteger actualQuestionsScore = new AtomicInteger();

        dto.questions().forEach(studentAnswerToQuestion -> {

            var questionResultKey = new LessonQuestionSolutionKey(extractQuestionId(studentAnswerToQuestion), studentId);
            var question = lessonQuestionsRepository.findById(studentAnswerToQuestion.questionId())
                    .orElseThrow(() -> new NotFoundResourceException("There is no Question with that id !!"));

            var actualAnswer = extractActualAnswer(question);

            boolean isStudentSucceeded = Objects.equals(studentAnswerToQuestion.answerIndex(), question.getCorrectAnswerIndex());

            if (isStudentSucceeded) {
                actualQuestionsScore.getAndIncrement();
            }

            var questionResult = new LessonQuestionSolution(questionResultKey,
                    student,
                    question,
                    question.getCorrectAnswerIndex(),
                    studentAnswerToQuestion.answerIndex(),
                    actualAnswer,
                    studentAnswerToQuestion.answer(),
                    isStudentSucceeded);

            var questionWrapperDto = new QuizQuestionWrapper(
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
                    isStudentSucceeded
            );

            questionsResultsWrappers.add(questionWrapperDto);
            questionsResults.add(questionResult);
        });

        var savedQuizResultDto = new SavedLessonQuizResultDto(dto.time(),actualQuestionsScore.get(),totalQuestionsScore,student,lesson);
        var savedQuizResult = saveQuizResult(savedQuizResultDto);

        questionsSolutionsRepository.saveAll(questionsResults);
        return new QuizResultWithQuestionsResponseDto(
                savedQuizResult.getSolutionPercentage(),
                savedQuizResult.getTakenTimeToSolveInSeconds(),
                savedQuizResult.getTotalScore(),
                savedQuizResult.getActualScore(),
                questionsResultsWrappers
        );

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
        var savedQuizResult = lessonQuizResultsRepository.save(quizResult);
        return savedQuizResult;
    }

    private static String extractActualAnswer(LessonQuestion question) {
        return question.getAnswers().get(question.getCorrectAnswerIndex());
    }

    private static Integer extractQuestionId(QuestionAnswerRequestDto studentAnswerToQuestion) {
        return studentAnswerToQuestion.questionId();
    }

}
