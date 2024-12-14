package com.agence.annonce.web.models;

import java.util.List;
import java.util.Locale.Category;

import org.springframework.web.multipart.MultipartFile;

import com.agence.annonce.dao.entities.Type;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class annonceForm {
@NotBlank()
private String title;
@NotBlank()
private Type type;
@NotBlank()
private Category category;
@NotBlank()
private String description;
@NotNull
@Size(min=8,max=8,message="phone number must be 8 digits")
private Integer tel;
@NotNull
private Double surface;
@NotNull
@Min(value=10)
private Double price;
@NotNull
private String governorate;
@NotNull
private String city;
@NotNull
private String street;
private List<MultipartFile> images;
    
}
