package com.example.projetj2E.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Specialite {

    @Id
    @SequenceGenerator(
            name = "specialite_gen",
            sequenceName = "specialite_gen",
            allocationSize = 1
    )
    private String nomDuSpecialite;
    @ManyToMany(mappedBy = "specialites")
    private List<Medecin> medecins;



}
