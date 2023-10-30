package com.example.historian_api.repositories.courses.quizzes;

import com.example.historian_api.entities.courses.quizzes.grades.GradeQuizQuestion;
import com.example.historian_api.entities.projections.GradeQuizQuestionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeQuizQuestionsRepository extends JpaRepository<GradeQuizQuestion, Integer> {

    List<GradeQuizQuestion> findAllByQuiz_IdOrderById(Integer quizId);

    @Query(value = "SELECT questions.id as id, " +
                   "questions.correct_answer_index as correctAnswerIndex, " +
                   "questions.is_checked_answer as isCheckedAnswer, " +
                   "questions.question as question, " +
                   "questions.quiz_id as quizId, " +
                   "questions.correct_answer_description as correctAnswerDescription, " +
                   "questions.photo_url as photoUrl, " +
                   "answers.answer as answer, " +
                   "solutions.student_id as studentId, " +
                   "solutions.student_answer as studentAnswer, " +
                   "solutions.student_answer_index as studentAnswerIndex, " +
                   "solutions.is_student_succeeded as isStudentSucceeded " +
                   "FROM grade_quizzes_questions questions " +
                   "LEFT JOIN grade_quiz_question_answers answers " +
                   "ON questions.id = answers.question_id " +
                   "LEFT JOIN grade_quizzes_questions_solutions solutions " +
                   "ON solutions.question_id = questions.id AND solutions.student_id = ?2 " +
                   "WHERE questions.quiz_id = ?1", nativeQuery = true)
    List<GradeQuizQuestionProjection> findAllQuestionsByQuizId(Integer quizId , Integer studentId);

}