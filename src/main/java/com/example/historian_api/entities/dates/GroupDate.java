package com.example.historian_api.entities.dates;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Builder
@Table(name = "group_date")
public class GroupDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GradeGroup group;

    private String dayName;
    private String lessonDateTime;
}