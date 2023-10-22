package com.example.historian_api.repositories;

import com.example.historian_api.entities.direct_questions.DirectQuestion;
import com.example.historian_api.entities.projections.QuestionWithAnswerProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectQuestionsRepository extends JpaRepository<DirectQuestion, Integer> {

    @Query(value = "select * " +
                   "from direct_questions " +
                   "where id not in (" +
                   "    select qid" +
                   "    from direct_answers" +
                   ") " +
                   "order by asked_on desc ", nativeQuery = true)
    List<DirectQuestion> findAllUnSolved();


    @Query(value = "select " +
                   "    q.id as question_Id," +
                   "    ans.id as answer_id," +
                   "    q.content as q_content," +
                   "    ans.content as ans_content," +
                   "    q.asked_on as asked_on," +
                   "    ans.replied_on as replied_on," +
                   "    q.student_id as student_id," +
                   "    s.name as student_name, " +
                   "    t.id as teacher_id," +
                   "    t.name as teacher_name," +
                   "    t.photo_url as teacher_photo_url " +
                   "from direct_questions q " +
                   "         join direct_answers ans " +
                   "              on q.id = ans.qid " +
                   "         join students s" +
                   "              on student_id = s.id " +
                   "         left join teachers t " +
                   "              on ans.author_id = t.id " +
                   "order by asked_on desc ", nativeQuery = true)
    List<QuestionWithAnswerProjection> findAllSolved();

    @Query(value = "select " +
                   "                       q.id as question_Id, " +
                   "                       ans.id as answer_id, " +
                   "                       q.content as q_content, " +
                   "                       ans.content as ans_content, " +
                   "                       q.asked_on as asked_on, " +
                   "                       ans.replied_on as replied_on, " +
                   "                       q.student_id as student_id, " +
                   "                       t.id as teacher_id," +
                   "                       t.name as teacher_name," +
                   "                       t.photo_url as teacher_photo_url " +
                   "from direct_questions q " +
                   "         left join direct_answers ans " +
                   "                   on q.id = ans.qid " +
                   "         left join teachers t " +
                   "              on ans.author_id = t.id " +
                   "where student_id = ?1 " +
                   "order by asked_on ", nativeQuery = true)
    List<QuestionWithAnswerProjection> findAllSolved(Integer studentId);
}
