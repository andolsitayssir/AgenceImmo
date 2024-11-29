package com.agence.annonce.dao.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "photos")
public class Photo {
    private Long idPhoto;
    private String url;
    @ManyToOne
    private Long idAnnonce;
  
}