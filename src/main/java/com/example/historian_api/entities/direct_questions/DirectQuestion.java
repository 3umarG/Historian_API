package com.example.historian_api.entities.direct_questions;


import com.example.historian_api.entities.users.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "direct_questions")
public class DirectQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    private LocalDateTime askedOn;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne(
            mappedBy = "question",
            cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DirectAnswer answer;


    public DirectQuestion(String content, LocalDateTime askedOn, Student student) {
        this.content = content;
        this.askedOn = askedOn;
        this.student = student;
    }
}
