package com.agence.annonce.models;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
  @Id
    private Long idAdmin;
    private String name;
    private String email;
    private String password;  
}