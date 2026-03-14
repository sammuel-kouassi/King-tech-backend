package com.example.kingtechbackend.controller;

import com.example.kingtechbackend.dto.LoginRequestDTO;
import com.example.kingtechbackend.model.Utilisateur;
import com.example.kingtechbackend.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // --- INSCRIPTION ---
    @PostMapping("/register")
    public ResponseEntity<?> inscrireUtilisateur(@RequestBody Utilisateur nouvelUtilisateur) {
        // 1. Vérifier si l'email existe déjà
        if (utilisateurRepository.findByEmail(nouvelUtilisateur.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur : Cet email est déjà utilisé !");
        }

        // 2. Sauvegarder l'utilisateur
        // TODO plus tard : Hasher le mot de passe avec BCryptPasswordEncoder
        Utilisateur utilisateurSauvegarde = utilisateurRepository.save(nouvelUtilisateur);

        // On ne renvoie pas le mot de passe au frontend par sécurité
        utilisateurSauvegarde.setMotDePasse(null);
        return ResponseEntity.ok(utilisateurSauvegarde);
    }

    // --- CONNEXION ---
    @PostMapping("/login")
    public ResponseEntity<?> connecterUtilisateur(@RequestBody LoginRequestDTO loginRequest) {
        // 1. Chercher l'utilisateur par email
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(loginRequest.getEmail());

        if (utilisateurOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Erreur : Email introuvable.");
        }

        Utilisateur utilisateur = utilisateurOpt.get();

        // 2. Vérifier le mot de passe
        if (!utilisateur.getMotDePasse().equals(loginRequest.getMotDePasse())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Erreur : Mot de passe incorrect.");
        }

        // 3. Connexion réussie ! On renvoie les infos (sans le mot de passe)
        utilisateur.setMotDePasse(null);
        return ResponseEntity.ok(utilisateur);
    }
}