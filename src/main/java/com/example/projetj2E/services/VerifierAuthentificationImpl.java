package com.example.projetj2E.services;

import com.example.projetj2E.entites.Admin;
import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.User;
import com.example.projetj2E.repository.AdminRepository;
import com.example.projetj2E.repository.MedecinRepository;
import com.example.projetj2E.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class VerifierAuthentificationImpl implements VerifierAuthentification {

    @Autowired
    private AuthentificationServiceImple authentificationService;
   @Autowired
   private AdminRepository adminRepository;
   @Autowired
   private PatientRepository patientRepository;
   @Autowired
   private MedecinRepository medecinRepository;
    @Override
    public boolean verifyAuthentificationPatient(String sessionid) throws UserNotFoundException, HandleIncorrectAuthentification {
        Patient patientsession= authentificationService.toPatient(sessionid);
        Optional<Patient> patientdb=patientRepository.findBypatientLogin(patientsession.getPatientLogin());
        if(patientdb.isEmpty()){
            throw new UserNotFoundException("Non trouver");
        }
        return patientdb.get().getSessionId().equals(sessionid);
    }

    @Override
    public boolean verifyAuthentificationMedecin(String sessionid) throws UserNotFoundException, HandleIncorrectAuthentification {
        Medecin medInSession = authentificationService.toMedecin(sessionid);
        Optional<Medecin> MedecinInDb=medecinRepository.findByMedLogin(medInSession.getMedLogin());
        if(MedecinInDb.isEmpty()){
            throw new UserNotFoundException("Non trouver");
        }
        return MedecinInDb.get().getSessionId().equals(sessionid);
    }

    @Override
    public boolean verifyAuthentificationAdmin(String sessionid) throws UserNotFoundException, HandleIncorrectAuthentification {
        Admin adminsession= authentificationService.toAdmin(sessionid);
        Optional<Admin> admindb=adminRepository.findBylogin(adminsession.getLogin());
        if(admindb.isEmpty()){
            throw new UserNotFoundException("Non trouver");
        }
        return admindb.get().getSessionId().equals(sessionid);
    }

}
