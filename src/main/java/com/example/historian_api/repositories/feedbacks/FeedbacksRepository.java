package com.example.historian_api.repositories.feedbacks;

import com.example.historian_api.entities.feedbacks.Feedback;
import com.example.historian_api.entities.projections.FeedbackProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbacksRepository extends JpaRepository<Feedback, Integer> {


    @Query(
            value = "select f.id as id," +
                    "f.content as content," +
                    "f.posted_on as posted_on," +
                    "s.id as student_id," +
                    "s.name as student_name," +
                    "s.photo_url as student_photo_url " +
                    "from feedbacks f " +
                    "join students s " +
                    "   ON s.id = f.student_id " +
                    "order by f.id desc " +
                    "Limit ?1 ",
            nativeQuery = true
    )
    List<FeedbackProjection> findTop(Integer count);

}
