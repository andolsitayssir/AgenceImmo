package com.agence.annonce.web.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.agence.annonce.dao.entities.Annonce;
import com.agence.annonce.dao.entities.Category;
import com.agence.annonce.dao.entities.Photo;

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
       
    @RequestMapping("/property-list")
    public String getAllproduct(Model model) {
        List<Annonce> annonces = annonceService.getAllAnnonce();
        model.addAttribute("annonces", annonces);
        
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
                for (MultipartFile photo : photos) {
                    if (!photo.isEmpty()) { // Check if the photo is not empty
                        StringBuilder fileName = new StringBuilder();
                        fileName.append(photo.getOriginalFilename());
                        Path newFilePath = Paths.get(uploadDirectory, fileName.toString());
                        try {
                            Files.write(newFilePath, photo.getBytes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        photosList.add(new Photo(null, fileName.toString(), annonce));
                    }
                }
                if (!photosList.isEmpty()) {
                    annonce.setPhotos(photosList);
                }
            this.annonceService.addAnnonce(annonce); // Save the annonce, which will also save the photos due to cascade
            return "redirect:/annonces/property-list";
    }


    @RequestMapping("{id}/edit-property")
    public String showEditProperty(@PathVariable Long id,Model model) {
        Annonce annonce = this.annonceService.getAnnoncebyId(id);
        model.addAttribute("annonceForm", new annonceForm());
        return "edit-property";
    }

   
    @RequestMapping(path="{id}/edit-property", method= RequestMethod.POST)
    public String editProperty(@Valid @ModelAttribute annonceForm annonceForm, BindingResult bindingResult,@PathVariable Long id,Model model,@RequestParam("photos") MultipartFile[] photos){
        if (bindingResult.hasErrors()) {
            return "edit-person";
        }
        Annonce annonce = this.annonceService.getAnnoncebyId(id);
        Address address = this.addresseService.getAddressbyAnnonce(annonce);
        List<Photo> photosList = this.photoService.getPhotoByAnnonce(annonce);
        annonce.setTitre(annonceForm.getTitre());
        annonce.setDescription(annonceForm.getDescription());
        annonce.setSurface(annonceForm.getSurface());
        annonce.setPrice(annonceForm.getPrice());
        annonce.setType(annonceForm.getType());
        annonce.setCategory(annonceForm.getCategory());
        annonce.setTel(annonceForm.getTel());
        address.setGovernorate(annonceForm.getGovernorate());
        address.setCity(annonceForm.getCity());
        address.setStreet(annonceForm.getStreet());
        annonce.setAddress(address);
        for (MultipartFile photo : photos) {
            if (!photo.isEmpty()) { // Check if the photo is not empty
                StringBuilder fileName = new StringBuilder();
                fileName.append(photo.getOriginalFilename());
                Path newFilePath = Paths.get(uploadDirectory, fileName.toString());
                try {
                    Files.write(newFilePath, photo.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                photosList.add(new Photo(null, fileName.toString(), annonce));
            }
        }
        if (!photosList.isEmpty()) {
            annonce.setPhotos(photosList);
        }
        this.addresseService.updateAddress(address); // Save the address
        this.annonceService.updateAnnonce(annonce); // Save the annonce
        return "redirect:/annonces/property-list";
    }

    @RequestMapping(path = "{id}/delete", method = RequestMethod.POST)
    public String deleteAnnonce(@PathVariable Long id) {
        Annonce annonce = this.annonceService.getAnnoncebyId(id);
        this.addresseService.deleteAddressByAnnonce(annonce);

        List<Photo> photosList = this.photoService.getPhotoByAnnonce(annonce);
        for (Photo photo : photosList) {
            Path filePath = Paths.get(uploadDirectory, photo.getUrl());
            try {
                Files.deleteIfExists(filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.photoService.deletePhotoByAnnonce(annonce);
        }

            return "redirect:/annonces/property-list";
        }
      
    }

    
    

