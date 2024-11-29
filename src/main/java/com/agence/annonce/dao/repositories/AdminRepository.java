package com.agence.annonce.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agence.annonce.dao.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    
}