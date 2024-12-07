package com.agence.annonce.web.models;

import java.util.Locale.Category;

import com.agence.annonce.dao.entities.Type;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class annonceForm {
@NotBlank(message = "titre est obligatoir")
private String titre;
@NotBlank(message="veuillez choisir un type")
private Type type;
@NotBlank(message="veuillez choisir une")
private Category category;
@NotBlank
private String description;
@NotNull
private Double surface;
@NotNull
@Min(value=10)
private Double price;
@NotBlank
private String Governorate;
@NotBlank
private String city;
@NotBlank
private String street;



    
}
