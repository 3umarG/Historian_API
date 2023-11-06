package com.example.historian_api.services.impl.courses.quizzes;

import com.example.historian_api.dtos.requests.AddQuizScoreRequestDto;
import com.example.historian_api.dtos.requests.SavedGradeQuizResultDto;
import com.example.historian_api.dtos.responses.GradeQuizResponseDto;
import com.example.historian_api.dtos.responses.QuizResultWithQuestionsResponseDto;
import com.example.historian_api.dtos.responses.QuizWithQuestionsResponseDto;
import com.example.historian_api.entities.courses.quizzes.grades.GradeQuizQuestion;
import com.example.historian_api.entities.courses.quizzes.grades.GradeQuizQuestionSolution;
import com.example.historian_api.entities.courses.quizzes.grades.GradeQuizResult;
import com.example.historian_api.entities.keys.GradeQuizQuestionSolutionKey;
import com.example.historian_api.entities.keys.GradeQuizResultKey;
import com.example.historian_api.exceptions.AlreadyEnrolledCourseException;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.mappers.QuizQuestionWrapper;
import com.example.historian_api.repositories.courses.quizzes.grades.GradeQuizQuestionSolutionsRepository;
import com.example.historian_api.repositories.courses.quizzes.grades.GradeQuizQuestionsRepository;
import com.example.historian_api.repositories.courses.quizzes.grades.GradeQuizzesRepository;
import com.example.historian_api.repositories.courses.quizzes.grades.GradeQuizzesResultsRepository;
import com.example.historian_api.repositories.grades.StudentGradesRepository;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.services.base.courses.quizzes.QuizzesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class QuizzesServiceImpl implements QuizzesService {

    private final StudentsRepository studentsRepository;
    private final StudentGradesRepository gradesRepository;
    private final GradeQuizzesRepository quizzesRepository;
    private final GradeQuizQuestionsRepository gradeQuizQuestionsRepository;
    private final GradeQuizQuestionSolutionsRepository quizQuestionSolutionsRepository;
    private final GradeQuizzesResultsRepository quizzesResultsRepository;

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

    @Override
    public QuizWithQuestionsResponseDto getGradeQuizQuestions(Integer quizId, Integer studentId) {

        var quiz = quizzesRepository.findById(quizId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Quiz with that id !!"));

        if (isNotFoundStudent(studentId)) {
            throw new NotFoundResourceException("There is no Student with that id !!");
        }

        var questions = gradeQuizQuestionsRepository.findAllQuestionsByQuizId(quizId, studentId);
        var quizWithQuestionsWrapper = new QuizQuestionWrapper(questions);


        return new QuizWithQuestionsResponseDto(
                quiz.getIsFinal(),
                quizWithQuestionsWrapper.reformatQuestionsWithAnswers()
        );
    }

    @Override
    public QuizResultWithQuestionsResponseDto solveQuiz(Integer studentId, Integer quizId, AddQuizScoreRequestDto dto) {
        var student = studentsRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Student with that id !!"));

        var quiz = quizzesRepository.findById(quizId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Quiz with that id !!"));

        if (quizzesResultsRepository.existsGradeQuizResultByStudent_IdAndQuiz_Id(studentId, quizId)) {
            throw new AlreadyEnrolledCourseException("Student already solved this quiz !!");
        }


        var questionsResults = new ArrayList<GradeQuizQuestionSolution>();
        var questionsResultsWrappers = new ArrayList<QuizQuestionWrapper>();

        int totalQuestionsScore = dto.questions().size();

        /**
         Lambda Expressions and forEach(s) are not thread safe,
         they don't support synchronization.
         So I use AtomicInteger to handle un-synchronized work
         */
        AtomicInteger actualQuestionsScore = new AtomicInteger();

        dto.questions().forEach(studentAnswerToQuestion -> {

            var questionResultKey = new GradeQuizQuestionSolutionKey(studentAnswerToQuestion.questionId(), studentId);
            var question = gradeQuizQuestionsRepository.findById(studentAnswerToQuestion.questionId())
                    .orElseThrow(() -> new NotFoundResourceException("There is no Question with that id !!"));

            var actualAnswer = extractActualAnswer(question);

            boolean isStudentSucceeded = Objects.equals(studentAnswerToQuestion.answerIndex(), question.getCorrectAnswerIndex());

            if (isStudentSucceeded)
                actualQuestionsScore.getAndIncrement();

            var questionResult = new GradeQuizQuestionSolution(questionResultKey,
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

        var savedQuizResultDto = new SavedGradeQuizResultDto(dto.time(), actualQuestionsScore.get(), totalQuestionsScore, student, quiz);
        var savedQuizResult = saveQuizResult(savedQuizResultDto);

        quizQuestionSolutionsRepository.saveAll(questionsResults);
        return new QuizResultWithQuestionsResponseDto(
                savedQuizResult.getSolutionPercentage(),
                savedQuizResult.getTakenTimeToSolveInSeconds(),
                savedQuizResult.getTotalScore(),
                savedQuizResult.getActualScore(),
                questionsResultsWrappers
        );
    }

    private static String extractActualAnswer(GradeQuizQuestion question) {
        return question.getAnswers().get(question.getCorrectAnswerIndex());
    }

    private GradeQuizResult saveQuizResult(SavedGradeQuizResultDto dto) {
        var quizResultKey = new GradeQuizResultKey(dto.student().getId(), dto.quiz().getId());
        var solutionPercentage = ((float) dto.actualQuestionsScore() / (float) dto.totalQuestionsScore()) * 100;
        var quizResult = new GradeQuizResult(quizResultKey,
                dto.quiz(),
                dto.student(),
                BigDecimal.valueOf(solutionPercentage),
                dto.time(),
                BigDecimal.valueOf(dto.totalQuestionsScore()),
                BigDecimal.valueOf(dto.actualQuestionsScore())
        );
        return quizzesResultsRepository.save(quizResult);
    }

    private boolean isNotFoundStudent(Integer studentId) {
        return !studentsRepository.existsById(studentId);
    }
}
