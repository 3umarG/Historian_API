package com.example.historian_api.repositories.dates;

import com.example.historian_api.entities.dates.GradeGroup;
import com.example.historian_api.entities.projections.FeedbackProjection;
import com.example.historian_api.entities.projections.GroupProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeGroupRepository extends JpaRepository<GradeGroup,Long> {
    @Query("SELECT g FROM GradeGroup g WHERE g.grade.id = :gradeId")
    List<GradeGroup> findAllByStudentGradeId(@Param("gradeId") Integer gradeId);
}
