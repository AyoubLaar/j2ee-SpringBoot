package com.example.projetj2E.repository;

import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.entites.RendezVous;
import com.example.projetj2E.entites.Specialite;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
class RendezVousRepositoryTest {
    
    @Autowired
    private  RendezVousRepository rendezVousRepository;


    @Test
    public void ajouterRdv(){
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
        Patient patient=Patient.builder()
                .nom("web")
                .prenom("projet")
                .dateDeNaissance(date1)
                .patientLogin("projetweb@gmail.com")
                .build();
        Specialite specialite=Specialite.builder()
                .nomDuSpecialite("dentist")
                .build();
        Specialite specialite1=Specialite.builder()
                .nomDuSpecialite("chirurgien")
                .build();
        Medecin medecin=Medecin.builder()
                .nom("medecin")
                .prenom("12")
                .dateDeNaissance(date1)
                .codeOrdreMedecin("Med565X78")
                .medLogin("medecin@gmail.com")
                .specialites(List.of(specialite1,specialite))
                .build();
        Medecin medecin2=Medecin.builder()
                .nom("medecin")
                .prenom("13")
                .dateDeNaissance(date1)
                .codeOrdreMedecin("Med89565X78")
                .medLogin("medecin13@gmail.com")
                .specialites(List.of(specialite1,specialite))
                .build();
        RendezVous rdv=RendezVous.builder()
                .heureRdv(heure2)
                .dateRdv(date2)
                .medecin(medecin2)
                .patient(patient)
                .build();

        rendezVousRepository.save(rdv);
    }

}