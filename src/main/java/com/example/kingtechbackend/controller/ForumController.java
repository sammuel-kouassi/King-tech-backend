package com.example.kingtechbackend.controller;

import com.example.kingtechbackend.model.Categorie;
import com.example.kingtechbackend.model.Discussion;
import com.example.kingtechbackend.model.Message;
import com.example.kingtechbackend.repository.MessageRepository;
import com.example.kingtechbackend.repository.UtilisateurRepository;
import com.example.kingtechbackend.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/forum")
@CrossOrigin(origins = "http://localhost:4200")
public class ForumController {

    @Autowired
    private ForumService forumService;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private MessageRepository messageRepository;

    // --- Routes GET (Pour afficher dans Angular) ---

    @GetMapping("/categories")
    public ResponseEntity<List<Categorie>> getCategories() {
        return ResponseEntity.ok(forumService.getAllCategories());
    }

    @GetMapping("/discussions")
    public ResponseEntity<List<Discussion>> getDernieresDiscussions() {
        return ResponseEntity.ok(forumService.getDernieresDiscussions());
    }

    @GetMapping("/categories/{categorieId}/discussions")
    public ResponseEntity<List<Discussion>> getDiscussionsDeCategorie(@PathVariable Long categorieId) {
        return ResponseEntity.ok(forumService.getDiscussionsParCategorie(categorieId));
    }

    // --- Routes POST (Pour ajouter des données via Postman ou Angular) ---

    @PostMapping("/categories")
    public ResponseEntity<Categorie> ajouterCategorie(@RequestBody Categorie categorie) {
        return ResponseEntity.ok(forumService.creerCategorie(categorie));
    }

    @PostMapping("/categories/{categorieId}/discussions")
    public ResponseEntity<Discussion> ajouterDiscussion(
            @PathVariable Long categorieId,
            @RequestBody Discussion discussion) {
        return ResponseEntity.ok(forumService.creerDiscussion(discussion, categorieId));
    }

    @PostMapping("/discussions/{discussionId}/messages")
    public ResponseEntity<Message> ajouterMessage(
            @PathVariable Long discussionId,
            @RequestBody Message message) {
        return ResponseEntity.ok(forumService.ajouterMessage(message, discussionId));
    }

    @GetMapping("/discussions/{id}")
    public ResponseEntity<Discussion> getDiscussion(@PathVariable Long id) {
        return ResponseEntity.ok(forumService.getDiscussionById(id));
    }

    @GetMapping("/discussions/{discussionId}/messages")
    public ResponseEntity<List<Message>> getMessagesDeDiscussion(@PathVariable Long discussionId) {
        return ResponseEntity.ok(forumService.getMessagesParDiscussion(discussionId));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getForumStats() {
        Map<String, Long> stats = new HashMap<>();

        // 1. Total des membres (tous les utilisateurs)
        stats.put("membres", utilisateurRepository.count());

        // 2. Total des messages du forum
        stats.put("messages", messageRepository.count());

        // 3. Total des experts (pour "experts en ligne", c'est une bonne approximation pour commencer)
        stats.put("experts", utilisateurRepository.countByRole("EXPERT"));

        return ResponseEntity.ok(stats);
    }
}