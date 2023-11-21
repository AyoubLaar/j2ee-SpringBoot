package com.example.projetj2E.services;

import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.erreur.UserNotFoundException;

public interface AuthentificationService {

    public  String creerSessionIdPourPatient(String email);

    public String creerSessionIdPourMedecin(String email);

    Patient toPatient(String jeton) throws UserNotFoundException;

    Medecin toMedecin(String jeton) throws UserNotFoundException;
}
