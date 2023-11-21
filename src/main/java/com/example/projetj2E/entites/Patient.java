package com.example.projetj2E.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
                        name="patient_login_unique",
                        columnNames = "patientLogin"
                )
        }
)
@Builder
public class Patient {

    @Id
    @SequenceGenerator(
            name = "patient_gen",
            sequenceName = "patient_gen",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "patient_gen")
    private Long patientId;
    @Column(
            nullable = false,
            updatable = false
    )
    @Email
    private String patientLogin;
    @Column(
            nullable = false
    )
    private String password;
    @Column(
            updatable = false,
            nullable = false
    )
    @NotBlank(message = "votre nom est requis")
    private String nom;
    @Column(
            updatable = false,
            nullable = false
    )
    private String prenom;
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDate dateDeNaissance;
    @Column(
            nullable = false
    )
    private String telephone;

    @Enumerated(EnumType.STRING)
    private Sexe sexe;

    @OneToMany(
           mappedBy = "patient"
    )
    private List<RendezVous> mesrendezvous;

    private String sessionId;

    private Autorisation autorisation;
}
