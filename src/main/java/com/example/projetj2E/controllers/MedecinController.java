package com.example.projetj2E.controllers;


import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.MedecinModel;

import com.example.projetj2E.models.User;
import com.example.projetj2E.services.MedecinServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/medecin")
public class MedecinController {


    @Autowired
    private MedecinServices medecinServices;

    @PostMapping("/signup")
    public ResponseEntity<String> registerMedecin(@RequestBody MedecinModel medecinModel) throws GereExistEmailException {
        return  medecinServices.registerMedecin(medecinModel);

    }

    @PostMapping("/signin")
    @CrossOrigin
    public ResponseEntity<String> authentifierUser(@RequestBody User medecin) throws HandleIncorrectAuthentification, UserNotFoundException {
        return medecinServices.authentifierUser(medecin);

    }
    @GetMapping ("/dashboard/mesRdv")
    public ResponseEntity<Object>  mesDemandeDeRdv(@RequestHeader(value = "token") String sessionid)
            throws UserNotFoundException, HandleIncorrectAuthentification {
        return medecinServices.mesDemandeDeRdv(sessionid);
    }


}
