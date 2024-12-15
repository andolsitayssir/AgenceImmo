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
import org.springframework.web.bind.annotation.GetMapping;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
       
    @RequestMapping("/property-list")
    public String getAllproduct(@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "5") int pageSize,Model model) {
       // List<Annonce> annonces = annonceService.getAllAnnonce();
       Page<Annonce> propertyPage = annonceService.getAllAnnoncePagination(PageRequest.of(page,pageSize));
        model.addAttribute("annonces", propertyPage.getContent());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", propertyPage.getTotalPages());
        return "property-list";
    }


    @GetMapping("/create-property")
    public String showAddProperty(Model model) {
        model.addAttribute("annonceForm", new annonceForm());
        model.addAttribute("categories", Category.values());
        return "add-property";
    }



     @RequestMapping(path="/create-property", method= RequestMethod.POST)
    public String addProperty(@Valid @ModelAttribute annonceForm annonceForm, BindingResult bindingResult,Model model,@RequestParam("photos") MultipartFile[] photos){
        Address address = new Address(annonceForm.getGovernorate(),annonceForm.getCity(),annonceForm.getStreet());
        this.addresseService.addAddress(address);   


        List<Photo> photosList = new ArrayList<Photo>();
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Invalid input");
            model.addAttribute("categories", Category.values());
            return "add-property";
        }


           Annonce annonce =new Annonce(null, annonceForm.getTitre(), annonceForm.getDescription(), annonceForm.getSurface(), annonceForm.getPrice(), annonceForm.getType(), annonceForm.getCategory(), address, annonceForm.getTel(), null);
            if (photos.length > 0) {
            
                for (MultipartFile photo : photos) {
                    
                StringBuilder fileName = new StringBuilder();
                fileName.append(photo.getOriginalFilename());
                Path newFilePath = Paths.get(uploadDirectory, fileName.toString());
                try {
                    Files.write(newFilePath, photo.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                photosList.add(new Photo(null, fileName.toString(), annonce ));
                
                
            }
            annonce.setPhotos(photosList);
             this.annonceService.addAnnonce(annonce);
        }     
        

         return "redirect:/annonces/property-list";
    
        
    
    }


    @RequestMapping("/edit-property")
    public String showEditProperty(Model model) {
        model.addAttribute("annonceForm", new annonceForm());
        return "edit-property";
    }
    

    
    

}