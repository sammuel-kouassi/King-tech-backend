package com.example.kingtechbackend.controller;

import com.example.kingtechbackend.model.Cours;
import com.example.kingtechbackend.repository.CoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formation")
@CrossOrigin(origins = "http://localhost:4200")
public class FormationController {

    @Autowired
    private CoursRepository coursRepository;

    @GetMapping("/cours")
    public ResponseEntity<List<Cours>> getAllCours() {
        return ResponseEntity.ok(coursRepository.findAll());
    }

    // Méthode utilitaire pour ajouter tes cours de test facilement
    @PostMapping("/cours")
    public ResponseEntity<Cours> creerCours(@RequestBody Cours cours) {
        return ResponseEntity.ok(coursRepository.save(cours));
    }
}