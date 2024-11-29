package com.agence.annonce.dao.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Long id;

    private String description;
    private Double surface;
    private Double price;

    @Enumerated(EnumType.STRING)
    private Type type;

   @Enumerated(EnumType.STRING)
    private Category category;

   @OneToOne
    private Address address;

    @OneToMany
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