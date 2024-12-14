package com.agence.annonce.web.models;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.agence.annonce.dao.entities.Category;
import com.agence.annonce.dao.entities.Type;

import jakarta.validation.constraints.Digits;
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

    @NotBlank(message="Title is required")
    private String titre;

    @NotNull(message="Please choose a type")
    private Type type;

    @NotNull(message="Please choose a category")
    private Category category;

    @NotBlank(message="Description is required")
    private String description;

    @NotNull
   @Digits(integer = 8, fraction = 0, message = "Phone number must be exactly 8 digits")
    private Integer tel;

    @NotNull
    private Double surface;

    @NotNull
    @Min(value=10, message="Price must be at least 10")
    private Double price;

    @NotBlank(message="Governorate is required")
    private String governorate;

    @NotBlank(message="City is required")
    private String city;

    @NotBlank(message="Street is required")
    private String street;

    private List<MultipartFile> photos;
}