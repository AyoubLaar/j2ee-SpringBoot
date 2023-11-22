package com.example.projetj2E.services;

import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.MedecinToSearch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

public interface VisitorService {

    ResponseEntity<Object> rechercherMedecinvisitor (MedecinToSearch medecinToSearch) throws UserNotFoundException;
}
