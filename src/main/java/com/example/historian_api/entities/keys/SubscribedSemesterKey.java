package com.example.historian_api.entities.keys;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SubscribedSemesterKey implements Serializable {
    private Integer semesterId;
    private Integer studentId;
}
