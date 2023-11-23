package com.example.projetj2E.services;

import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;

public interface VerifierAuthentification {

    boolean verifyAuthentificationPatient(String sessionid) throws UserNotFoundException,  HandleIncorrectAuthentification;

    boolean verifyAuthentificationMedecin(String sessionid) throws UserNotFoundException,  HandleIncorrectAuthentification;


    boolean verifyAuthentificationAdmin(String sessionid) throws UserNotFoundException,  HandleIncorrectAuthentification;
}
