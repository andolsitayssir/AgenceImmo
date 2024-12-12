package com.agence.annonce.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.agence.annonce.web.models.annonceForm;

@Controller
public class AnnonceController {

     @RequestMapping("/create-property")
    public String showAddProperty(Model model) {
        model.addAttribute("annonceForm", new annonceForm());
        return "add-property";
    }
    @RequestMapping("/edit-property")
    public String showEditProperty(Model model) {
        model.addAttribute("annonceForm", new annonceForm());
        return "edit-property";
    }
    
}