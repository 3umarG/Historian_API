package com.example.historian_api.mappers;

import com.example.historian_api.dtos.requests.ComplaintRequestDto;
import com.example.historian_api.entities.complaints.Complaint;
import com.example.historian_api.entities.users.Student;
import com.example.historian_api.enums.ComplaintStatus;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.users.StudentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ComplaintRequestDtoToComplaintMapper implements Function<ComplaintRequestDto, Complaint> {

    private final StudentsRepository studentsRepository;
    @Override
    public Complaint apply(ComplaintRequestDto complaintRequestDto) {
        Student student = studentsRepository.findById(complaintRequestDto.studentId())
                .orElseThrow(() -> new NotFoundResourceException("There is no Student with that id !!"));
        return Complaint.builder()
                .content(complaintRequestDto.content())
                .creator(student)
                .creationDate(LocalDateTime.now())
                .status(ComplaintStatus.Active)
                .build();
    }
}
