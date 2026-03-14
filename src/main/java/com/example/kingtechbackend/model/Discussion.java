package com.example.kingtechbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "discussions_forum")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discussion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre; // ex: "Robot suiveur de ligne..."

    // Infos sur l'auteur (on garde ça simple pour le moment sans table Utilisateur complexe)
    private String nomAuteur; // ex: "MakerSophie"
    private String initialesAuteur; // ex: "MS"

    private LocalDateTime dateCreation;

    // Les statistiques de la ligne
    private Integer nombreReponses = 0;
    private Integer nombreVues = 0;

    // Pour afficher le petit badge vert "ÉPINGLÉ"
    private boolean epingle = false;

    private String typeSujet;

    // Le lien vers la catégorie parente
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;


    // Ajoute ceci : Une discussion contient plusieurs messages
    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();
}