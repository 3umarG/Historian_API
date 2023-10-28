package com.example.historian_api.services.impl.direct_questions;

import com.example.historian_api.dtos.responses.DirectAnswerResponseDto;
import com.example.historian_api.dtos.responses.DirectQuestionResponseDto;
import com.example.historian_api.dtos.responses.DirectQuestionWithAnswerResponseDto;
import com.example.historian_api.entities.direct_questions.DirectAnswer;
import com.example.historian_api.entities.direct_questions.DirectQuestion;
import com.example.historian_api.entities.projections.QuestionWithAnswerProjection;
import com.example.historian_api.entities.users.Student;
import com.example.historian_api.exceptions.BadRequestException;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.mappers.DirectQuestionToDtoMapper;
import com.example.historian_api.mappers.DirectQuestionWithAnswerToDtoMapper;
import com.example.historian_api.repositories.direct_answers.DirectAnswersRepository;
import com.example.historian_api.repositories.direct_answers.DirectQuestionsRepository;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.repositories.users.TeachersRepository;
import com.example.historian_api.services.base.direct_questions.DirectQuestionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectQuestionsServiceImpl implements DirectQuestionsService {

    private final StudentsRepository studentsRepository;
    private final DirectQuestionsRepository directQuestionsRepository;
    private final DirectAnswersRepository directAnswersRepository;
    private final DirectQuestionToDtoMapper questionToDtoMapper;
    private final DirectQuestionWithAnswerToDtoMapper questionWithAnswerToDtoMapper;
    private final TeachersRepository teachersRepository;

    private static DirectQuestionWithAnswerResponseDto apply(QuestionWithAnswerProjection q) {
        return new DirectQuestionWithAnswerResponseDto(
                new DirectQuestionResponseDto(
                        q.getQuestionId(),
                        q.getQuestion(),
                        q.getAskedOnTime(),
                        q.getStudentId(),
                        q.getStudentName() != null
                                ? q.getStudentName()
                                : null
                ),
                q.getAnswerId() != null,
                q.getAnswerId() != null
                        ? new DirectAnswerResponseDto(
                        q.getAnswerId(),
                        q.getAnswer(),
                        q.getAnsweredOnTime(),
                        q.getQuestionId(),
                        q.getTeacherId(),
                        q.getTeacherName(),
                        q.getTeacherPhotoUrl())
                        : null

        );
    }

    @Override
    public DirectQuestionResponseDto sendQuestionByStudentId(String questionContent, Integer studentId) {
        var student = findStudentById(studentId);

        var question = new DirectQuestion(questionContent, LocalDateTime.now(), student);
        var savedQuestion = directQuestionsRepository.save(question);

        return questionToDtoMapper.apply(savedQuestion);
    }


    private Student findStudentById(Integer studentId) {
        return studentsRepository
                .findById(studentId)
                .orElseThrow(() -> new NotFoundResourceException("There is no student with that id !!"));
    }

    @Override
    public DirectAnswerResponseDto answerQuestionByTeacher(Integer questionId, String answerContent, Integer teacherId) {
        var question = findQuestionById(questionId);
        var teacher = teachersRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Teacher with that id !!"));

        if (question.getAnswer() != null)
            throw new BadRequestException("Already answered Question !!");

        var answerToQuestion = new DirectAnswer(question, answerContent, LocalDateTime.now(), teacher);
        var savedAnswer = directAnswersRepository.save(answerToQuestion);

        return generateDirectAnswerResponseDto(savedAnswer);
    }

    private static DirectAnswerResponseDto generateDirectAnswerResponseDto(DirectAnswer savedAnswer) {
        return new DirectAnswerResponseDto(
                savedAnswer.getId(),
                savedAnswer.getContent(),
                savedAnswer.getRepliedOn(),
                savedAnswer.getQuestion().getId(),
                savedAnswer.getTeacher().getId(),
                savedAnswer.getTeacher().getName(),
                savedAnswer.getTeacher().getPhotoUrl()
        );
    }

    private DirectQuestion findQuestionById(Integer questionId) {
        return directQuestionsRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Question with that id !!"));
    }

    @Override
    public List<DirectQuestionResponseDto> getAllUnsolvedQuestions() {
        var questions = directQuestionsRepository.findAllUnSolved();
        return questions.stream()
                .map(questionToDtoMapper)
                .toList();
    }

    @Override
    public List<DirectQuestionWithAnswerResponseDto> getAllSolvedQuestions() {
        var questions = directQuestionsRepository.findAllSolved();
        return questions.stream()
                .map(DirectQuestionsServiceImpl::apply)
                .toList();
    }

    @Override
    public List<DirectQuestionWithAnswerResponseDto> getAllQuestionsWithTheirAnswersByStudentId(Integer studentId) {
        findStudentById(studentId);

        var questions = directQuestionsRepository.findAllSolved(studentId);

        return questions.stream()
                .map(q -> new DirectQuestionWithAnswerResponseDto(
                        new DirectQuestionResponseDto(
                                q.getQuestionId(),
                                q.getQuestion(),
                                q.getAskedOnTime(),
                                q.getStudentId(),
                                null
                        ),
                        q.getAnswerId() != null,
                        q.getAnswerId() != null
                                ? new DirectAnswerResponseDto(
                                    q.getAnswerId(),
                                    q.getAnswer(),
                                    q.getAnsweredOnTime(),
                                    q.getQuestionId(),
                                    q.getTeacherId(),
                                    q.getTeacherName(),
                                    q.getTeacherPhotoUrl())
                                : null

                ))
                .toList();
    }
}
