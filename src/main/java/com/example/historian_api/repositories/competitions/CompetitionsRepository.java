package com.example.historian_api.repositories.competitions;

import com.example.historian_api.entities.competitions.Competition;
import com.example.historian_api.entities.projections.FeedbackProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitionsRepository extends JpaRepository<Competition,Integer> {
    @Query(
            value = "select * " +
                    "from competitions c " +
                    "order by c.id desc " +
                    "Limit ?1 ",
            nativeQuery = true
    )
    List<Competition> findTop(Integer count);
}
