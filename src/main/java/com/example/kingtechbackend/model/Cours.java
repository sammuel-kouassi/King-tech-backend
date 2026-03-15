package com.example.kingtechbackend.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "cours")
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String niveau;
    private String niveauClass;
    private Double rating;
    private String tags;
    private String duree;
    private Integer lecons;
    private String etudiants;
    private String icone;
    private String bgColor;
    private String categorie;
}