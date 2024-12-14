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

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/annonces")
public class LandingPageController {
    private static List<Annonce> annonces = new ArrayList<Annonce>();
    private static Long idCount =0L;

    private final AnnonceService annonceService;
    private final AddresseService addresseService;
    private final PhotoService photoService;
      public LandingPageController( AnnonceService annonceService,AddresseService addresseService, PhotoService photoService){

        this.annonceService=annonceService;
        this.addresseService=addresseService;
        this.photoService=photoService;
    }
    @RequestMapping("/landing-page")
    public String getAllproduct(Model model) {
        List<Annonce> annonces = annonceService.getAllAnnonce();
       

        model.addAttribute("annonces", annonces);
        
        return "landing-page";
    }
    
    
}
