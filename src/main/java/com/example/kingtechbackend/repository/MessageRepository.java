package com.example.kingtechbackend.repository;

import com.example.kingtechbackend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByDiscussionIdOrderByDateCreationAsc(Long discussionId);
}