package com.example.historian_api.services.impl.feedbacks;

import com.example.historian_api.dtos.responses.FeedbackResponseDto;
import com.example.historian_api.entities.feedbacks.Feedback;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.feedbacks.FeedbacksRepository;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.services.base.feedbacks.FeedbacksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbacksServiceImpl implements FeedbacksService {

    private final FeedbacksRepository feedbacksRepository;
    private final StudentsRepository studentsRepository;

    @Override
    public List<FeedbackResponseDto> getTop(Integer count) {
        return feedbacksRepository.findTop(count)
                .stream()
                .map(feedback -> new FeedbackResponseDto(
                        feedback.getId(),
                        feedback.getContent(),
                        feedback.getPostedOn(),
                        feedback.getStudentId(),
                        feedback.getStudentName(),
                        feedback.getStudentPhotoUrl()
                ))
                .toList();
    }

    @Override
    public List<FeedbackResponseDto> getAll() {
        return feedbacksRepository.findAll()
                .stream()
                .map(feedback -> new FeedbackResponseDto(
                        feedback.getId(),
                        feedback.getContent(),
                        feedback.getPostedOn(),
                        feedback.getStudent().getId(),
                        feedback.getStudent().getName(),
                        feedback.getStudent().getPhotoUrl()
                ))
                .toList();
    }

    @Override
    public FeedbackResponseDto addFeedback(Integer studentId , String content) {
        var student = studentsRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourceException("There is no student with that id !!"));

        var feedback = new Feedback(
                student,
                LocalDateTime.now(),
                content
        );

        var savedFeedback = feedbacksRepository.save(feedback);

        return new FeedbackResponseDto(
                savedFeedback.getId(),
                savedFeedback.getContent(),
                savedFeedback.getPostedOn(),
                student.getId(),
                student.getName(),
                student.getPhotoUrl()
        );
    }
}
