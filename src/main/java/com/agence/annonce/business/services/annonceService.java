package com.agence.annonce.business.services;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.agence.annonce.dao.entities.Annonce;
import com.agence.annonce.dao.entities.Category;
import com.agence.annonce.dao.entities.Type;

public interface AnnonceService {
    List<Annonce> getAllAnnonce();
    Annonce getAnnoncebyId(Long id);
    List<Annonce> getAnnonceByType(Type type);
    List<Annonce> getAnnonceByCategory(Category category);
    List<Annonce> getAnnonceByTypeAndCategory(Type type,Category category);
    List<Annonce> getAnnonceSortedByPrice(String order);
    Page<Annonce> getAllAnnoncePagination(Pageable pegeable);

    Page<Annonce> getAnnonceSortedByPricePagination(String order,Pageable pegeable);
  Page<Annonce> getAnnonceByTypeAndCategoryPagination(Type type, Category category, PageRequest pageRequest);

Page<Annonce> getAnnonceByTypePagination(Type type, PageRequest pageRequest);

Page<Annonce> getAnnonceByCategoryPagination(Category category, PageRequest pageRequest);

    //Create
    Annonce addAnnonce(Annonce annonce);
    //Update
    Annonce updateAnnonce(Annonce annonce);
    //Delete
    void deleteAnnonceById(Long id);
    
    
    
}