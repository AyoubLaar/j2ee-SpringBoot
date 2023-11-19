package com.example.projetj2E.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RendezVous {

    @Id
    @SequenceGenerator(
            name = "rdv",
            sequenceName = "rdv",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "rdv")
    private Long rdvId;
    private LocalDate dateRdv;
    private LocalTime heureRdv;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "medecin_id",
            referencedColumnName = " MedecinId"
    )
    private Medecin medecin;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "patient_id",
            referencedColumnName = " patientId"
    )
    private Patient patient;

    @Enumerated(EnumType.STRING)
    private Etatrdv statusRdv;


}
