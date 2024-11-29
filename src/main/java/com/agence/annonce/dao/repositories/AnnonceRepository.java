package com.agence.annonce.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agence.annonce.dao.entities.Annonce;

public interface AnnonceRepository extends JpaRepository<Annonce,Long> {
    
}