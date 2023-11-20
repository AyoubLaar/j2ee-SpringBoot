package com.example.projetj2E.models;


import com.example.projetj2E.entites.Sexe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientModel {

    private String nom;
    private String prenom;
    private String patientLogin;
    private String password;
    private LocalDate dateDeNaissance;
    private String telephone;
    private Sexe sexe;

}
