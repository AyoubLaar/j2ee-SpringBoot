package com.example.projetj2E.repository;

import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.Sexe;

import com.example.projetj2E.entites.Ville;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class MedecinRepositoryTest {

    @Autowired
    private MedecinRepository medecinRepository;

     @BeforeEach
     void setUp() {
         medecinRepository.deleteAll();
     }

        @Test
        public void testSaveMedecin() {
            // given
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Ville ville = new Ville("rabat");
            Medecin medecin = Medecin.builder()
                    .sexe(Sexe.Homme)
                    .password("medecin21")
                    .adressCabinet("rabatinpt")
                    .prenom("medecin")
                    .nom("23")
                    .codeOrdreMedecin("Axdfgru45667X4")
                    .dateDeNaissance(LocalDate.parse("23/08/2005",formatter))
                    .medLogin("medecin21@gmail.com")
                    .ville(ville)
                    .build();

            // when
            Medecin savedMedecin = medecinRepository.save(medecin);

            // then
            assertThat(savedMedecin).isNotNull(); // Vérifie si la sauvegarde a réussi
            assertThat(savedMedecin.getMedecinId()).isNotNull(); // Vérifie si l'ID a été attribué
            assertThat(savedMedecin.getMedecinId()).isGreaterThan(0L); // Vérifie si l'ID est valide
            assertThat(medecinRepository.existsById(savedMedecin.getMedecinId())).isTrue(); // Vérifie si le médecin existe dans la base de données
        }

}