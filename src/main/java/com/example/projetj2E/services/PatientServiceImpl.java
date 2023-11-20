package com.example.projetj2E.services;

import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.hassing.HassingAndMatchingTester;
import com.example.projetj2E.models.PatientModel;
import com.example.projetj2E.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientServices{

    private final PatientRepository patientRepository;

    public  PatientServiceImpl(PatientRepository patientRepository){
        this.patientRepository=patientRepository;
    }

    @Override
    public Patient savePatient(PatientModel patientModel) throws GereExistEmailException {

              Patient patient=new Patient();
              patient.setNom(patientModel.getNom());
              patient.setPrenom(patientModel.getPrenom());
              patient.setDateDeNaissance(patientModel.getDateDeNaissance());
              patient.setSexe(patientModel.getSexe());
              patient.setPassword(HassingAndMatchingTester.passwordtohash(patientModel.getPassword()));
              patient.setTelephone(patientModel.getTelephone());
              if(patientRepository.existsByPatientLogin(patientModel.getPatientLogin())){
                      throw new GereExistEmailException("il y a déja un utilisateur avec cet email");
              }
              patient.setPatientLogin(patientModel.getPatientLogin());
              patientRepository.save(patient);

              return patient;
    }
}
