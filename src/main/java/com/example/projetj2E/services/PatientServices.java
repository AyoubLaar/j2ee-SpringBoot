package com.example.projetj2E.services;

import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.erreur.*;
import com.example.projetj2E.models.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PatientServices {

    void save(Patient patient);

    Optional<Patient> findById(Long id);
    ResponseEntity<String> savePatient(PatientModel patientModel) throws GereExistEmailException;
    ResponseEntity<String> authentifierUser(User patient) throws HandleIncorrectAuthentification, UserNotFoundException;

     ResponseEntity<Object> disponibilites(String sessionid, Long medecinId) throws UserNotFoundException, HandleIncorrectAuthentification;

    ResponseEntity<String> choisirUnRdv(String sessionId, RdvModel rdvModel) throws UserNotFoundException, HandleIncorrectAuthentification, RendezVousExisteDeja;

    ResponseEntity<Object> chercherMedecin(String sessionid, MedecinToSearch medecinToSearch) throws UserNotFoundException, HandleIncorrectAuthentification;

    List<Patient> trouverParPrenomEtNom(String prenom,String nom);


    ResponseEntity<Object> mesRdv(String sessionid) throws UserNotFoundException, HandleIncorrectAuthentification;

    ResponseEntity<Object> mesDemandes(String sessionid) throws UserNotFoundException, HandleIncorrectAuthentification;
}
