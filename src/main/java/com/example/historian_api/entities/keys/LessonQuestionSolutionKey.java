package com.example.historian_api.entities.keys;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LessonQuestionSolutionKey implements Serializable {
    private Integer studentId;
    private Integer questionId;
}
