package com.example.projetj2E.services;

import com.example.projetj2E.entites.Etatrdv;
import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.entites.RendezVous;
import com.example.projetj2E.entites.Sexe;
import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.hassing.HassingAndMatchingTester;
import com.example.projetj2E.models.PatientModel;
import com.example.projetj2E.models.RdvModel;
import com.example.projetj2E.models.User;
import com.example.projetj2E.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientServices{
    @Autowired
    private PatientRepository patientRepository;



    @Override
    public Patient savePatient(PatientModel patientModel) throws GereExistEmailException {
              Patient patient=new Patient();
              patient.setNom(patientModel.getNom());
              patient.setPrenom(patientModel.getPrenom());
              patient.setDateDeNaissance(patientModel.getDateDeNaissance());
              patient.setSexe(patientModel.getSexe());
              patient.setPassword(HassingAndMatchingTester.passwordtohash(patientModel.getPassword()));
              patient.setTelephone(patientModel.getTelephone());
              if(patientRepository.findBypatientLogin(patientModel.getPatientLogin()).isPresent()){
                  throw  new GereExistEmailException("il y a déja un utilisateur avec cet email");
              }
              patient.setPatientLogin(patientModel.getPatientLogin());
              patientRepository.save(patient);
              return patient;
    }

    @Override
    public String authentifierUser(User patient) throws HandleIncorrectAuthentification {
        Optional<Patient> optionalPatient = patientRepository.findBypatientLogin(patient.getLogin());
        if(optionalPatient.isPresent()){
            String userPassword=optionalPatient.get().getPassword();
            if(!HassingAndMatchingTester.passwordMatching(userPassword, patient.getPassword()))
            {throw new HandleIncorrectAuthentification("votre mot de passe ou login n'est pas correct");}

            return "sessionid";
        }
        throw new HandleIncorrectAuthentification("votre mot de passe ou login n'est pas correct");
    }

    @Override
    public void choisirUnRdv(RdvModel rdvModel) {

        RendezVous rendezVous=new RendezVous();
        rendezVous.setRdvId(rdvModel.getMedecinId());
        rendezVous.setHeureRdv(rdvModel.getHeureRdv());
        rendezVous.setDateRdv(rdvModel.getDateRdv());
        rendezVous.setStatusRdv(Etatrdv.Attente);
    }
}
