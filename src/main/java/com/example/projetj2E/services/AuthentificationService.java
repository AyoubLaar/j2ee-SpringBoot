package com.example.projetj2E.services;

import com.example.projetj2E.entites.Admin;
import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.erreur.UserNotFoundException;

public interface AuthentificationService {

    public  String creerSessionIdPourPatient(String email);
    public String creerSessionIdPourMedecin(String email);
    public String creerSessionIdPourAdmin(String email);
    public Patient toPatient(String jeton) throws UserNotFoundException;
    public Medecin toMedecin(String jeton) throws UserNotFoundException;
    public Admin toAdmin(String sessionId)throws UserNotFoundException ;
    public String getRole(String jeton) ;
}
