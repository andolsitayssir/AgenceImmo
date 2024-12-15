package com.agence.annonce.business.services;

import java.util.List;

import com.agence.annonce.dao.entities.Annonce;
import com.agence.annonce.dao.entities.Photo;

public interface PhotoService {
    List<Photo> getPhotoByAnnonce(Annonce annonce);
    Photo getPhotobyId(Long id);

    // Create 
    Photo addPhoto(Photo photo);
    // Delete
    void deletePhotoByAnnonce(Annonce annonce);
    

}
