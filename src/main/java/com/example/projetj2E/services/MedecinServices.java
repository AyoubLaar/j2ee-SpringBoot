package com.example.projetj2E.services;

import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.GereMedecinNotFound;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.MedecinModel;
import com.example.projetj2E.models.MedecinToSearch;
import com.example.projetj2E.models.RdvModel;
import com.example.projetj2E.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;

public interface MedecinServices {
    Medecin registerMedecin(MedecinModel medecinModel) throws GereExistEmailException;


    ResponseEntity<String> authentifierUser(User medecin) throws HandleIncorrectAuthentification, UserNotFoundException;

    ResponseEntity<Object> rechercherMedecin(MedecinToSearch medecinToSearch) throws GereMedecinNotFound;

    ResponseEntity<Object> mesDemandeDeRdv(@RequestHeader("token") String sessionid) throws UserNotFoundException;
}
