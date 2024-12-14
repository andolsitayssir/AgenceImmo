package com.agence.annonce.web.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.agence.annonce.dao.entities.Annonce;
import com.agence.annonce.dao.entities.Category;
import com.agence.annonce.dao.entities.Photo;
import com.agence.annonce.dao.repositories.AddresseRepository;
import com.agence.annonce.web.models.annonceForm;

import jakarta.validation.Valid;


import com.agence.annonce.business.services.AddresseService;
import com.agence.annonce.business.services.AnnonceService;
import com.agence.annonce.business.services.PhotoService;
import com.agence.annonce.dao.entities.Address;


@Controller
@RequestMapping("/annonces")
public class AnnonceController  {

    private static List<Annonce> annonces = new ArrayList<Annonce>();
    private static Long idCount =0L;
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images";

    private final AnnonceService annonceService;
    private final AddresseService addresseService;
    private final PhotoService photoService;
    public AnnonceController( AnnonceService annonceService,AddresseService addresseService, PhotoService photoService){

        this.annonceService=annonceService;
        this.addresseService=addresseService;
        this.photoService=photoService;
    }
       
    


    @RequestMapping("/create-property")
    public String showAddProperty(Model model) {
        model.addAttribute("annonceForm", new annonceForm());
        model.addAttribute("categories", Category.values());
        return "add-property";
    }



     @RequestMapping(path="/create-property", method= RequestMethod.POST)
    public String addProperty(@Valid @ModelAttribute annonceForm annonceForm, BindingResult bindingResult,Model model,@RequestParam MultipartFile photos){
        Address address = new Address(annonceForm.getGovernorate(),annonceForm.getCity(),annonceForm.getStreet());
        this.addresseService.addAddress(address);   

        List<Photo> photosList = new ArrayList<Photo>();
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Invalid input");
            model.addAttribute("categories", Category.values());
            return "add-property";
        }
        if (!photos.isEmpty()) {
            StringBuilder fileName = new StringBuilder();
            for (Photo photo : photos) {
                
           
            fileName.append(photo.getOriginalFilename());
            Path newFilePath = Paths.get(uploadDirectory, fileName.toString());

            try {
                Files.write(newFilePath, photos.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            photosList.add(new Photo(null, fileName.toString()));
        }
            this.annonceService.
            addAnnonce(new Annonce(null, annonceForm.getTitre(), annonceForm.getDescription(), annonceForm.getSurface(), annonceForm.getPrice(), annonceForm.getType(), annonceForm.getCategory(), address, annonceForm.getTel(), photosList));
        }     
        
        else {
            this.annonceService
            .addAnnonce(new Annonce(null, annonceForm.getTitre(), annonceForm.getDescription(), annonceForm.getSurface(), annonceForm.getPrice(), annonceForm.getType(), annonceForm.getCategory(), address, annonceForm.getTel(), null));
        }
         return "redirect:/property-list";
    }
        
    



    @RequestMapping("/edit-property")
    public String showEditProperty(Model model) {
        model.addAttribute("annonceForm", new annonceForm());
        return "edit-property";
    }

    @RequestMapping("/property-list")
    public String showPropertyList(Model model) {
        model.addAttribute("annonceForm", new annonceForm());
        return "property-list";
    }
    
    

}