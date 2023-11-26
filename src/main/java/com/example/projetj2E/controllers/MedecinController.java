package com.example.projetj2E.controllers;


import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.RendezVousNotFound;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.MedecinModel;

import com.example.projetj2E.models.RdvToAcceptOrReject;
import com.example.projetj2E.models.User;
import com.example.projetj2E.services.MedecinServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/medecin")
@CrossOrigin
public class MedecinController {

    @Autowired
    private MedecinServices medecinServices;

    @PostMapping("/signup")
    @CrossOrigin
    public ResponseEntity<String> registerMedecin(@RequestBody MedecinModel medecinModel)
            throws GereExistEmailException {
        return  medecinServices.registerMedecin(medecinModel);

    }
    @PostMapping("/signin")
    @CrossOrigin
    public ResponseEntity<String> authentifierUser(@RequestBody User medecin)
            throws HandleIncorrectAuthentification, UserNotFoundException {
        return medecinServices.authentifierUser(medecin);
    }
    @GetMapping ("/dashboard/mesdemandes")
    @CrossOrigin
    public ResponseEntity<Object>  mesDemandeDeRdv(@RequestHeader(value = "token") String sessionid)
            throws UserNotFoundException, HandleIncorrectAuthentification {
        return medecinServices.mesDemandeDeRdv(sessionid);
    }
    @GetMapping ("/dashboard/mesRdv")
    @CrossOrigin
    public ResponseEntity<Object>  mesRdv(@RequestHeader(value = "token") String sessionid)
            throws UserNotFoundException, HandleIncorrectAuthentification {
        return medecinServices.mesRdv(sessionid);
    }
    @PutMapping("/dashbaord/mesRdv")
    public ResponseEntity<String> acceptOrRejectDemand(@RequestHeader(value = "token") String sessionId,
            @RequestBody RdvToAcceptOrReject rdv) throws UserNotFoundException,
            HandleIncorrectAuthentification, RendezVousNotFound
    {
                return  medecinServices.acceptOrRejectDemand(rdv,sessionId);
    }

    @PutMapping("/dashbaord/mesRdv/annuler")
    public ResponseEntity<String> annulerRdv(@RequestHeader(value = "token") String sessionId,
                                                       @RequestParam Long rdvId) throws UserNotFoundException,
            HandleIncorrectAuthentification, RendezVousNotFound
    {
        return  medecinServices.annulerRdv(rdvId,sessionId);
    }



}
