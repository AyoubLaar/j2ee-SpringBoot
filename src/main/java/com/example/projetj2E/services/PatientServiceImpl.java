package com.example.projetj2E.services;

import com.example.projetj2E.entites.Etatrdv;
import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.entites.RendezVous;
import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.GereMedecinNotFound;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.hassing.HassingAndMatchingTester;
import com.example.projetj2E.models.PatientModel;
import com.example.projetj2E.models.RdvModel;
import com.example.projetj2E.models.User;
import com.example.projetj2E.repository.MedecinRepository;
import com.example.projetj2E.repository.PatientRepository;
import com.example.projetj2E.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientServices{
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AuthentificationService authentificationService;
    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private MedecinRepository medecinRepository;
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
        String sessionId = authentificationService.creerSessionIdPourPatient(patientModel.getPatientLogin());
        patient.setSessionId(sessionId);
        patientRepository.save(patient);
        return ResponseEntity.ok(sessionId);
    }

    @Override
    public ResponseEntity<String> authentifierUser(User user) throws HandleIncorrectAuthentification, UserNotFoundException {
        Optional<Patient> optionalPatient = patientRepository.findBypatientLogin(user.getLogin());
        if(optionalPatient.isPresent()){
            Patient patient = optionalPatient.get();
            String password=patient.getPassword();
            if(!HassingAndMatchingTester.passwordMatching(password, user.getPassword()))
            {
                throw new HandleIncorrectAuthentification("données invalide!");
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
    public ResponseEntity<String> choisirUnRdv(String sessionId, RdvModel rdvModel) throws GereMedecinNotFound, UserNotFoundException {
        Optional<Medecin> medecin=medecinRepository.findById(rdvModel.getMedecinId());
        if(medecin.isEmpty()){
            throw  new GereMedecinNotFound("medecin n'est pas trouver");
        }
        Patient patient=authentificationService.toPatient(sessionId);
        RendezVous rendezVous=new RendezVous();
        rendezVous.setHeureRdv(LocalTime.of(Integer.parseInt(rdvModel.getHeureRdv()),0));
        rendezVous.setDateRdv(rdvModel.getDateRdv());
        rendezVous.setStatusRdv(Etatrdv.Attente);
        rendezVousRepository.save(rendezVous);
        rendezVous.setMedecin(medecin.get());
        rendezVous.setPatient(patient);
        rendezVousRepository.save(rendezVous);
        return ResponseEntity.status(200).body("saved");
    }

}

