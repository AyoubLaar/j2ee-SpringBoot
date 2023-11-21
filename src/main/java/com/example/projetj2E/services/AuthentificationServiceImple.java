package com.example.projetj2E.services;

import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.hassing.HassingAndMatchingTester;
import com.example.projetj2E.repository.MedecinRepository;
import com.example.projetj2E.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthentificationServiceImple implements AuthentificationService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Override
    public String creerSessionIdPourPatient(String email){
        String message = "patient:"+email+":"+LocalDate.now().toString();
        String sessionId = HassingAndMatchingTester.encrypt(message);
        return sessionId;
    }

    @Override
    public String creerSessionIdPourMedecin(String email) {
        String message = "medecin:"+email+":"+ LocalDateTime.now().toString();
        String sessionId = HassingAndMatchingTester.encrypt(message);
        return sessionId;
    }

    @Override
    public Patient toPatient(String jeton) throws UserNotFoundException {
        String message = HassingAndMatchingTester.decrypt(jeton);
        String [] split_message = message.split(":");
        if(split_message[1] != "patient")throw new UserNotFoundException("pas un patient!");
        Optional<Patient> optional_patient = patientRepository.findBypatientLogin(split_message[1]);
        if(optional_patient.isEmpty())throw new UserNotFoundException("email inexistant!");
        return optional_patient.get();
    }

    @Override
    public Medecin toMedecin(String jeton) throws UserNotFoundException {
        String message = HassingAndMatchingTester.decrypt(jeton);
        String [] split_message = message.split(":");
        if(split_message[1] != "medecin")throw new UserNotFoundException("pas un medecin!");
        Optional<Medecin> optional_patient = medecinRepository.findByMedLogin(split_message[1]);
        if(optional_patient.isEmpty())throw new UserNotFoundException("email inexistant!");
        return optional_patient.get();
    }
}
