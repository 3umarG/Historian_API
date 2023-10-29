package com.example.historian_api.entities.keys;

import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FinalRevisionQuestionSolutionKey implements Serializable {
    private Integer studentId;
    private Integer questionId;
}
