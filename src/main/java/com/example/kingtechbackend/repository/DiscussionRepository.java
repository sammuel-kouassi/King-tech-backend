package com.example.kingtechbackend.repository;

import com.example.kingtechbackend.model.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    // Cette méthode va récupérer toutes les discussions, triées de la plus récente à la plus ancienne
    List<Discussion> findAllByOrderByDateCreationDesc();
    List<Discussion> findByCategorieIdOrderByDateCreationDesc(Long categorieId);
}