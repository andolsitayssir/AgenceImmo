package com.agence.annonce.dao.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agence.annonce.dao.entities.Annonce;
import com.agence.annonce.dao.entities.Category;
import com.agence.annonce.dao.entities.Type;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce,Long> {

    List<Annonce> findByTitre(String titre);
    List<Annonce> findByType(Type type);
    List<Annonce> findByCategory(Category category);
    List<Annonce> findByTypeAndCategory(Type type, Category category);
    Page<Annonce> findByTypeAndCategory(Type type, Category category, Pageable pageable);

    Page<Annonce> findByType(Type type, Pageable pageable);

    Page<Annonce> findByCategory(Category category, Pageable pageable); 
}