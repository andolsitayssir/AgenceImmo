package com.agence.annonce.business.servicesImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agence.annonce.business.services.PhotoService;
import com.agence.annonce.dao.entities.Annonce;
import com.agence.annonce.dao.entities.Photo;
import com.agence.annonce.dao.repositories.PhotoRepository;

@Service
public class PhotoServiceImpl implements PhotoService  {
    private final PhotoRepository photoRepository;
    public PhotoServiceImpl(PhotoRepository photoRepository){
       this.photoRepository=photoRepository;
    }


    @Override
    public List<Photo> getPhotoByAnnonce(Annonce annonce) {
        return this.photoRepository.findByAnnonce(annonce);
    }

    @Override
    public Photo getPhotobyId(Long id) {
        return this.photoRepository.findById(id).get();
    }

}