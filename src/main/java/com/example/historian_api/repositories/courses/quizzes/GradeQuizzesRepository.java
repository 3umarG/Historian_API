package com.example.historian_api.repositories.courses.quizzes;

import com.example.historian_api.entities.courses.quizzes.grades.GradeQuiz;
import com.example.historian_api.entities.projections.GradeQuizProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeQuizzesRepository extends JpaRepository<GradeQuiz, Integer> {

    @Query(value = "select quizzes.*, " +
                   "       case " +
                   "           when results.student_id is not null then true " +
                   "           else false " +
                   "           end " +
                   "           as is_solved " +
                   "from grade_quizzes quizzes " +
                   "         left join grade_quizzes_results results " +
                   "                   on quizzes.id = results.quiz_id and results.student_id = ?2 " +
                   "where quizzes.grade_id = ?1 " +
                   "order by quizzes.id",
            nativeQuery = true)
    List<GradeQuizProjection> getAllQuizzesForStudentByGradeId(Integer gradeId, Integer studentId);
}