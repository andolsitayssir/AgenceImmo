package com.agence.annonce.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.agence.annonce.business.services.AddresseService;
import com.agence.annonce.business.services.AnnonceService;
import com.agence.annonce.business.services.PhotoService;
import com.agence.annonce.dao.entities.Annonce;
import com.agence.annonce.dao.entities.Category;
import com.agence.annonce.dao.entities.Type;


import org.springframework.web.bind.annotation.RequestParam;


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
    public String getAllproduct(Model model) {
        List<Annonce> annonces = annonceService.getAllAnnonce();
        model.addAttribute("annonces", annonces);
        model.addAttribute("categories", Category.values());
        model.addAttribute("types", Type.values());
        return "landing-page";
    }
    @RequestMapping("/filter")
    public String filterAnnonces(Model model, @RequestParam String type, @RequestParam String category) {
        List<Annonce> annonces = annonceService.getAllAnnonce();
        List<Annonce> filteredAnnonces = new ArrayList<Annonce>();
        for (Annonce annonce : annonces) {
            if (annonce.getType().toString().equals(type) && annonce.getCategory().toString().equals(category)) {
                filteredAnnonces.add(annonce);
            }
        }
        model.addAttribute("annonces", filteredAnnonces);
        model.addAttribute("categories", Category.values());
        model.addAttribute("types", Type.values());
        return "landing-page";
    }
    
    
}
