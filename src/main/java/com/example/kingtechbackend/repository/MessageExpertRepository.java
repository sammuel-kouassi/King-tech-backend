package com.example.kingtechbackend.repository;

import com.example.kingtechbackend.model.MessageExpert;
import com.example.kingtechbackend.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MessageExpertRepository extends JpaRepository<MessageExpert, Long> {

    @Query("SELECT m FROM MessageExpert m WHERE " +
            "(m.expediteur.id = :user1 AND m.destinataire.id = :user2) OR " +
            "(m.expediteur.id = :user2 AND m.destinataire.id = :user1) " +
            "ORDER BY m.dateEnvoi ASC")
    List<MessageExpert> findConversation(@Param("user1") Long user1, @Param("user2") Long user2);

    @Query("SELECT DISTINCT m.expediteur FROM MessageExpert m WHERE m.destinataire.id = :expertId")
    List<Utilisateur> findClientsParExpert(@Param("expertId") Long expertId);
}