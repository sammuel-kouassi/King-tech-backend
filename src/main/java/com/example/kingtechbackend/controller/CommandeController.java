package com.example.kingtechbackend.controller;


import com.example.kingtechbackend.dto.CommandeRequestDTO;
import com.example.kingtechbackend.model.Commande;
import com.example.kingtechbackend.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@CrossOrigin(origins = "http://localhost:4200")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @PostMapping
    public ResponseEntity<Commande> validerCommande(@RequestBody CommandeRequestDTO request) {
        try {
            Commande nouvelleCommande = commandeService.creerCommande(request);
            return ResponseEntity.ok(nouvelleCommande);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Commande>> getToutesLesCommandes() {
        List<Commande> commandes = commandeService.getAllCommandes();
        return ResponseEntity.ok(commandes);
    }
}
