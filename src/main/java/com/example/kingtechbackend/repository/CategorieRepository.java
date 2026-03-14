package com.example.kingtechbackend.repository;

import com.example.kingtechbackend.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}