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
  @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "surface" ,nullable = false)
    private Double surface;
    @Column(name = "price" ,nullable = false)
    private Double price;
   
    @Column(name = "type" ,nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;
    
    @Column(name = "category" ,nullable = false)
   @Enumerated(EnumType.STRING)
    private Category category;
   
   
   @OneToOne
   @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL)
    private List <Photo>  photos= new ArrayList<Photo>();
   
    private Integer tel;

    public Annonce(Long id, String description, Double surface, Double price, Type type, Category category, Address address, Integer tel) {
        this.id = id;
        this.description = description;
        this.surface = surface;
        this.price = price;
        this.type = type;
        this.category = category;
        this.address = address;
        this.tel = tel;
    }
}