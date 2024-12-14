package com.agence.annonce.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agence.annonce.dao.entities.Annonce;
import com.agence.annonce.dao.entities.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByAnnonce(Annonce annonce);
}