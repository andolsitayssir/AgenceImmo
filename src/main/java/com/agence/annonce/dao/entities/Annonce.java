package com.agence.annonce.dao.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "annonces")
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long annonce_id;
    
    @Column(name = "titre" )//nullable = false//
    private String titre;
    @Column(name = "description")
     private String description; 
   
    @Column(name = "surface")
    private Double surface;
    @Column(name = "price" )
    private Double price;
   
    @Column(name = "type" )
    @Enumerated(EnumType.STRING)
    private Type type;
    
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;
   
   
   @OneToOne
   @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;
   
    @Column(name = "tel" )
    private Integer tel;

    @OneToMany(mappedBy ="annonce", cascade = CascadeType.ALL)
    private List <Photo>  photos= new ArrayList<Photo>();



    public Annonce(Long annonce_id, String titre, String description, Double surface, Double price, Type type,
            Category category, Address address, Integer tel) {
        this.annonce_id = annonce_id;
        this.titre = titre;
        this.description = description;
        this.surface = surface;
        this.price = price;
        this.type = type;
        this.category = category;
        this.address = address;
        this.tel = tel;
    }



    public Annonce(Long id, String titre, String description, Double surface, Double price, Type type, Category category, Integer tel) {
        this.annonce_id = id;
        this.titre = titre;
        this.description = description;
        this.surface = surface;
        this.price = price;
        this.type = type;
        this.category = category;
        this.tel = tel;
    }
    



    
}