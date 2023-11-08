package com.example.historian_api.utils.repositories_utils.students;

import com.example.historian_api.entities.users.Student;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.users.StudentsRepository;
import com.example.historian_api.utils.constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentsRepositoryUtils {

    private final StudentsRepository studentsRepository;

    public Student getStudentByIdOrThrowNotFound(Integer studentId){
        return studentsRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourceException(ExceptionMessages.getNotFoundResourceMessage("Student")));
    }

    public Boolean isNotFoundStudent(Integer studentId){
        return !studentsRepository.existsById(studentId);
    }
}
