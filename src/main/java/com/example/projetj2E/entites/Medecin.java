package com.example.projetj2E.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="medecin_login_unique",
                        columnNames = "medLogin"
                ), @UniqueConstraint(
                name="code_ordre_med_unique",
                columnNames = "codeOrdreMedecin"
        )
        }
)
@Builder
public class Medecin {

    @Id
    @SequenceGenerator(
            name = "medecin_gen",
            sequenceName = "medecin_gen",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "medecin_gen")
    private Long MedecinId;

    @Column(
            nullable = false,
            updatable = false
    )
    private String medLogin;

    @Column(
            nullable = false,
            updatable = false
    )
    private String password;

    @Column(
            nullable = false
    )
    private String nom;

    @Column(
            nullable = false,
            updatable = false
    )
    private String prenom;

    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDate dateDeNaissance;

    @Column(
            nullable = false,
            updatable = false
    )
    private String codeOrdreMedecin;


    @OneToMany(
              mappedBy = "medecin"
    )
    private List<RendezVous> mesrendezvous;

    @ElementCollection
    private List<Specialite> specialites;

    @Embedded
    private Ville ville;

    @Column(columnDefinition = "TEXT",
             nullable = false
    )
    private String adressCabinet;

    @Enumerated(EnumType.STRING)
    private EtatDemandeMedecin statusDemande;

    @Enumerated(EnumType.STRING)
    private Sexe sexe;

    private String sessionId;

}
