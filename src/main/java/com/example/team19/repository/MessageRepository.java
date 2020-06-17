package com.example.team19.repository;

import com.example.team19.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value="SELECT m.mainId from Message m WHERE m.request.id=?1")
    List<Long> findExistingMessageIds(Long id);

    @Query(value="FROM Message m WHERE m.request.id=?1 ORDER BY m.dateTime ASC")
    List<Message> findMessagesForRequest(Long id);

}
