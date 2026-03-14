package com.example.kingtechbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages_forum")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    private String nomAuteur;
    private String initialesAuteur;
    private LocalDateTime dateCreation;

    // Le lien vers la discussion parente
    @ManyToOne
    @JoinColumn(name = "discussion_id")
    @JsonIgnore // Empêche la boucle infinie
    private Discussion discussion;
}