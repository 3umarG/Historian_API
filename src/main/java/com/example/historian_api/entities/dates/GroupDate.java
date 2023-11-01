package com.example.historian_api.entities.dates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Builder
@Table(name = "groups_dates")
public class GroupDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GradeGroup group;

    private String dayName;
    private String lessonDateTime;


    public GroupDate(GradeGroup group, String dayName, String lessonDateTime) {
        this.group = group;
        this.dayName = dayName;
        this.lessonDateTime = lessonDateTime;
    }
}
