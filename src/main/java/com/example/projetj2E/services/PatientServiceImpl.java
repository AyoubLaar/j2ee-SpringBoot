package com.example.projetj2E.services;

import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.hassing.HassingAndMatchingTester;
import com.example.projetj2E.models.PatientModel;
import com.example.projetj2E.models.User;
import com.example.projetj2E.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientServices{
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AuthentificationService authentificationService;
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
    public ResponseEntity<String> authentifierUser(User patient) throws HandleIncorrectAuthentification, UserNotFoundException {
        Optional<Patient> optionalPatient = patientRepository.findBypatientLogin(patient.getLogin());
        if(optionalPatient.isPresent()){
            String userPassword=optionalPatient.get().getPassword();
            if(!HassingAndMatchingTester.passwordMatching(userPassword, patient.getPassword()))
            {
                throw new HandleIncorrectAuthentification("données invalide!");
            }
            String sessionId = authentificationService.creerSessionIdPourPatient(optionalPatient.get().getPatientLogin());
            return ResponseEntity.ok(sessionId);
        }else{
            throw new UserNotFoundException("email inexistant!");
        }
    }
}
