package com.agence.annonce.web.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.agence.annonce.dao.entities.Annonce;
import com.agence.annonce.web.models.annonceForm;

import jakarta.validation.Valid;

import com.agence.annonce.business.services.annonceService;

@Controller
@RequestMapping("/annonces")
public class AnnonceController {

    private static List<Annonce> annonces = new ArrayList<Annonce>();
    private static Long idCount =0L;
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images";


    private final annonceService annonceService;
    public AnnonceController( annonceService annonceService){
        this.annonceService=annonceService;
    }


     @RequestMapping("/create-property")
    public String showAddProperty(Model model) {
        model.addAttribute("annonceForm", new annonceForm());
        return "add-property";
    }

    @RequestMapping(path="/create-property", method= RequestMethod.POST)
    public String addProperty(@Valid @ModelAttribute annonceForm annonceForm, BindingResult bindingResult,Model model,@RequestParam MultipartFile file){
            if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Invalid input");
            return "add-person";
        }
        if (!file.isEmpty()) {
            StringBuilder fileName = new StringBuilder();
            fileName.append(file.getOriginalFilename());
            Path newFilePath = Paths.get(uploadDirectory, fileName.toString());

            try {
                Files.write(newFilePath, file.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.annonceService
                .addAnnonce(new Annonce(null, annonceForm.getTitre(),annonceForm.getDescription(),annonceForm.getSurface(),annonceForm.getPrice(),annonceForm.getType(),annonceForm.getCategory(),annonceForm.getTel()));
        }//  else {

        //     .addAnnonce(new Annonce(null, annonceForm.getTitre(),annonceForm.getDescription(),annonceForm.getSurface(),annonceForm.getPrice(),annonceForm.getType(),annonceForm.getCategory(),annonceForm.getTel()));
        // }
        
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