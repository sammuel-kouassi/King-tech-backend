package com.example.kingtechbackend.repository;

import com.example.kingtechbackend.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    // Cette méthode magique permet de trouver un utilisateur via son email
    Optional<Utilisateur> findByEmail(String email);
    List<Utilisateur> findByRole(String role);
    long countByRole(String role);
}