package com.example.kingtechbackend.dto;

public record ProduitResumeDTO(
        Long id,
        String nom,
        Double prix,
        String categorie,
        Double note,
        String badge,
        String imagePrincipale,
        Boolean isPopular
) {}