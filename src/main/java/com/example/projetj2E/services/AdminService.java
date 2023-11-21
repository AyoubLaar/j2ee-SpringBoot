package com.example.projetj2E.services;

import com.example.projetj2E.erreur.GereMedecinNotFound;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.MedecinToDelete;
import com.example.projetj2E.models.MedecinToSearch;
import com.example.projetj2E.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AdminService {
    ResponseEntity<String> authentifierUser(User user) throws HandleIncorrectAuthentification, UserNotFoundException;

    void supprimerMedecin(MedecinToSearch medecinToSearch) ;


    ResponseEntity<Object> chercherMedecin(MedecinToDelete medecinToDelete) throws GereMedecinNotFound;
}
