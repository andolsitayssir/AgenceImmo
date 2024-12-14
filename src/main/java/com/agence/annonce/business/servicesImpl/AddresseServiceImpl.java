package com.agence.annonce.business.servicesImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agence.annonce.business.services.AddresseService;
import com.agence.annonce.dao.entities.Address;
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
    public Address getAddressbyId(Long id) {
        return this.addresseRepository.findById(id).get();
    }


    @Override
    public Address addAddress(Address address) {
        return this.addresseRepository.save(address);
    }
}