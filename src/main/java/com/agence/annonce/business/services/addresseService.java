package com.agence.annonce.business.services;

import java.util.List;

import com.agence.annonce.dao.entities.Address;

public interface addresseService {
    List<Address> getAllAddress();
    Address getAddressbyId(Long id);
}