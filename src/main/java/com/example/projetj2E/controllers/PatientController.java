package com.example.projetj2E.controllers;

import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.PatientModel;
import com.example.projetj2E.models.RdvModel;
import com.example.projetj2E.models.User;
import com.example.projetj2E.services.PatientServiceImpl;
import com.example.projetj2E.services.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/patient")
public class PatientController {

        @Autowired
        private PatientServiceImpl patientServices;

        @PostMapping(value = "/signup")
        @CrossOrigin
        public ResponseEntity<String> savePatient(@RequestBody PatientModel patientModel) {
                try {
                        return patientServices.savePatient(patientModel);
                } catch (GereExistEmailException e) {
                                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
                }
        }

        @PostMapping("/signin")
        @CrossOrigin
        public ResponseEntity<String> authentifierUser(@RequestBody User patient) throws HandleIncorrectAuthentification {
                try {
                        return patientServices.authentifierUser(patient);
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
                }
        }

        @PostMapping("/rendezvous")
        @CrossOrigin
        public ResponseEntity<String> prendreRendezvous(@RequestBody RdvModel rdvModel){
                return patientServices.prendreRendezvous(rdvModel);
        }


}
