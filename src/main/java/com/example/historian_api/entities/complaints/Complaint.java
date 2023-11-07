package com.example.historian_api.entities.complaints;

import com.example.historian_api.entities.users.Student;
import com.example.historian_api.enums.ComplaintStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "complaints", indexes = {
        @Index(name = "complaint_status_index", columnList = "complaint_status"),
        @Index(name = "complaint_student_id_index", columnList = "student_id"),
})
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "complaint_status")
    private ComplaintStatus status;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student creator;}
