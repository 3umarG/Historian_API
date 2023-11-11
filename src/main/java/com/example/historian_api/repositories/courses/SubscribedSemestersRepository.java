package com.example.historian_api.repositories.courses;

import com.example.historian_api.entities.courses.SubscribedSemester;
import com.example.historian_api.entities.keys.SubscribedSemesterKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribedSemestersRepository extends JpaRepository<SubscribedSemester, SubscribedSemesterKey> {
}