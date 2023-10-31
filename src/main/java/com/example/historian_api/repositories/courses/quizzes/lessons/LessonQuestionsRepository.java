package com.example.historian_api.repositories.courses.quizzes.lessons;

import com.example.historian_api.entities.courses.quizzes.lessons.LessonQuestion;
import com.example.historian_api.entities.projections.GradeQuizQuestionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonQuestionsRepository extends JpaRepository<LessonQuestion, Integer> {

    @Query(value = "SELECT questions.id as id, " +
                   "questions.correct_answer_index as correctAnswerIndex, " +
                   "questions.is_checked_answer as isCheckedAnswer, " +
                   "questions.question as question, " +
                   "questions.lesson_id as quizId, " +
                   "questions.correct_answer_description as correctAnswerDescription, " +
                   "questions.photo_url as photoUrl, " +
                   "answers.answer as answer, " +
                   "solutions.student_id as studentId, " +
                   "solutions.student_answer as studentAnswer, " +
                   "solutions.student_answer_index as studentAnswerIndex, " +
                   "solutions.is_student_succeeded as isStudentSucceeded " +
                   "FROM lessons_questions questions " +
                   "LEFT JOIN lessons_question_answers answers " +
                   "ON questions.id = answers.question_id " +
                   "LEFT JOIN lessons_questions_solutions solutions " +
                   "ON solutions.question_id = questions.id AND solutions.student_id = ?2 " +
                   "WHERE questions.lesson_id = ?1 " +
                   "order by questions.id",
            nativeQuery = true)
    List<GradeQuizQuestionProjection> findAllQuestionsByLessonId(Integer lessonId, Integer studentId);

}