package com.agence.annonce.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.agence.annonce.business.services.AddresseService;
import com.agence.annonce.business.services.AnnonceService;
import com.agence.annonce.business.services.PhotoService;
import com.agence.annonce.dao.entities.Annonce;
import com.agence.annonce.dao.entities.Category;
import com.agence.annonce.dao.entities.Photo;
import com.agence.annonce.dao.entities.Type;
import com.agence.annonce.web.models.annonceForm;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/home")
public class LandingPageController {
   
   
    private final PhotoService photoService;
    private final AddresseService addresseService;

    private final AnnonceService annonceService;

      public LandingPageController( AnnonceService annonceService,AddresseService addresseService, PhotoService photoService){

        this.annonceService=annonceService;
        this.addresseService=addresseService;
        this.photoService=photoService;
    }
    @RequestMapping("/landing-page")
    public String getAllproduct(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "6") int pageSize,
                                Model model) {
       Page<Annonce> propertyPage = annonceService.getAllAnnoncePagination(PageRequest.of(page,pageSize));
               for (Annonce property : propertyPage.getContent()) {
            List<Photo> photos = photoService.getPhotoByAnnonce(property);
            property.setPhotos(photos); 
        }
        model.addAttribute("annonces", propertyPage.getContent());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", page);

        model.addAttribute("totalPages", propertyPage.getTotalPages());
        model.addAttribute("types", Type.values());
        model.addAttribute("categories", Category.values());

     
        return "landing-page";
    }
    
    @RequestMapping("/filter")
    public String filterAnnonces(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "6") int pageSize,
                                 @RequestParam(required = false) String type,
                                 @RequestParam(required = false) String category,
                                 Model model) {
        Page<Annonce> filteredPage;
    
        if (type != null && !type.isEmpty() && category != null && !category.isEmpty()) {
            filteredPage = annonceService.getAnnonceByTypeAndCategoryPagination(
                    Type.valueOf(type), 
                    Category.valueOf(category), 
                    PageRequest.of(page, pageSize)
            );
        } else if (type != null && !type.isEmpty()) {
            filteredPage = annonceService.getAnnonceByTypePagination(
                    Type.valueOf(type), 
                    PageRequest.of(page, pageSize)
            );
        } else if (category != null && !category.isEmpty()) {
            filteredPage = annonceService.getAnnonceByCategoryPagination(
                    Category.valueOf(category), 
                    PageRequest.of(page, pageSize)
            );
        } else {
            filteredPage = annonceService.getAllAnnoncePagination(PageRequest.of(page, pageSize));
        }
    
        
        model.addAttribute("annonces", filteredPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", filteredPage.getTotalPages());
        model.addAttribute("types", Type.values());
        model.addAttribute("categories", Category.values());
    
        return "landing-page";
    }
    
    @RequestMapping("{id}/property-details")
    public String showPropertyDetails(@PathVariable Long id, Model model) {
        Annonce annonce = annonceService.getAnnoncebyId(id);
        List<Photo> photos = photoService.getPhotoByAnnonce(annonce);
         model.addAttribute("annonce", annonce);
         model.addAttribute("photos", photos);
         model.addAttribute("annonce_id", id);

        return "property-details";
    }
    

    
}
