package com.example.projetj2E.services;

import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.RendezVousNotFound;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;

public interface MedecinServices {
    ResponseEntity<String> registerMedecin(MedecinModel medecinModel) throws GereExistEmailException;

    ResponseEntity<String> authentifierUser(User medecin) throws HandleIncorrectAuthentification, UserNotFoundException;

    ResponseEntity<Object> mesDemandeDeRdv(@RequestHeader("token") String sessionid) throws UserNotFoundException, HandleIncorrectAuthentification;
    List<Medecin> findAllMedecinDemand();
    ResponseEntity<String> acceptOrRejectDemand(RdvToAcceptOrReject rdv, String sessionId) throws HandleIncorrectAuthentification, UserNotFoundException, RendezVousNotFound;

   ResponseEntity<String> accepeterMedecin(Medecin medecin);

    ResponseEntity<String> rejeterMedecin(Medecin medecin);

    List<Medecin> trouverParNomEtPrenom(String nom,String prenom);
    ResponseEntity<Object> mesRdv(String sessionid) throws HandleIncorrectAuthentification, UserNotFoundException;
    void save(Medecin medecin);

    Optional<Medecin> findById(Long id);
    Optional<Medecin> findByMedecinLogin(String login);

}
