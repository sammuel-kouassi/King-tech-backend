package com.example.kingtechbackend.repository;


import com.example.kingtechbackend.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    long count();
}