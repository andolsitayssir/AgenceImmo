package com.agence.annonce.web.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.agence.annonce.dao.entities.Annonce;
import com.agence.annonce.dao.entities.Category;
import com.agence.annonce.dao.entities.Photo;

import com.agence.annonce.web.models.annonceForm;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.agence.annonce.business.services.AddresseService;
import com.agence.annonce.business.services.AnnonceService;
import com.agence.annonce.business.services.PhotoService;
import com.agence.annonce.dao.entities.Address;

import org.springframework.mock.web.MockMultipartFile;



@Controller
@RequestMapping("/annonces")
public class AnnonceController  {


    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images";

    private final AnnonceService annonceService;
    private final AddresseService addresseService;
    private final PhotoService photoService;
    public AnnonceController( AnnonceService annonceService,AddresseService addresseService, PhotoService photoService){

        this.annonceService=annonceService;
        this.addresseService=addresseService;
        this.photoService=photoService;
    }
       
    @RequestMapping("")
    public String getAllproduct(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "4") int pageSize,
                                Model model) {
       // List<Annonce> annonces = annonceService.getAllAnnonce();
       Page<Annonce> propertyPage = annonceService.getAllAnnoncePagination(PageRequest.of(page,pageSize));
        model.addAttribute("annonces", propertyPage.getContent());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", propertyPage.getTotalPages());
        return "property-list";
    }

    @RequestMapping("/sort")
    public String getAnnoncesSorted(@RequestParam(required=false,defaultValue="asc") String orderByPrice, 
                                Model model,
                                @RequestParam(defaultValue="0") int page,
                                @RequestParam(defaultValue="4") int pageSize) {
       Page<Annonce> propertyPage = annonceService.getAnnonceSortedByPricePagination(orderByPrice, PageRequest.of(page,pageSize));
        model.addAttribute("annonces", propertyPage.getContent());
        model.addAttribute("orderByPrice", orderByPrice);
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
                for (MultipartFile photo : photos) {
                    if (!photo.isEmpty()) { 
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

            this.annonceService.addAnnonce(annonce);
            return "redirect:/annonces";
    }

    public MultipartFile convertPhotoToMultipartFile(Photo photo) throws IOException {
        
        File file = new File(photo.getUrl());
    
        
        if (file.exists() && file.isFile()) {
            
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] data = fileInputStream.readAllBytes();
            fileInputStream.close();
    
          
            return new MockMultipartFile(
                "file",                          
                file.getName(),                    
                "image/jpeg",                      
                data                               
            );
        } else {
            
            throw new IOException("File not found at URL: " + photo.getUrl());
        }
    }
    
    
    
    @RequestMapping("{id}/edit-property")
    public String showEditProperty(@PathVariable Long id,Model model) {
        Annonce annonce = this.annonceService.getAnnoncebyId(id);
        List<Photo> photos = photoService.getPhotoByAnnonce(annonce);

        model.addAttribute("photos", photos);
        model.addAttribute("annonceForm", new annonceForm(annonce.getTitre(),annonce.getType(),annonce.getCategory(), annonce.getDescription(),annonce.getTel(), annonce.getSurface(), annonce.getPrice(), annonce.getAddress().getGovernorate(), annonce.getAddress().getCity(), annonce.getAddress().getStreet(),null));
        model.addAttribute("annonce_id", id);
        model.addAttribute("categories", Category.values());
        return "edit-property";
    }

   
    @RequestMapping(path = "{id}/edit-property", method = RequestMethod.POST)
    public String editProperty(
        @Valid @ModelAttribute annonceForm annonceForm,
        BindingResult bindingResult,
        @PathVariable Long id,
        Model model,
        @RequestParam("photos") MultipartFile[] photos) {

    if (bindingResult.hasErrors()) {
        model.addAttribute("categories", Category.values());
        return "edit-property";
    }

    Annonce annonce = annonceService.getAnnoncebyId(id);
    Address address = annonce.getAddress();

    address.setGovernorate(annonceForm.getGovernorate());
    address.setCity(annonceForm.getCity());
    address.setStreet(annonceForm.getStreet());

     annonce.setTitre(annonceForm.getTitre());
    annonce.setDescription(annonceForm.getDescription());
    annonce.setSurface(annonceForm.getSurface());
    annonce.setPrice(annonceForm.getPrice());
    annonce.setType(annonceForm.getType());
    annonce.setCategory(annonceForm.getCategory());
    annonce.setTel(annonceForm.getTel());

    List<Photo> existingPhotos = annonce.getPhotos();
    if (existingPhotos == null) {
        existingPhotos = new ArrayList<>();
        annonce.setPhotos(existingPhotos);
    }

    
    for (MultipartFile photo : photos) {
        if (!photo.isEmpty()) {
            String fileName = photo.getOriginalFilename();
            Path filePath = Paths.get(uploadDirectory, fileName);
            try {
                Files.write(filePath, photo.getBytes());
                existingPhotos.add(new Photo(null, fileName, annonce)); // Add to the existing collection
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

  
    addresseService.updateAddress(address);
    annonceService.updateAnnonce(annonce);

    return "redirect:/annonces";
}

  @PostMapping("/photos/{photoId}/delete")
  public String deletePhoto(@PathVariable Long photoId, Model model) {
    Photo photo = photoService.getPhotoById(photoId);
        Annonce annonce = photo.getAnnonce();
        Long annonceId = annonce.getAnnonce_id();
        annonce.getPhotos().remove(photo);
        annonceService.updateAnnonce(annonce);
        Path filePath = Paths.get(uploadDirectory, photo.getUrl());
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            photoService.deletePhotoById(photoId);
            model.addAttribute("annonceId", annonceId);

            return "redirect:/annonces/" + annonceId + "/edit-property";
        }
    

    @RequestMapping(path = "{id}/delete-property", method = RequestMethod.POST)
    public String deleteAnnonce(@PathVariable Long id) {
        Annonce annonce = this.annonceService.getAnnoncebyId(id);
        if (annonce != null) {
           
        this.annonceService.deleteAnnonceById(id);

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
        }
        return "redirect:/annonces";
      
    }
  
}

    
    

