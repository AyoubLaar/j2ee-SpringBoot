package com.example.projetj2E.controllers;

import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.models.PatientModel;
import com.example.projetj2E.services.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/patient")
public class PatientController {

        @Autowired
        private PatientServices patientServices;

        @PostMapping("/signup")
        public String savePatient(@RequestBody PatientModel patientModel) throws GereExistEmailException {
                Patient patient=patientServices.savePatient(patientModel);
                return "success";
        }


}
