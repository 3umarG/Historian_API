package com.example.historian_api.entities.projections;

import org.springframework.beans.factory.annotation.Value;

public interface GradeCoursesInformationProjection {

    @Value("#{target.id}")
    Integer getCourseId();

    @Value("#{target.title}")
    String getCourseTitle();

    @Value("#{target.state}")
    String getCourseEnrollmentState();
}
