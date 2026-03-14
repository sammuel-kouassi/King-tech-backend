package com.example.kingtechbackend.dto;

import java.util.List;

public record CommandeRequestDTO(
        String nomClient,
        String emailClient,
        String adresseLivraison,
        String telephone,
        List<LigneCommandeRequestDTO> lignes
) {}
