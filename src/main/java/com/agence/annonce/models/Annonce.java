package com.agence.annonce.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;



import java.util.ArrayList;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Annonce {

  
    private Long id;

    private String description;
    private Double surface;
    private Double price;

    private Category category;

    private Address address;
    private List <Photo>  photos= new ArrayList<Photo>();
    private Integer tel;
    
}