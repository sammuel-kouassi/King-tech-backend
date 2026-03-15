package com.example.kingtechbackend.controller;

import com.example.kingtechbackend.model.Newsletter;
import com.example.kingtechbackend.repository.NewsletterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/newsletter")
@CrossOrigin(origins = "http://localhost:4200")
public class NewsletterController {

    @Autowired
    private NewsletterRepository newsletterRepository;

    @PostMapping("/subscribe")
    public ResponseEntity<?> sInscrire(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");

        if (email == null || !email.contains("@")) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email invalide."));
        }

        if (newsletterRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Cet email est déjà inscrit !"));
        }

        Newsletter nouvelAbonne = new Newsletter();
        nouvelAbonne.setEmail(email);
        newsletterRepository.save(nouvelAbonne);

        return ResponseEntity.ok(Map.of("message", "Merci pour votre inscription !"));
    }
}