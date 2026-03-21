package com.example.kingtechbackend.service;

import com.example.kingtechbackend.dto.CommandeRequestDTO;
import com.example.kingtechbackend.dto.LigneCommandeRequestDTO;
import com.example.kingtechbackend.model.Commande;
import com.example.kingtechbackend.model.LigneCommande;
import com.example.kingtechbackend.model.Produit;
import com.example.kingtechbackend.repository.CommandeRepository;
import com.example.kingtechbackend.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Transactional // Garantit que si une erreur survient, rien n'est sauvegardé à moitié
    public Commande creerCommande(CommandeRequestDTO request) {
        Commande commande = new Commande();
        commande.setDateCommande(LocalDateTime.now());
        commande.setStatut("VALIDEE");

        // Infos client
        commande.setNomClient(request.nomClient());
        commande.setEmailClient(request.emailClient());
        commande.setAdresseLivraison(request.adresseLivraison());
        commande.setTelephone(request.telephone());

        // Génération du numéro de commande (Ex : KT-2026-0001)
        int anneeEnCours = Calendar.getInstance().get(Calendar.YEAR);
        long nombreCommandesExistantes = commandeRepository.count();
        String numCommande = String.format("KT-%d-%04d", anneeEnCours, nombreCommandesExistantes + 1);
        commande.setNumeroCommande(numCommande);

        // SÉCURITÉ 1 : On s'assure que la liste des lignes est initialisée pour éviter le NullPointerException
        if (commande.getLignes() == null) {
            commande.setLignes(new ArrayList<>());
        }

        double totalCommande = 0.0;

        for (LigneCommandeRequestDTO ligneDTO : request.lignes()) {
            Produit produit = produitRepository.findById(ligneDTO.produitId())
                    .orElseThrow(() -> new RuntimeException("Produit non trouvé : " + ligneDTO.produitId()));

            // SÉCURITÉ 2 : On gère le cas où la case stock est vide (null) dans Supabase
            int stockDisponible = (produit.getStock() != null) ? produit.getStock() : 0;

            if (stockDisponible < ligneDTO.quantite()) {
                throw new RuntimeException("Stock insuffisant pour le produit : " + produit.getNom());
            }

            // Création de la ligne de commande
            LigneCommande ligne = new LigneCommande();
            ligne.setProduit(produit);
            ligne.setQuantite(ligneDTO.quantite());
            ligne.setPrixUnitaire(produit.getPrix()); // On fige le prix actuel

            double sousTotal = produit.getPrix() * ligneDTO.quantite();
            ligne.setSousTotal(sousTotal);

            // Lier la ligne à la commande
            ligne.setCommande(commande);
            commande.getLignes().add(ligne);

            // Ajouter au total global
            totalCommande += sousTotal;

            // Mise à jour du stock du produit (On déduit ce qui a été acheté)
            produit.setStock(stockDisponible - ligneDTO.quantite());
            produitRepository.save(produit);
        }

        commande.setTotal(totalCommande);

        // Sauvegarde finale en base de données (CascadeType.ALL va aussi sauvegarder les lignes)
        return commandeRepository.save(commande);
    }

    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }
}