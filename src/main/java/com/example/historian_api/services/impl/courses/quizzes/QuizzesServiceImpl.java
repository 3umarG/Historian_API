package com.example.historian_api.services.impl.courses.quizzes;

import com.example.historian_api.dtos.requests.AddQuizScoreRequestDto;
import com.example.historian_api.dtos.requests.QuestionAnswerRequestDto;
import com.example.historian_api.dtos.requests.SavedGradeQuizResultDto;
import com.example.historian_api.dtos.responses.GradeQuizResponseDto;
import com.example.historian_api.dtos.responses.QuizResultWithQuestionsResponseDto;
import com.example.historian_api.dtos.responses.QuizWithQuestionsResponseDto;
import com.example.historian_api.entities.courses.quizzes.grades.GradeQuizQuestion;
import com.example.historian_api.entities.courses.quizzes.grades.GradeQuizQuestionSolution;
import com.example.historian_api.entities.courses.quizzes.grades.GradeQuizResult;
import com.example.historian_api.entities.keys.GradeQuizQuestionSolutionKey;
import com.example.historian_api.entities.users.Student;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.mappers.QuizWithQuestionsWrapper;
import com.example.historian_api.repositories.courses.quizzes.grades.GradeQuizQuestionsRepository;
import com.example.historian_api.repositories.courses.quizzes.grades.GradeQuizzesRepository;
import com.example.historian_api.repositories.grades.StudentGradesRepository;
import com.example.historian_api.services.base.courses.quizzes.QuizSolver;
import com.example.historian_api.services.base.courses.quizzes.QuizzesService;
import com.example.historian_api.services.utils.GradeQuizQuestionsRepositoryUtils;
import com.example.historian_api.services.utils.GradeQuizzesRepositoryUtils;
import com.example.historian_api.services.utils.QuestionResultCollector;
import com.example.historian_api.services.utils.StudentsRepositoryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizzesServiceImpl implements QuizzesService {

    private final GradeQuizQuestionsRepositoryUtils gradeQuizQuestionsRepositoryUtils;
    private final StudentsRepositoryUtils studentsRepositoryUtils;
    private final GradeQuizzesRepositoryUtils quizzesRepositoryUtils;
    @Qualifier(value = "GradeQuizSolver")
    private final QuizSolver quizSolver;
    private final StudentGradesRepository gradesRepository;
    private final GradeQuizzesRepository quizzesRepository;
    private final GradeQuizQuestionsRepository gradeQuizQuestionsRepository;

    @Override
    public List<GradeQuizResponseDto> getGradeQuizzesForStudent(Integer gradeId, Integer studentId) {

        if (studentsRepositoryUtils.isNotFoundStudent(studentId)) {
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

        if (studentsRepositoryUtils.isNotFoundStudent(studentId)) {
            throw new NotFoundResourceException("There is no Student with that id !!");
        }

        var questions = gradeQuizQuestionsRepository.findAllQuestionsByQuizId(quizId, studentId);
        var quizWithQuestionsWrapper = new QuizWithQuestionsWrapper(questions);


        return new QuizWithQuestionsResponseDto(
                quiz.getIsFinal(),
                quizWithQuestionsWrapper.reformatQuestionsWithAnswers()
        );
    }

    @Override
    public QuizResultWithQuestionsResponseDto solveQuiz(Integer studentId, Integer quizId, AddQuizScoreRequestDto dto) {

        var student = studentsRepositoryUtils.getStudentByIdOrThrowNotFound(studentId);

        var quiz = quizzesRepositoryUtils.getQuizByIdOrThrowNotFound(quizId);

        quizSolver.ensureStudentHasNotSolvedQuiz(studentId, quizId);

        var questionResultCollector = new QuestionResultCollector<GradeQuizQuestionSolution>(dto.questions().size());


        dto.questions().forEach(studentAnswerToQuestion -> {

            var question = gradeQuizQuestionsRepositoryUtils.getQuestionByIdOrThrowNotFound(studentAnswerToQuestion.questionId());

            boolean isStudentSucceeded = quizSolver.isStudentAnswerCorrect(studentAnswerToQuestion, question);

            if (isStudentSucceeded) {
                questionResultCollector.incrementSucceededQuestionsCounter();
            }


            var questionSolution = createQuestionSolution(studentId, studentAnswerToQuestion, question, student, isStudentSucceeded);
            var questionWrapperDto = createQuestionWrapper(studentId, studentAnswerToQuestion, question, isStudentSucceeded);

            questionResultCollector.addQuestionSolution(questionSolution);
            questionResultCollector.addQuestionSolutionWrapper(questionWrapperDto);
        });

        quizSolver.saveQuestionsSolutionsToDb(questionResultCollector.getQuestionsSolutions());

        var savedQuizResultDto = new SavedGradeQuizResultDto(dto.time(),
                questionResultCollector.getSucceededQuestionsCounter(),
                questionResultCollector.getTotalQuestionsCounter(),
                student,
                quiz);
        GradeQuizResult savedQuizResult = (GradeQuizResult) quizSolver.saveQuizResult(savedQuizResultDto);

        return createQuizResultResponseDto(savedQuizResult, questionResultCollector);
    }

    private static QuizResultWithQuestionsResponseDto createQuizResultResponseDto(GradeQuizResult savedQuizResult, QuestionResultCollector<GradeQuizQuestionSolution> questionResultCollector) {
        return new QuizResultWithQuestionsResponseDto(
                savedQuizResult.getSolutionPercentage(),
                savedQuizResult.getTakenTimeToSolveInSeconds(),
                savedQuizResult.getTotalScore(),
                savedQuizResult.getActualScore(),
                questionResultCollector.getQuestionsSolutionsWrappers()
        );
    }

    private static QuizWithQuestionsWrapper createQuestionWrapper(Integer studentId, QuestionAnswerRequestDto studentAnswerToQuestion, GradeQuizQuestion question, boolean isStudentSucceeded) {
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
                isStudentSucceeded
        );
    }

    private static GradeQuizQuestionSolution createQuestionSolution(Integer studentId, QuestionAnswerRequestDto studentAnswerToQuestion, GradeQuizQuestion question, Student student, boolean isStudentSucceeded) {
        var questionResultKey = new GradeQuizQuestionSolutionKey(studentAnswerToQuestion.questionId(), studentId);

        var actualAnswer = extractActualAnswer(question);

        return new GradeQuizQuestionSolution(questionResultKey,
                student,
                question,
                question.getCorrectAnswerIndex(),
                studentAnswerToQuestion.answerIndex(),
                actualAnswer,
                studentAnswerToQuestion.answer(),
                isStudentSucceeded);
    }

    private static String extractActualAnswer(GradeQuizQuestion question) {
        return question.getAnswers().get(question.getCorrectAnswerIndex());
    }

}
