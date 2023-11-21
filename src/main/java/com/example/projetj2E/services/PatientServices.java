package com.example.projetj2E.services;

import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.PatientModel;
import com.example.projetj2E.models.User;
import org.springframework.http.ResponseEntity;

public interface PatientServices {

    ResponseEntity<String> savePatient(PatientModel patientModel) throws GereExistEmailException;
    ResponseEntity<String> authentifierUser(User patient) throws HandleIncorrectAuthentification, UserNotFoundException;
}
