package com.example.projetj2E.services;

import com.example.projetj2E.entites.RendezVous;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface AdminService {
    ResponseEntity<String> authentifierUser(User user) throws HandleIncorrectAuthentification, UserNotFoundException;

    ResponseEntity<Object> chercherMedecin(@RequestHeader("token") String sessionid, MedecinToSearch medecin) throws UserNotFoundException, HandleIncorrectAuthentification;

    ResponseEntity<String> bloquerMedecin(String sessionid, MedecinId medecinId) throws UserNotFoundException, HandleIncorrectAuthentification;

    ResponseEntity<String> saveAdmin(User user);

    ResponseEntity<String> accepterOrRejectMedecin(String sessionid, AcceptOrReject medecin) throws UserNotFoundException, HandleIncorrectAuthentification;

    List<Map<String,Object>> medecinDemand(String sessionid) throws HandleIncorrectAuthentification, UserNotFoundException;

    ResponseEntity<Object> chercherPatient(String sessionid, PatientToSearch patient) throws UserNotFoundException, HandleIncorrectAuthentification;

    ResponseEntity<Object> bloquerPatient(String sessionid, PatientId patientId) throws HandleIncorrectAuthentification, UserNotFoundException;

    List<LocalTime> RdvIndisponibles(RendezVous rdv);
}
