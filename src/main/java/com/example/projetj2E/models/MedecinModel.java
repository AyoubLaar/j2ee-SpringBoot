package com.example.projetj2E.models;


import com.example.projetj2E.entites.Sexe;
import com.example.projetj2E.entites.Ville;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedecinModel {

    private String medLogin;
    private String password;
    private String nom;
    private String prenom;
    private LocalDate dateDeNaissance;
    private String codeOrdreMedecin;
    private Ville ville;
    private String adressCabinet;
    private Sexe sexe;
    private List<String> specialites;
}
