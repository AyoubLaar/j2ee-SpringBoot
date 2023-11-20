package com.example.projetj2E.controllers;

import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.models.PatientModel;
import com.example.projetj2E.models.User;
import com.example.projetj2E.services.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
        public ResponseEntity<Object> savePatient(@RequestBody PatientModel patientModel){
                Map<String,String> map = new HashMap<>();
                try {
                        Patient patient = patientServices.savePatient(patientModel);
                }catch(Exception exception){
                        map.put("token","error");
                        return ResponseEntity.status(400).body(map);
                }
                map.put("token","success");
                return ResponseEntity.status(200).body(map);
        }

        @PostMapping("/signin")
        @CrossOrigin
        public String authentifierUser(@RequestBody User patient){
                String sessionId=patientServices.authentifierUser(patient);
                return sessionId;
        }
}
