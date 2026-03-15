package com.example.kingtechbackend.repository;


import com.example.kingtechbackend.model.Cours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursRepository extends JpaRepository<Cours, Long> {
}