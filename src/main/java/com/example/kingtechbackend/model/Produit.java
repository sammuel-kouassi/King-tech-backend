package com.example.kingtechbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "produits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Column(length = 300)
    private String descriptionCourte;

    @Column(length = 3000)
    private String description;

    private Double prix;
    private String categorie;
    private Integer stock;

    private Double note;
    private String badge;

    // Champ pour identifier les produits vedettes
    private Boolean isPopular;

    @ElementCollection
    @CollectionTable(name = "produit_images", joinColumns = @JoinColumn(name = "produit_id"))
    @Column(name = "image_url")
    private List<String> images;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> caracteristiques;
}