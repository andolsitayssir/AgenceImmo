package com.agence.annonce.business.services;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.agence.annonce.dao.entities.Annonce;
import com.agence.annonce.dao.entities.Category;
import com.agence.annonce.dao.entities.Type;

public interface AnnonceService {
    List<Annonce> getAllAnnonce();
    Annonce getAnnoncebyId(Long id);
    List<Annonce> getAnnonceByType(Type type);
    List<Annonce> getAnnonceByCategory(Category category);
    List<Annonce> getAnnonceSortedByPrice(String order);
    Page<Annonce> getAllAnnoncePagination(Pageable pegeable);
    Page<Annonce> getAnnonceSortedByAgePagination(String order,Pageable pegeable);

    //Create
    Annonce addAnnonce(Annonce annonce);
    //Update
    Annonce updateAnnonce(Annonce annonce);
    //Delete
    void deleteAnnonceById(Long id);
    
    
    
}