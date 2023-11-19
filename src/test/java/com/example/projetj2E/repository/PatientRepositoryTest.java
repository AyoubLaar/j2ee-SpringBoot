package com.example.projetj2E.repository;

import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.entites.RendezVous;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


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

    @Test
    public void savePatientWithRdv(){
        String dateStr = "14/11/2000";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date1 = LocalDate.parse(dateStr, formatter);
        String dateSt = "18/11/2023";
        LocalDate date2 = LocalDate.parse(dateStr, formatter);

        String heureString = "13:45:30"; // Exemple d'une heure au format HH:mm:ss
        // Formatter pour l'heure au format HH:mm:ss
        DateTimeFormatter formattereur = DateTimeFormatter.ofPattern("HH:mm:ss");
        // Conversion de la chaîne en LocalTime
        LocalTime heure = LocalTime.parse(heureString, formattereur);
        String heureStrin = "14:00:30";
        LocalTime heure2 = LocalTime.parse(heureString, formattereur);

        RendezVous rdv1=RendezVous.builder()
                .dateRdv(date2)
                .heureRdv(heure)
                .build();
        RendezVous rdv2=RendezVous.builder()
                .dateRdv(date2)
                .heureRdv(heure2)
                .build();
        Patient patient=Patient.builder()
                .nom("j2e")
                .prenom("projet")
                .dateDeNaissance(date1)
                .patientLogin("projetj2e@gmail.com")
                .mesrendezvous(List.of(rdv1,rdv2))
                .build();

        patientRepository.save(patient);
    }

}