package com.example.projetj2E.services;

import com.example.projetj2E.entites.Admin;
import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.hassing.HassingAndMatchingTester;
import com.example.projetj2E.repository.AdminRepository;
import com.example.projetj2E.repository.MedecinRepository;
import com.example.projetj2E.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthentificationServiceImple implements AuthentificationService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private MedecinRepository medecinRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public String creerSessionIdPourPatient(String email){
        String message = "patient:"+email+":"+LocalDateTime.now().toString();
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
    public String creerSessionIdPourAdmin(String email) {
        String message = "admin:"+email+":"+ LocalDateTime.now().toString();
        String sessionId = HassingAndMatchingTester.encrypt(message);
        return sessionId;
    }

    @Override
    public Patient toPatient(String sessionId) throws UserNotFoundException {
        String message = HassingAndMatchingTester.decrypt(sessionId);
        String [] split_message = message.split(":");
        if(!Objects.equals(split_message[0], "patient"))throw new UserNotFoundException("pas un patient!");
        Optional<Patient> optional_patient = patientRepository.findBypatientLogin(split_message[1]);
        if(optional_patient.isEmpty())throw new UserNotFoundException("email inexistant!");
        Patient patient = optional_patient.get();
        if(patient.getSessionId().equals(sessionId))return patient;
        throw new UserNotFoundException("Jeton invalide");
    }

    @Override
    public Medecin toMedecin(String sessionId) throws UserNotFoundException {
        String message = HassingAndMatchingTester.decrypt(sessionId);
        String [] split_message = message.split(":");
        if(!Objects.equals(split_message[0], "medecin"))throw new UserNotFoundException("pas un medecin!");
        Optional<Medecin> optional_medecin = medecinRepository.findByMedLogin(split_message[1]);
        if(optional_medecin.isEmpty())throw new UserNotFoundException("email inexistant!");
        Medecin medecin = optional_medecin.get();
        if(medecin.getSessionId().equals(sessionId))return medecin;
        throw new UserNotFoundException("Jeton invalide");
    }

    public Admin toAdmin(String sessionId) throws UserNotFoundException{
        String message = HassingAndMatchingTester.decrypt(sessionId);
        String [] split_message = message.split(":");
        if(!Objects.equals(split_message[0], "admin"))throw new UserNotFoundException("pas l'admin!");
        Optional<Admin> optional_admin = adminRepository.findBylogin(split_message[1]);
        if(optional_admin.isEmpty())throw new UserNotFoundException("email inexistant!");
        Admin admin = optional_admin.get();
        if(admin.getSessionId().equals(sessionId))return admin;
        throw new UserNotFoundException("Jeton invalide");
    }

    @Override
    public String getRole(String sessionId) {
        String [] split_message = HassingAndMatchingTester.decrypt(sessionId).split(":");
        String role = split_message[0];
        switch (role){
            case "patient":
                try{
                    Optional<Patient> optional_patient = patientRepository.findBypatientLogin(split_message[1]);
                    if(optional_patient.isEmpty())throw new UserNotFoundException("email inexistant!");
                    Patient patient = optional_patient.get();
                    if(patient.getSessionId().equals(sessionId))return "patient";
                    throw new UserNotFoundException("Jeton invalide");
                }catch(Exception exception){
                    System.out.println(exception.getMessage());
                    return "UNAUTHORIZED";
                }
            case "medecin":
                try{
                    Optional<Medecin> optional_medecin = medecinRepository.findByMedLogin(split_message[1]);
                    if(optional_medecin.isEmpty())throw new UserNotFoundException("email inexistant!");
                    Medecin medecin = optional_medecin.get();
                    if(medecin.getSessionId().equals(sessionId))return "medecin";
                    throw new UserNotFoundException("Jeton invalide");
                }catch(Exception exception){
                    System.out.println(exception.getMessage());
                    return "UNAUTHORIZED";
                }
            case "admin":
                try{
                    Optional<Admin> optional_admin = adminRepository.findBylogin(split_message[1]);
                    if(optional_admin.isEmpty())throw new UserNotFoundException("email inexistant!");
                    Admin admin = optional_admin.get();
                    if(admin.getSessionId().equals(sessionId))return "admin";
                    throw new UserNotFoundException("Jeton invalide");
                }catch(Exception exception){
                    System.out.println(exception.getMessage());
                    return "UNAUTHORIZED";
                }
            default:
                return "UNAUTHORIZED";
        }
    }


}
