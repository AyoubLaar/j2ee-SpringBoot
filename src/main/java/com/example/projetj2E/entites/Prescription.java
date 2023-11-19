package com.example.projetj2E.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prescription {

    @Id
    @SequenceGenerator(
            name = "prescription_gen",
            sequenceName = "prescription_gen",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "prescription_gen")
    private Long prescriptionId;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Etatprescription statusPrescription;

    @ManyToMany
    @JoinTable(
            name = "prescription_medicament",
            joinColumns = @JoinColumn(name = "prescription_id",
                    referencedColumnName ="prescriptionId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "nom_du_medicament",
                    referencedColumnName = " nomDuMedicament"
            )
    )
    private List<Medicament> medicaments;
}
