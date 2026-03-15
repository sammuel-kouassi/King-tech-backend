package com.example.kingtechbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // 1. Envoyer une notification à un expert
    @Async
    public void envoyerNotificationExpert(String emailExpert, String nomClient, String messageApercu) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailExpert);
        message.setSubject("NOUVEAU MESSAGE - KING-TECH Expert");
        message.setText("Bonjour,\n\n" +
                "Vous avez reçu un nouveau message de " + nomClient + " sur la plateforme KING-TECH.\n\n" +
                "Aperçu du message : \n\"" + messageApercu + "\"\n\n" +
                "Veuillez vous connecter pour lui répondre.\n\n" +
                "L'équipe KING-TECH");

        mailSender.send(message);
    }

    // 2. Envoyer un reçu d'achat au client
    @Async
    public void envoyerRecuAchat(String emailClient, String nomClient, String numeroCommande, double montantTotal) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailClient);
        message.setSubject("Reçu de votre commande KING-TECH #" + numeroCommande);
        message.setText("Bonjour " + nomClient + ",\n\n" +
                "Merci pour votre achat sur KING-TECH !\n" +
                "Votre commande numéro " + numeroCommande + " d'un montant de " + montantTotal + " FCFA a bien été validée.\n\n" +
                "Nos équipes préparent votre matériel électronique.\n\n" +
                "À très bientôt,\nL'équipe KING-TECH");

        mailSender.send(message);
    }
}