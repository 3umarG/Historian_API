package com.example.historian_api.services.impl.complaints;

import com.example.historian_api.dtos.requests.ComplaintRequestDto;
import com.example.historian_api.dtos.responses.ComplaintResponseDto;
import com.example.historian_api.entities.complaints.Complaint;
import com.example.historian_api.entities.users.Student;
import com.example.historian_api.enums.ComplaintStatus;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.mappers.ComplaintRequestDtoToComplaintMapper;
import com.example.historian_api.mappers.ComplaintToComplaintResponseDtoMapper;
import com.example.historian_api.repositories.complaints.ComplaintRepository;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.services.base.complaints.ComplaintsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ComplaintsServiceImpl implements ComplaintsService {

    private final ComplaintRepository complaintRepository;
    private final StudentsRepository studentsRepository;
    private final ComplaintRequestDtoToComplaintMapper requestDtoToComplaintMapper;
    private final ComplaintToComplaintResponseDtoMapper complaintToComplaintResponseDtoMapper;
    @Override
    public ComplaintResponseDto saveComplaint(ComplaintRequestDto complaintRequestDto) {
        Student student = studentsRepository.findById(complaintRequestDto.studentId())
                .orElseThrow(() -> new NotFoundResourceException("There is no Student with that id !!"));

        Complaint complaint = requestDtoToComplaintMapper.apply(complaintRequestDto);
        complaint.setCreator(student);
        complaintRepository.save(complaint);
        return complaintToComplaintResponseDtoMapper.apply(complaint);
    }

    @Override
    public ComplaintResponseDto updateStatus(Long complaintId, ComplaintStatus newStatus) {
        int updatedCount = complaintRepository.updateStatus(complaintId, newStatus);
        if (updatedCount == 0) {
            throw new NotFoundResourceException("Complaint not found with id: " + complaintId);
        }
        Complaint updatedComplaint=complaintRepository.findById(complaintId).get();
        return complaintToComplaintResponseDtoMapper.apply(updatedComplaint);
    }

    @Override
    public List<ComplaintResponseDto> getStudentComplaints(Integer studentId) {
        studentsRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Student with that id !!"));

        List<Complaint>complaintList=complaintRepository.findByCreatorId(studentId);
        return complaintList.stream().map(complaintToComplaintResponseDtoMapper::apply).toList();
    }
}
