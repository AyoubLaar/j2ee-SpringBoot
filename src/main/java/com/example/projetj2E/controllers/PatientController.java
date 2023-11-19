package com.example.projetj2E.controllers;

import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.models.PatientModel;
import com.example.projetj2E.services.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class PatientController {

        @Autowired
        private PatientServices patientServices;

        @PostMapping("/patients")
        public String savePatient(@RequestBody PatientModel patientModel){
                Patient patient=patientServices.savePatient(patientModel);

                return "success";
        }

}
