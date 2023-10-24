package com.example.historian_api.services.base.feedbacks;

import com.example.historian_api.dtos.responses.FeedbackResponseDto;
import com.example.historian_api.entities.feedbacks.Feedback;

import java.util.List;

public interface FeedbacksService {
    List<FeedbackResponseDto> getTop(Integer count);

    List<FeedbackResponseDto> getAll();

    FeedbackResponseDto addFeedback(Integer studentId, String content);
}
