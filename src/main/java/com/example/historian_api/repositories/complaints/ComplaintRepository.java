package com.example.historian_api.repositories.complaints;

import com.example.historian_api.entities.complaints.Complaint;
import com.example.historian_api.enums.ComplaintStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint,Long> {
    @Query("SELECT c FROM Complaint c WHERE c.creator.id = :creatorId order by c.id asc ")
    List<Complaint> findByCreatorId(@Param("creatorId") Integer creatorId);
    
    @Modifying
    @Transactional
    @Query("UPDATE Complaint c SET c.status = :newStatus WHERE c.id = :complaintId")
    int updateStatus(@Param("complaintId") Long complaintId, @Param("newStatus") ComplaintStatus newStatus);

    @Modifying
    @Transactional
    @Query("UPDATE Complaint c SET c.content = :newContent WHERE c.id = :complaintId")
    int updateContent(@Param("complaintId") Long complaintId, @Param("newContent") String newContent);
}
