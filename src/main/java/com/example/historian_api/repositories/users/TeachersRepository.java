package com.example.historian_api.repositories.users;

import com.example.historian_api.entities.users.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeachersRepository extends JpaRepository<Teacher, Integer> {
    Optional<Teacher> findByPhone(String phone);
}
