package com.example.historian_api.repositories.dates;

import com.example.historian_api.entities.dates.GradeGroup;
import com.example.historian_api.entities.dates.GroupDate;
import com.example.historian_api.entities.projections.FeedbackProjection;
import com.example.historian_api.entities.projections.GroupProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DateRepository extends JpaRepository<GroupDate,Long> {
    @Query("SELECT g FROM GroupDate g WHERE g.group.id = :groupId")
    List<GroupDate> findAllByGroupId(@Param("groupId") Long groupId);


}
