package com.example.kingtechbackend.controller;



import com.example.kingtechbackend.dto.ProduitResumeDTO;
import com.example.kingtechbackend.model.Produit;
import com.example.kingtechbackend.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produits")
@CrossOrigin(origins = "http://localhost:4200")
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping
    public List<ProduitResumeDTO> getAllProduits() {
        List<Produit> produits = produitRepository.findAll();

        return produits.stream().map(p -> new ProduitResumeDTO(
                p.getId(),
                p.getNom(),
                p.getPrix(),
                p.getCategorie(),
                p.getNote(),
                p.getBadge(),
                p.getImages() != null && !p.getImages().isEmpty() ? p.getImages().get(0) : null,
                p.getIsPopular()
        )).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Produit getProduitById(@PathVariable Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
    }
}