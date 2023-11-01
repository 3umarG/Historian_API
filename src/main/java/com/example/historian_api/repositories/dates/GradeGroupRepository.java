package com.example.historian_api.repositories.dates;

import com.example.historian_api.entities.dates.GradeGroup;
import com.example.historian_api.entities.projections.GradeGroupProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeGroupRepository extends JpaRepository<GradeGroup,Long> {
    @Query("SELECT g FROM GradeGroup g WHERE g.grade.id = :gradeId order by g.id asc ")
    List<GradeGroupProjection> findAllByStudentGradeId(@Param("gradeId") Integer gradeId);

    @Query("SELECT g FROM GradeGroup g order by g.id asc ")
    List<GradeGroupProjection> findAllWithProjection();

}
