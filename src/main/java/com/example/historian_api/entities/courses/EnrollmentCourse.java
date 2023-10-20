package com.example.historian_api.entities.courses;


import com.example.historian_api.entities.keys.EnrollmentCourseKey;
import com.example.historian_api.entities.users.Student;
import com.example.historian_api.enums.CourseTakenState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enrollment_courses")
public class EnrollmentCourse {

    @EmbeddedId
    private EnrollmentCourseKey key;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    @Enumerated(EnumType.STRING)
    private CourseTakenState state;

}
