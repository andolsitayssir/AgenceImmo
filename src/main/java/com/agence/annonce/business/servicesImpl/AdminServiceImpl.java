package com.agence.annonce.business.servicesImpl;



import org.springframework.stereotype.Service;

import com.agence.annonce.business.sevices.AdminService;
import com.agence.annonce.dao.entities.Admin;
import com.agence.annonce.dao.repositories.AdminRepository;


@Service
public class AdminServiceImpl implements AdminService {
   private final AdminRepository AdminRepository; 
   public AdminServiceImpl (AdminRepository AdminRepository) {
       this.AdminRepository = AdminRepository;
   }
@Override
public Admin getAdminById(Long id) {
    if(id==null) {
        return null;
    }
    return this.AdminRepository.findById(id).get();


}
}
