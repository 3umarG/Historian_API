package com.example.historian_api.entities.direct_questions;


import com.example.historian_api.entities.users.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "direct_answers")
public class DirectAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "qid")
    private DirectQuestion question;

    private String content;

    private LocalDateTime repliedOn;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Teacher teacher;

    public DirectAnswer(DirectQuestion question, String content, LocalDateTime repliedOn,Teacher teacher) {
        this.question = question;
        this.content = content;
        this.repliedOn = repliedOn;
        this.teacher = teacher;
    }
}
