package com.example.kingtechbackend.service;

import com.example.kingtechbackend.model.Categorie;
import com.example.kingtechbackend.model.Discussion;
import com.example.kingtechbackend.model.Message;
import com.example.kingtechbackend.repository.CategorieRepository;
import com.example.kingtechbackend.repository.DiscussionRepository;
import com.example.kingtechbackend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ForumService {

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private MessageRepository messageRepository;

    // --- Méthodes pour les Catégories ---
    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    public Categorie creerCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    // --- Méthodes pour les Discussions ---
    public List<Discussion> getDernieresDiscussions() {
        return discussionRepository.findAllByOrderByDateCreationDesc();
    }

    public List<Discussion> getDiscussionsParCategorie(Long categorieId) {
        return discussionRepository.findByCategorieIdOrderByDateCreationDesc(categorieId);
    }

    public Discussion creerDiscussion(Discussion discussion, Long categorieId) {
        Categorie categorie = categorieRepository.findById(categorieId)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));

        discussion.setCategorie(categorie);

        // On sécurise la date de création si elle n'est pas fournie
        if (discussion.getDateCreation() == null) {
            discussion.setDateCreation(LocalDateTime.now());
        }

        // On met à jour le compteur global de la catégorie (+1 sujet).
        categorie.setNombreSujets(categorie.getNombreSujets() + 1);
        categorieRepository.save(categorie);

        return discussionRepository.save(discussion);
    }

    // --- Méthodes pour les Messages (Réponses) ---
    public Message ajouterMessage(Message message, Long discussionId) {
        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new RuntimeException("Discussion introuvable"));

        message.setDiscussion(discussion);

        if (message.getDateCreation() == null) {
            message.setDateCreation(LocalDateTime.now());
        }

        // Nombre Réponse dans la discussion concernée
        discussion.setNombreReponses(discussion.getNombreReponses() + 1);

        // Nombre Message dans la catégorie globale
        Categorie cat = discussion.getCategorie();
        cat.setNombreMessages(cat.getNombreMessages() + 1);

        // On sauvegarde les mises à jour des compteurs
        categorieRepository.save(cat);
        discussionRepository.save(discussion);

        // On sauvegarde enfin le message
        return messageRepository.save(message);
    }

    // Récupérer une discussion par son ID
    public Discussion getDiscussionById(Long id) {
        return discussionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discussion introuvable"));
    }

    // Récupérer tous les messages d'une discussion
    public List<Message> getMessagesParDiscussion(Long discussionId) {
        return messageRepository.findByDiscussionIdOrderByDateCreationAsc(discussionId);
    }
}