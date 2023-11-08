package com.example.historian_api.services.utils.students;

import com.example.historian_api.entities.users.Student;
import com.example.historian_api.exceptions.NotFoundResourceException;
import com.example.historian_api.repositories.users.StudentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentsRepositoryUtils {

    private final StudentsRepository studentsRepository;

    public Student getStudentByIdOrThrowNotFound(Integer studentId){
        return studentsRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourceException("There is no Student with that id !!"));
    }

    public Boolean isNotFoundStudent(Integer studentId){
        return !studentsRepository.existsById(studentId);
    }
}
