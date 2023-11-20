package com.example.projetj2E.services;

import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.models.PatientModel;
import com.example.projetj2E.models.User;

public interface PatientServices {

    Patient savePatient(PatientModel patientModel);
    String authentifierUser(User patient);
}
