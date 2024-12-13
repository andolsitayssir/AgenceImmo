package com.agence.annonce.business.servicesImpl;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.agence.annonce.business.services.AnnonceService;
import com.agence.annonce.dao.entities.Annonce;
import com.agence.annonce.dao.entities.Category;
import com.agence.annonce.dao.entities.Type;
import com.agence.annonce.dao.repositories.AnnonceRepository;

public class AnnonceServiceImpl implements AnnonceService {
    private final AnnonceRepository annonceRepository;
    public AnnonceServiceImpl(AnnonceRepository annonceRepository){
       this.annonceRepository=annonceRepository;
    }

    @Override
    public List<Annonce> getAllAnnonce() {
        return this.annonceRepository.findAll();
    }

    @Override
    public Annonce getAnnoncebyId(Long id) {
        if(id==null){
            return null;
        }
        return this.annonceRepository.findById(id).get();
    }

    @Override
    public List<Annonce> getAnnonceByType(Type type) {
        return this.annonceRepository.findByType(type);
    }

    @Override
    public List<Annonce> getAnnonceByCategory(Category category) {
        return this.annonceRepository.findByCategory(category);
    }

    @Override
    public List<Annonce> getAnnonceSortedByPrice(String order) {
        Sort.Direction direction = Sort.Direction.ASC;
        if("desc".equalsIgnoreCase(order)){
            direction = Sort.Direction.DESC;
        }
        return annonceRepository.findAll(Sort.by(direction,"price"));
    }

    @Override
    public Page<Annonce> getAllAnnoncePagination(Pageable pegeable) {
        if (pegeable == null) {
            return null;
        }
        return this.annonceRepository.findAll(pegeable);
    }

    @Override
    public Page<Annonce> getAnnonceSortedByAgePagination(String order, Pageable pegeable) {
        if(pegeable ==null){
            return null;
        }  
        Sort.Direction direction= Sort.Direction.ASC;
        if("desc".equalsIgnoreCase(order)){
            direction= Sort.Direction.DESC;
        }
        Pageable sortedPageable=PageRequest.of(
            pegeable.getPageNumber(),
            pegeable.getPageSize(),
            Sort.by(direction,"age")
        );
        return this.annonceRepository.findAll(sortedPageable);
    }





    @Override
    public Annonce addAnnonce(Annonce annonce) {
        if(annonce==null){
            return null;
        }
       return this.annonceRepository.save(annonce);
    }

    @Override
    public Annonce updateAnnonce(Annonce annonce) {
        if(annonce==null){
            return null;
        }
       return this.annonceRepository.save(annonce);
    }

    @Override
    public void deleteAnnonceById(Long id) {
        if(id==null){
            return ;
        }
         this.annonceRepository.deleteById(id);
        
    }

    

}