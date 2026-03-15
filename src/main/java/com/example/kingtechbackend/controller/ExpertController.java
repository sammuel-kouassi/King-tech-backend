package com.example.kingtechbackend.controller;

import com.example.kingtechbackend.model.MessageExpert;
import com.example.kingtechbackend.model.Utilisateur;
import com.example.kingtechbackend.repository.MessageExpertRepository;
import com.example.kingtechbackend.repository.UtilisateurRepository;
import com.example.kingtechbackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experts")
@CrossOrigin(origins = "http://localhost:4200")
public class ExpertController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private MessageExpertRepository messageExpertRepository;

    @Autowired
    private EmailService emailService;

    // 1. Récupérer tous les experts pour la barre latérale
    @GetMapping
    public ResponseEntity<List<Utilisateur>> getExperts() {
        return ResponseEntity.ok(utilisateurRepository.findByRole("EXPERT"));
    }

    // 2. Récupérer l'historique du chat entre l'utilisateur connecté et un expert
    @GetMapping("/chat")
    public ResponseEntity<List<MessageExpert>> getConversation(
            @RequestParam Long userId,
            @RequestParam Long expertId) {
        return ResponseEntity.ok(messageExpertRepository.findConversation(userId, expertId));
    }

    // 3. Envoyer un nouveau message privé
    @PostMapping("/chat")
    public ResponseEntity<?> envoyerMessage(@RequestBody MessageExpert messageInfo,
                                            @RequestParam Long expediteurId,
                                            @RequestParam Long destinataireId) {

        Utilisateur expediteur = utilisateurRepository.findById(expediteurId)
                .orElseThrow(() -> new RuntimeException("Expéditeur introuvable"));
        Utilisateur destinataire = utilisateurRepository.findById(destinataireId)
                .orElseThrow(() -> new RuntimeException("Destinataire introuvable"));

        messageInfo.setExpediteur(expediteur);
        messageInfo.setDestinataire(destinataire);

        MessageExpert messageSauvegarde = messageExpertRepository.save(messageInfo);

        emailService.envoyerNotificationExpert(
                destinataire.getEmail(),
                expediteur.getPrenom() + " " + expediteur.getNom(),
                messageInfo.getContenu()
        );
        return ResponseEntity.ok(messageSauvegarde);
    }

    // NOUVEAU : Récupérer la liste des clients d'un expert
    @GetMapping("/{expertId}/clients")
    public ResponseEntity<List<Utilisateur>> getClients(@PathVariable Long expertId) {
        return ResponseEntity.ok(messageExpertRepository.findClientsParExpert(expertId));
    }
}