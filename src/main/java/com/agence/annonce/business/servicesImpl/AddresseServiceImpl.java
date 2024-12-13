package com.agence.annonce.business.servicesImpl;

import java.util.List;

import com.agence.annonce.business.services.addresseService;
import com.agence.annonce.dao.entities.Address;
import com.agence.annonce.dao.repositories.AddresseRepository;

public class AddresseServiceImpl implements addresseService {

    private final AddresseRepository addresseRepository;
    public AddresseServiceImpl(AddresseRepository addresseRepository){
       this.addresseRepository=addresseRepository;
    }
    

    @Override
    public List<Address> getAllAddress() {
        return this.addresseRepository.findAll();
    }

    @Override
    public Address getAddressbyId(Long id) {
        return this.addresseRepository.findById(id).get();
    }
}