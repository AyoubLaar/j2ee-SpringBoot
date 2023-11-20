package com.example.projetj2E.services;

import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.entites.Sexe;
import com.example.projetj2E.models.PatientModel;
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
    public Patient savePatient(PatientModel patientModel) {
              Patient patient=new Patient();
              patient.setPatientLogin(patientModel.getPatientLogin());
              patient.setNom(patientModel.getNom());
              patient.setPrenom(patientModel.getPrenom());
              patient.setDateDeNaissance(patientModel.getDateDeNaissance());
              patient.setSexe(patientModel.getSexe());
              patient.setPassword(patientModel.getPassword());
              patient.setTelephone(patientModel.getTelephone());
              patientRepository.save(patient);
              return patient;
    }

    @Override
    public String authentifierUser(User patient) {
        Optional<Patient> optionalPatient = patientRepository.findBypatientLogin(patient.getLogin());
        return optionalPatient.toString();
    }
}
