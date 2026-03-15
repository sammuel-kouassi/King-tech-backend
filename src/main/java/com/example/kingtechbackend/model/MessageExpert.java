package com.example.kingtechbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages_experts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageExpert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenu;

    private LocalDateTime dateEnvoi = LocalDateTime.now();

    // Celui qui envoie le message
    @ManyToOne
    @JoinColumn(name = "expediteur_id", nullable = false)
    private Utilisateur expediteur;

    // Celui qui reçoit le message (l'expert, ou le client si l'expert répond).
    @ManyToOne
    @JoinColumn(name = "destinataire_id", nullable = false)
    private Utilisateur destinataire;
}