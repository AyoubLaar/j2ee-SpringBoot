package com.example.projetj2E.controllers;



import com.example.projetj2E.erreur.GereMedecinNotFound;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.MedecinToSearch;
import com.example.projetj2E.services.MedecinServices;

import com.example.projetj2E.services.VisitorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1")
@CrossOrigin
public class VisitorController {

       @Autowired
       private VisitorServiceImpl visitorService;

    @PostMapping("/recherche")
    @CrossOrigin
    public ResponseEntity<Object> chercherMedecin( @RequestBody MedecinToSearch medecinToSearch) throws  UserNotFoundException {
        return  visitorService.rechercherMedecinvisitor(medecinToSearch);

    }

}
