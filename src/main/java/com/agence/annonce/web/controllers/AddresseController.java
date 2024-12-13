package com.agence.annonce.web.controllers;

import java.util.ArrayList;
import java.util.List;

import com.agence.annonce.business.services.addresseService;
import com.agence.annonce.business.services.AnnonceService;
import com.agence.annonce.dao.entities.Address;

public class AddresseController {
    private static List<Address> annonces = new ArrayList<Address>();
    private static Long idCount =0L;

    private final addresseService addresseService;
    public AddresseController( addresseService addresseService){
        this.addresseService=addresseService;
    }

    
}