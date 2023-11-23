package com.example.projetj2E.services;

import com.example.projetj2E.entites.*;
import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.hassing.HassingAndMatchingTester;
import com.example.projetj2E.models.MedecinToSearch;
import com.example.projetj2E.models.PatientModel;
import com.example.projetj2E.models.RdvModel;
import com.example.projetj2E.models.User;
import com.example.projetj2E.repository.MedecinRepository;
import com.example.projetj2E.repository.PatientRepository;
import com.example.projetj2E.repository.RendezVousRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Service
public class PatientServiceImpl implements PatientServices{
    private final PatientRepository patientRepository;
    private final AuthentificationService authentificationService;
    private final RendezVousRepository rendezVousRepository;
    private final VerifierAuthentificationImpl verifierAuthentification;
    private final SpecialiteService specialiteService;

    private final MedecinRepository medecinRepository;

    public PatientServiceImpl(PatientRepository patientRepository, AuthentificationService authentificationService, RendezVousRepository rendezVousRepository, SpecialiteService specialiteService, MedecinRepository medecinRepository, VerifierAuthentificationImpl verifierAuthentification) {
        this.patientRepository = patientRepository;
        this.authentificationService = authentificationService;
        this.rendezVousRepository = rendezVousRepository;
        this.specialiteService = specialiteService;
        this.medecinRepository = medecinRepository;
        this.verifierAuthentification = verifierAuthentification;
    }

    @Override
    public ResponseEntity<String> savePatient(PatientModel patientModel) throws GereExistEmailException {
        if(patientRepository.findBypatientLogin(patientModel.getPatientLogin()).isPresent()){
            throw  new GereExistEmailException("email déja utilisé");
        }
        Patient patient=new Patient();
        patient.setNom(patientModel.getNom());
        patient.setPrenom(patientModel.getPrenom());
        patient.setDateDeNaissance(patientModel.getDateDeNaissance());
        patient.setSexe(patientModel.getSexe());
        patient.setPassword(HassingAndMatchingTester.passwordtohash(patientModel.getPassword()));
        patient.setTelephone(patientModel.getTelephone());
        patient.setPatientLogin(patientModel.getPatientLogin());
        patient.setAutorisation(Autorisation.Autoriser);
        String sessionId=authentificationService.creerSessionIdPourPatient(patient.getPatientLogin());
        patient.setSessionId(sessionId);
        try{
            patientRepository.save(patient);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("ERROR");
        }
        return ResponseEntity.ok(sessionId);
    }

    @Override
    public ResponseEntity<String> authentifierUser(User user) throws HandleIncorrectAuthentification, UserNotFoundException {
        Optional<Patient> optionalPatient = patientRepository.findBypatientLogin(user.getLogin());
        if(optionalPatient.isPresent()){
            Patient patient = optionalPatient.get();
            String password=patient.getPassword();
            if(!HassingAndMatchingTester.passwordMatching(password, user.getPassword()) || patient.getAutorisation().equals(Autorisation.NonAutoriser))
            {
                throw new HandleIncorrectAuthentification("données invalide! ou  votre compte a été supprimer");
            }
            String sessionId = authentificationService.creerSessionIdPourPatient(optionalPatient.get().getPatientLogin());
            patient.setSessionId(sessionId);
            try {
                patientRepository.save(patient);
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
     public ResponseEntity<Object> chercherMedecin(String sessionid, MedecinToSearch medecinToSearch) throws UserNotFoundException, HandleIncorrectAuthentification {
        // authentifier patient
        if(!verifierAuthentification.verifyAuthentificationPatient(sessionid)){
            throw new HandleIncorrectAuthentification("Non Authentifié");
        }
        Specialite medspecialite = Specialite.builder()
                .nomDuSpecialite(medecinToSearch.getSpecialite())
                .build();
        Ville ville = new Ville(medecinToSearch.getVille());
        if(!(medecinToSearch.getNom()==null)){
            List<Medecin> medecins = medecinRepository.findAllByVilleAndSpecialitesAndNom(ville, medspecialite
                    ,medecinToSearch.getNom());
            if (medecins.isEmpty()) {
                throw new UserNotFoundException("Medecin non trouvé");
            }
            List<Map<String, Object>> medecinstrouves = new ArrayList<>();
            for (Medecin medecin : medecins) {
                Map<String, Object> medecin_json = new HashMap<>();
                medecin_json.put("nom", medecin.getNom());
                medecin_json.put("prenom", medecin.getPrenom());
                medecin_json.put("ville",medecin.getVille());
                medecin_json.put("sexe",medecin.getSexe());
                medecin_json.put("address_cabinet",medecin.getAdressCabinet());
                medecin_json.put("specialite",specialiteService.getMedecinSpecialites(medecin.getSpecialites()));
                medecin_json.put("id",medecin.getMedecinId())  ;
                medecinstrouves.add(medecin_json);
            }
            return ResponseEntity.status(HttpStatus.OK).body(medecinstrouves);
        }

        List<Medecin> medecins = medecinRepository.findAllByVilleAndSpecialites(ville, medspecialite);

        if (medecins.isEmpty()) {
            throw new UserNotFoundException("Medecin non trouvé");
        }
        List<Map<String, Object>> medecinstrouves = new ArrayList<>();
        for (Medecin medecin : medecins) {
            Map<String, Object> medecin_json = new HashMap<>();
            medecin_json.put("nom", medecin.getNom());
            medecin_json.put("prenom", medecin.getPrenom());
            medecin_json.put("ville",medecin.getVille());
            medecin_json.put("sexe",medecin.getSexe());
            medecin_json.put("address_cabinet",medecin.getAdressCabinet());
            medecin_json.put("specialite",specialiteService.getMedecinSpecialites(medecin.getSpecialites()));
            medecin_json.put("id",medecin.getMedecinId())  ;
            medecinstrouves.add(medecin_json);
        }
        return ResponseEntity.status(HttpStatus.OK).body(medecinstrouves);

    }

    @Override
    public ResponseEntity<Object> disponibilites(String sessionid, MedecinToSearch medecin) {
        return null;
    }

    @Override
    public ResponseEntity<String> choisirUnRdv(String sessionId, RdvModel rdvModel) throws UserNotFoundException, HandleIncorrectAuthentification {
        if(!verifierAuthentification.verifyAuthentificationPatient(sessionId)){
            throw new HandleIncorrectAuthentification("Non Authentifié");
        }
        Optional<Medecin> medecin=medecinRepository.findById(rdvModel.getMedecinId());
        if(medecin.isEmpty()){
            throw  new UserNotFoundException("medecin n'existe pas");
        }
        Patient patient=authentificationService.toPatient(sessionId);
        RendezVous rendezVous=new RendezVous();
        rendezVous.setHeureRdv(LocalTime.of(Integer.parseInt(rdvModel.getHeureRdv()),0));
        rendezVous.setDateRdv(rdvModel.getDateRdv());
        rendezVous.setStatusDemandeRdv(StatusDemandeRdv.Attente);
        rendezVous.setStatusRdv(StatusRdv.Attente);
        rendezVousRepository.save(rendezVous);
        rendezVous.setMedecin(medecin.get());
        rendezVous.setPatient(patient);
        rendezVousRepository.save(rendezVous);
        return ResponseEntity.status(200).body("saved");
    }



}

