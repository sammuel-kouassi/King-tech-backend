package com.example.kingtechbackend.controller;

import com.example.kingtechbackend.dto.LoginRequestDTO;
import com.example.kingtechbackend.model.Utilisateur;
import com.example.kingtechbackend.repository.UtilisateurRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping("/register")
    public ResponseEntity<?> inscrireUtilisateur(@RequestBody Utilisateur nouvelUtilisateur) {
        if (utilisateurRepository.findByEmail(nouvelUtilisateur.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur : Cet email est déjà utilisé !");
        }

        // TODO plus tard : Hasher le mot de passe avec BCryptPasswordEncoder
        Utilisateur utilisateurSauvegarde = utilisateurRepository.save(nouvelUtilisateur);

        utilisateurSauvegarde.setMotDePasse(null);
        return ResponseEntity.ok(utilisateurSauvegarde);
    }

    @PostMapping("/login")
    public ResponseEntity<?> connecterUtilisateur(@RequestBody LoginRequestDTO loginRequest) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(loginRequest.getEmail());

        if (utilisateurOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Erreur : Email introuvable.");
        }

        Utilisateur utilisateur = utilisateurOpt.get();

        if (!utilisateur.getMotDePasse().equals(loginRequest.getMotDePasse())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Erreur : Mot de passe incorrect.");
        }

        utilisateur.setMotDePasse(null);
        return ResponseEntity.ok(utilisateur);
    }

    @PostMapping("/google")
    public ResponseEntity<?> connexionGoogle(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList("741902731114-9v8hb1h0laa6rpecl44cmhrm16a6g2in.apps.googleusercontent.com"))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                GoogleIdToken.Payload googlePayload = idToken.getPayload();

                String email = googlePayload.getEmail();
                String nomFamily = (String) googlePayload.get("family_name");
                String prenomGiven = (String) googlePayload.get("given_name");

                Optional<Utilisateur> userOpt = utilisateurRepository.findByEmail(email);
                Utilisateur utilisateur;

                if (userOpt.isPresent()) {
                    utilisateur = userOpt.get();
                } else {
                    utilisateur = new Utilisateur();
                    utilisateur.setEmail(email);
                    utilisateur.setNom(nomFamily != null ? nomFamily : "Utilisateur");
                    utilisateur.setPrenom(prenomGiven != null ? prenomGiven : "Google");
                    utilisateur.setMotDePasse("GOOGLE_AUTH");
                    utilisateur.setRole("MEMBRE");
                    utilisateur = utilisateurRepository.save(utilisateur);
                }

                return ResponseEntity.ok(utilisateur);
            } else {
                return ResponseEntity.status(401).body("Jeton Google invalide.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur serveur lors de la vérification Google.");
        }
    }
}