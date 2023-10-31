package com.example.historian_api.entities.projections;
import com.example.historian_api.entities.dates.GroupDate;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

public interface GroupProjection {
    @Value("#{target.id}")
    Long getId();
    @Value("#{target.title}")
    String getTitle();
    @Value("#{target.grade_id}")
    Integer getGradeId();
    @Value("#{target.dates}")
    List<GroupDate> getDates();
}
