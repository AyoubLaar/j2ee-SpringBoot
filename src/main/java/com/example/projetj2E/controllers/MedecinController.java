package com.example.projetj2E.controllers;


import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.models.MedecinModel;
import com.example.projetj2E.services.MedecinServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/medecin")
public class MedecinController {


    @Autowired
    private MedecinServices medecinServices;
    @PostMapping("/signup")
    public String registerMedecin(@RequestBody MedecinModel medecinModel){
        Medecin medecin=medecinServices.registerMedecin(medecinModel);
        return "succes";
    }
}
