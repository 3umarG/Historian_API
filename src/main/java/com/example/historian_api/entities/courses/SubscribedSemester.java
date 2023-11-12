package com.example.historian_api.entities.courses;

import com.example.historian_api.entities.keys.SubscribedSemesterKey;
import com.example.historian_api.entities.users.Student;
import com.example.historian_api.enums.CourseTakenState;
import com.example.historian_api.enums.SubscriptionPaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "subscribed_semesters")
public class SubscribedSemester {

    @EmbeddedId
    private SubscribedSemesterKey key;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("semesterId")
    @JoinColumn(name = "semester_id")
    private GradeSemester semester;

    @Enumerated(EnumType.STRING)
    private CourseTakenState subscriptionState;

    @Enumerated(EnumType.STRING)
    private SubscriptionPaymentMethod paymentMethod;
}
