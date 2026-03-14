package com.example.kingtechbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "utilisateurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String motDePasse; // Note : En production, on crypte ça avec BCrypt !

    private String role = "MEMBRE"; // "MEMBRE", "EXPERT", ou "ADMIN"

    private LocalDateTime dateInscription = LocalDateTime.now();

    // Génère les initiales (ex: "Jean Kouassi" -> "JK") pour les avatars
    public String getInitials() {
        String n = (nom != null && !nom.isEmpty()) ? nom.substring(0, 1).toUpperCase() : "";
        String p = (prenom != null && !prenom.isEmpty()) ? prenom.substring(0, 1).toUpperCase() : "";
        return p + n;
    }
}