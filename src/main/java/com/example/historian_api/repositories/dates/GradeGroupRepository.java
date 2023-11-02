package com.example.historian_api.repositories.dates;

import com.example.historian_api.entities.dates.GradeGroup;
import com.example.historian_api.entities.projections.GradeGroupProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("SELECT g FROM GradeGroup g WHERE g.id = :groupId")
    GradeGroupProjection findByGroupIdWithProjection(@Param("groupId") Long groupId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE grade_group SET title = :newTitle WHERE id = :groupId")
    void updateGroupTitleById(@Param("newTitle") String newTitle, @Param("groupId") Long groupId);

}
