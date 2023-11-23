package com.example.projetj2E.controllers;

import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.MedecinToSearch;
import com.example.projetj2E.models.PatientModel;
import com.example.projetj2E.models.RdvModel;
import com.example.projetj2E.models.User;
import com.example.projetj2E.services.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin
@RequestMapping("api/v1/patient")
public class PatientController {

        @Autowired
        private PatientServices patientServices;

        @PostMapping(value = "/signup" , produces = MediaType.APPLICATION_JSON_VALUE)
        @CrossOrigin
        public ResponseEntity<String> savePatient(@RequestBody PatientModel patientModel) throws GereExistEmailException {
                return patientServices.savePatient(patientModel);
        }

        @PostMapping("/signin")
        @CrossOrigin
        public ResponseEntity<String> authentifierUser(@RequestBody User patient) throws HandleIncorrectAuthentification, UserNotFoundException {
                return patientServices.authentifierUser(patient);

        }

        @PostMapping("dashboard/recherche")
        @CrossOrigin
        public ResponseEntity<Object> chercherMedecin(@RequestHeader("token") String sessionid, @RequestBody MedecinToSearch medecinToSearch) throws UserNotFoundException, HandleIncorrectAuthentification {
                return   patientServices.chercherMedecin(sessionid,medecinToSearch);

        }

        @GetMapping("dashboard/recherche")
        public ResponseEntity<Object> disponibilites(@RequestHeader("token") String sessionid,@RequestBody MedecinToSearch medecin ){
                     return  patientServices.disponibilites(sessionid,medecin);
        }

        @PutMapping("dashboard/recherche")
        @CrossOrigin
        public ResponseEntity<String> choisirUnRdv(@RequestHeader("token") String sessionid, @RequestBody RdvModel rdvModel) throws UserNotFoundException, HandleIncorrectAuthentification {
                return   patientServices.choisirUnRdv(sessionid,rdvModel);

        }


}
