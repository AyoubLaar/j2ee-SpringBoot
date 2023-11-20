package com.example.projetj2E.repository;

import com.example.projetj2E.entites.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



@SpringBootTest
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void savePatient(){
        String dateStr = "14/11/2023";

        // Formatter pour convertir la chaîne en LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Analyse de la chaîne en LocalDate
        LocalDate date = LocalDate.parse(dateStr, formatter);
        Patient patient=Patient.builder()
                .nom("j2e")
                .prenom("projet")
                .patientLogin("projetj2e@gmail.com")
                .dateDeNaissance(date)
                .build();

        patientRepository.save(patient);

    }


}