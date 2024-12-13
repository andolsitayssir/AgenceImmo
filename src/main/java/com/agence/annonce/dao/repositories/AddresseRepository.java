package com.agence.annonce.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agence.annonce.dao.entities.Address;

public interface AddresseRepository extends JpaRepository<Address, Long> {
    
}