package com.agence.annonce.business.servicesImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agence.annonce.business.services.AddresseService;
import com.agence.annonce.dao.entities.Address;
import com.agence.annonce.dao.entities.Annonce;
import com.agence.annonce.dao.repositories.AddresseRepository;

@Service
public class AddresseServiceImpl implements AddresseService {

    private final AddresseRepository addresseRepository;
    public AddresseServiceImpl(AddresseRepository addresseRepository){
       this.addresseRepository=addresseRepository;
    }
    

    @Override
    public List<Address> getAllAddress() {
        return this.addresseRepository.findAll();
    }

    @Override
    public Address getAddressbyAnnonce(Annonce annonce) {
        return this.addresseRepository.findByAnnonce(annonce);
    }


    @Override
    public Address addAddress(Address address) {
        return this.addresseRepository.save(address);
    }


    @Override
    public Address updateAddress(Address address) {
        return this.addresseRepository.save(address);
    }


    @Override
    public void deleteAddressByAnnonce(Annonce annonce) {
        this.addresseRepository.deleteByAnnonce(annonce);
    }
}