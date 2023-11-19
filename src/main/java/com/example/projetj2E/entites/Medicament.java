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
public class Medicament {

    @Id
    @SequenceGenerator(
            name = "medoc_gen",
            sequenceName = "medoc_gen",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "medoc_gen")
    private String nomDuMedicament;

    @Column(
            nullable = false,
            updatable = false
    )
    private Double prix;

    @ManyToMany(mappedBy = "medicaments")
    private List<Prescription> prescriptions;

}
