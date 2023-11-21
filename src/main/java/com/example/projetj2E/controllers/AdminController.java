package com.example.projetj2E.controllers;

import com.example.projetj2E.erreur.GereMedecinNotFound;
import com.example.projetj2E.models.MedecinToDelete;
import com.example.projetj2E.services.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminServiceImpl adminService;
    @PostMapping("/cherchermedecin")
    public ResponseEntity<Object> chercherMedecin(@RequestBody MedecinToDelete medecinToDelete) throws GereMedecinNotFound {
        return adminService.chercherMedecin(medecinToDelete);
    }

   /* @PostMapping("/listeMedecins/supprimer")
    public String supprimerMedecin(@RequestBody MedecinToSearch medecinToSearch)  {

    }

    */
}
