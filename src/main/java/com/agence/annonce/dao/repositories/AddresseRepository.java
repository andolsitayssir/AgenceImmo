package com.agence.annonce.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agence.annonce.dao.entities.Address;


@Repository
public interface AddresseRepository extends JpaRepository<Address, Long> {
    
}