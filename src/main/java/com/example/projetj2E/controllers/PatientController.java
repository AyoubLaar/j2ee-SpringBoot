package com.example.projetj2E.controllers;

import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.GereMedecinNotFound;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.PatientModel;
import com.example.projetj2E.models.RdvModel;
import com.example.projetj2E.models.User;
import com.example.projetj2E.services.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
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
        private PatientServices patientServices;

        @PostMapping(value = "/signup" , produces = MediaType.APPLICATION_JSON_VALUE)
        @CrossOrigin
        public ResponseEntity<Object> savePatient(@RequestBody PatientModel patientModel) throws GereExistEmailException {
                Map<String,String> map = new HashMap<>();
                ResponseEntity<String> patient = patientServices.savePatient(patientModel);

                map.put("token","success");
                return ResponseEntity.status(200).body("sessionid");
        }

        @PostMapping("/signin")
        @CrossOrigin
        public ResponseEntity<String> authentifierUser(@RequestBody User patient) throws HandleIncorrectAuthentification, UserNotFoundException {
                return patientServices.authentifierUser(patient);

        }

        @PostMapping("/rendezvous")
        @CrossOrigin
        public ResponseEntity<String> choisirUnRdv(@RequestHeader("token") String sessionid, @RequestBody RdvModel rdvModel) throws GereMedecinNotFound, UserNotFoundException {
                return   patientServices.choisirUnRdv(sessionid,rdvModel);

        }


}
