package com.example.projetj2E.services;


import com.example.projetj2E.entites.*;
import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.hassing.HassingAndMatchingTester;
import com.example.projetj2E.models.MedecinModel;
import com.example.projetj2E.models.MedecinToSearch;
import com.example.projetj2E.models.User;
import com.example.projetj2E.repository.MedecinRepository;

import com.example.projetj2E.repository.SpecialiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.*;

@Service
public class MedecinServicesImpl implements MedecinServices {
    private final MedecinRepository medecinRepository;

    private final SpecialiteService specialiteService;

    private MedecinServices medecinServices;

    @Autowired
    private AuthentificationService authentificationService;

    private final SpecialiteRepository specialiteRepository;

    public MedecinServicesImpl(MedecinRepository medecinRepository, SpecialiteService specialiteService, SpecialiteRepository specialiteRepository) {
        this.medecinRepository = medecinRepository;
        this.specialiteService = specialiteService;
        this.specialiteRepository = specialiteRepository;
    }

    @Override
    public ResponseEntity<String> registerMedecin(MedecinModel medecinModel) throws GereExistEmailException {
        Medecin medecin = new Medecin();
        medecin.setAdressCabinet(medecinModel.getAdressCabinet());
        medecin.setCodeOrdreMedecin(medecinModel.getCodeOrdreMedecin());
        medecin.setNom(medecinModel.getNom());
        medecin.setSexe(medecinModel.getSexe());
        medecin.setPassword(HassingAndMatchingTester.passwordtohash(medecinModel.getPassword()));
        medecin.setPrenom(medecinModel.getPrenom());
        medecin.setVille(medecinModel.getVille());
        medecin.setDateDeNaissance(medecinModel.getDateDeNaissance());
        medecin.setStatusDemande(StatusMedecin.Attente);
        if ((medecinRepository.findByMedLogin(medecinModel.getMedLogin()).isPresent())) {
            throw new GereExistEmailException("il y a déja un utilisateur avec cet email");
        }
        medecin.setMedLogin(medecinModel.getMedLogin());
        medecin.setAutorisation(Autorisation.NonAutoriser);
        List<Specialite> specialites = specialiteService.returnSpecialites(medecinModel.getSpecialites());
        specialiteRepository.saveAll(specialites);// a éliminer seulement pour tester
        medecin.setSpecialites(specialites);
        medecinRepository.save(medecin);
        return ResponseEntity.status(HttpStatus.OK).body("Succes");

    }

    @Override
    public boolean verifyAuthentification(String sessionid) throws UserNotFoundException, HandleIncorrectAuthentification {
        Medecin medecin= authentificationService.toMedecin(sessionid);
        User user= User.builder()
                .login(medecin.getMedLogin())
                .password(medecin.getPassword())
                .build();
        ResponseEntity<String> reponse  =medecinServices.authentifierUser(user);
        return !Objects.equals(reponse.getBody(), sessionid);
    }

    @Override
    public ResponseEntity<String> authentifierUser(User medecin) throws HandleIncorrectAuthentification, UserNotFoundException {
        Optional<Medecin> optionalMedecin = medecinRepository.findByMedLogin(medecin.getLogin());
        if (optionalMedecin.isPresent()) {
            Medecin medecinfound=optionalMedecin.get();
            String userPassword = medecinfound.getPassword();
            if (!HassingAndMatchingTester.passwordMatching(userPassword, medecin.getPassword()) || medecinfound.getAutorisation().equals(Autorisation.NonAutoriser) ) {
                throw new HandleIncorrectAuthentification("Saisir des données correct ou votre compte a été  bloqué");
            }
            String sessionId = authentificationService.creerSessionIdPourMedecin(optionalMedecin.get().getMedLogin());
            medecinfound.setSessionId(sessionId);
            try {
                medecinRepository.save(medecinfound);
            }catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseEntity.badRequest().body("ERROR");
            }
            return ResponseEntity.ok(sessionId);
        }else{
            throw new UserNotFoundException("email inexistant!");
        }

    }


    @Override
    public ResponseEntity<Object> mesDemandeDeRdv(@RequestHeader("token") String sessionid) throws UserNotFoundException, HandleIncorrectAuthentification {
        if(medecinServices.verifyAuthentification(sessionid)){
            throw  new HandleIncorrectAuthentification("authentification incorrect");
        }
        Medecin medecin= authentificationService.toMedecin(sessionid);
        List<RendezVous> tousmesrdv=medecin.getMesrendezvous();
        List<Map<String, Object>> medemandestrouves= new ArrayList<>();
        for (RendezVous demande : tousmesrdv) {
                Map<String, Object> Rendezvous_json = new HashMap<>();
                Rendezvous_json.put("rdvId",demande.getRdvId());
                Rendezvous_json.put("statusRdv",demande.getStatusRdv());
                Patient patient =demande.getPatient();
                Rendezvous_json.put("nom",patient.getNom());
                Rendezvous_json.put("prenom",patient.getPrenom());
                Rendezvous_json.put("telephone",patient.getTelephone());
                Rendezvous_json.put("email",patient.getPatientLogin());
                Rendezvous_json.put("dateRdv",demande.getDateRdv());
                Rendezvous_json.put("heureRdv",demande.getHeureRdv());
                medemandestrouves.add(Rendezvous_json);

        }
        return ResponseEntity.status(HttpStatus.OK).body(medemandestrouves);
    }
}