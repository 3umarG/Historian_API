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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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
        Complaint complaint = updateComplaintStatusInternal(complaintId, newStatus);
        return complaintToComplaintResponseDtoMapper.apply(complaint);
    }

    @Override
    public ComplaintResponseDto updateContent(Long complaintId, String newContent) {
        Complaint complaint = updateComplaintContentInternal(complaintId, newContent);
        return complaintToComplaintResponseDtoMapper.apply(complaint);
    }

    @Override
    public ComplaintResponseDto findComplaintById(Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new NotFoundResourceException("There is no complaint with that id !!"));
        return complaintToComplaintResponseDtoMapper.apply(complaint);
    }

    @Override
    public ComplaintResponseDto removeComplaint(Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new NotFoundResourceException("There is no complaint with that id !!"));
        complaintRepository.delete(complaint);
        return complaintToComplaintResponseDtoMapper.apply(complaint);
    }

    @Override
    public List<ComplaintResponseDto> getStudentComplaints(Integer studentId) {
        studentsRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourceException("There is no student with that id !!"));

        List<Complaint> complaintList = complaintRepository.findByCreatorId(studentId);
        return complaintList.stream().map(complaintToComplaintResponseDtoMapper).toList();
    }

    @Override
    public List<ComplaintResponseDto> getComplaintsByStatus(Integer studentId, ComplaintStatus status) {
        studentsRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourceException("There is no student with that id !!"));

        if (status != ComplaintStatus.Resolved && status != ComplaintStatus.Active) {
            throw new NotFoundResourceException("Invalid status !!");
        }

        List<Complaint> complaintList = complaintRepository.findByCreatorId(studentId);
        List<Complaint> complaints = complaintList.stream().filter(complaint -> complaint.getStatus() == status).toList();
        return complaints.stream().map(complaintToComplaintResponseDtoMapper).toList();
    }

    private Complaint updateComplaintStatusInternal(Long complaintId, ComplaintStatus newStatus) {
        if (newStatus != ComplaintStatus.Resolved && newStatus != ComplaintStatus.Active) {
            throw new NotFoundResourceException("Invalid status !!");
        }
        int updatedCount = complaintRepository.updateStatus(complaintId, newStatus);
        if (updatedCount == 0) {
            throw new NotFoundResourceException("Complaint not found with id: " + complaintId);
        }
        return complaintRepository.findById(complaintId).get();
    }

    private Complaint updateComplaintContentInternal(Long complaintId, String newContent) {
        if (newContent.isEmpty()) {
            throw new NotFoundResourceException("Invalid content !!");
        }
        int updatedCount = complaintRepository.updateContent(complaintId, newContent);
        if (updatedCount == 0) {
            throw new NotFoundResourceException("Complaint not found with id: " + complaintId);
        }
        return complaintRepository.findById(complaintId).get();
    }
}