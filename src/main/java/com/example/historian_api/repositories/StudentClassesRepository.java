package com.example.historian_api.repositories;

import com.example.historian_api.entities.courses.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentClassesRepository extends JpaRepository<StudentClass, Integer> {
}