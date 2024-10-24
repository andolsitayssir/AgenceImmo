package com.agence.annonce.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Address {
    private Long idAddress; 
    private String city;
    private String town;
    private String street;
}