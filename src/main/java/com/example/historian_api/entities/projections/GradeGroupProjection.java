package com.example.historian_api.entities.projections;
import com.example.historian_api.entities.courses.StudentGrade;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;


public interface GradeGroupProjection {
    @Value("#{target.id}")
    Long getGroupId();
    @Value("#{target.title}")
    String getTitle();
    @Value("#{target.grade}")
    StudentGrade getStudentGrade();
    @Value("#{target.dates}")
    List<GroupDateProjection> getGroupDates();
}