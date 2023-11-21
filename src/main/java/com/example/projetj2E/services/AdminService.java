package com.example.projetj2E.services;

import com.example.projetj2E.erreur.GereMedecinNotFound;
import com.example.projetj2E.models.MedecinToDelete;
import com.example.projetj2E.models.MedecinToSearch;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AdminService {
    void supprimerMedecin(MedecinToSearch medecinToSearch) ;

    ResponseEntity<Object> chercherMedecin(MedecinToDelete medecinToDelete) throws GereMedecinNotFound;
}
