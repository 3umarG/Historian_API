package com.example.historian_api.repositories.users;

import com.example.historian_api.entities.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentsRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByPhone(String phone);

}