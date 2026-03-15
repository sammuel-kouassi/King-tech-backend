package com.example.kingtechbackend.controller;

import com.example.kingtechbackend.dto.CommandeRequestDTO;
import com.example.kingtechbackend.model.Commande;
import com.example.kingtechbackend.service.CommandeService;
import com.example.kingtechbackend.service.EmailService;
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

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<Commande> validerCommande(@RequestBody CommandeRequestDTO request) {
        try {
            // On crée et sauvegarde la commande
            Commande nouvelleCommande = commandeService.creerCommande(request);

            // On utilise bien "nouvelleCommande" pour récupérer les infos et envoyer l'email !
            emailService.envoyerRecuAchat(
                    nouvelleCommande.getEmailClient(),
                    nouvelleCommande.getNomClient(),
                    "CMD-" + nouvelleCommande.getId(), // Ex: CMD-104
                    nouvelleCommande.getTotal()            );

            // On renvoie la réponse à Angular
            return ResponseEntity.ok(nouvelleCommande);

        } catch (RuntimeException e) {
            System.err.println("Erreur lors de la validation de la commande : " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Commande>> getToutesLesCommandes() {
        List<Commande> commandes = commandeService.getAllCommandes();
        return ResponseEntity.ok(commandes);
    }
}