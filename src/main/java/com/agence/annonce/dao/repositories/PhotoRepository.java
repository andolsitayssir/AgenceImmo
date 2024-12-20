package com.agence.annonce.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agence.annonce.dao.entities.Annonce;
import com.agence.annonce.dao.entities.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByAnnonce(Annonce annonce);
    void deleteByAnnonce(Annonce annonce);
    void deleteById(Long photoId);
   

}