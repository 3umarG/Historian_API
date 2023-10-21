package com.example.historian_api.entities.direct_questions;


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


    public DirectAnswer(DirectQuestion question, String content, LocalDateTime repliedOn) {
        this.question = question;
        this.content = content;
        this.repliedOn = repliedOn;
    }
}
