package com.example.projetj2E.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedecinToSearch {

    private String nom;
    private String ville;
    private String specialite;

}
