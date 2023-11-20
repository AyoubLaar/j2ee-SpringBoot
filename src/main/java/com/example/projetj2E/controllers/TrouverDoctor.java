package com.example.projetj2E.controllers;



import com.example.projetj2E.erreur.GereMedecinNotFound;
import com.example.projetj2E.models.MedecinToSearch;
import com.example.projetj2E.services.MedecinServices;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1")
@CrossOrigin
public class TrouverDoctor {


    private final MedecinServices medecinServices;

    public TrouverDoctor(MedecinServices medecinServices) {
        this.medecinServices = medecinServices;
    }


    @PostMapping(value = "recherche" , produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<Object> rechercherMedecin(@RequestBody MedecinToSearch medecinToSearch) throws GereMedecinNotFound {

           return medecinServices.rechercherMedecin(medecinToSearch);


    }

}
