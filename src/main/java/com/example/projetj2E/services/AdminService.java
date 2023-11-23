package com.example.projetj2E.services;

import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.AcceptOrReject;
import com.example.projetj2E.models.MedecinToDelete;
import com.example.projetj2E.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

public interface AdminService {
    ResponseEntity<String> authentifierUser(User user) throws HandleIncorrectAuthentification, UserNotFoundException;

    ResponseEntity<Object> chercherMedecin(@RequestHeader("token") String sessionid, MedecinToDelete medecinToDelete) throws UserNotFoundException, HandleIncorrectAuthentification;

    ResponseEntity<String> supprimerMedecin(String sessionid, MedecinToDelete medecinToDelete) throws UserNotFoundException, HandleIncorrectAuthentification;

    ResponseEntity<String> saveAdmin(User user);

    ResponseEntity<String> accepterOrRejectMedecin(String sessionid, AcceptOrReject medecin) throws UserNotFoundException, HandleIncorrectAuthentification;

    List<Map<String,Object>> medecinDemand(String sessionid) throws HandleIncorrectAuthentification, UserNotFoundException;
}
