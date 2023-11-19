package com.example.projetj2E.repository;

import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.Sexe;
import com.example.projetj2E.entites.Specialite;
import com.example.projetj2E.entites.Ville;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class MedecinRepositoryTest {

    @Autowired
    private MedecinRepository medecinRepository;

    private Medecin medecin;

     @BeforeEach
     void setUp() {

     }
      @Test
      public void savemedecin(){
             //given
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
          Specialite specialite1=new Specialite("Oncologue");
          Specialite specialite2=new Specialite("chirurgien");
          Ville ville=new Ville("rabat");
          medecin=Medecin.builder()
                  .sexe(Sexe.Homme)
                  .password("medecin21")
                  .adressCabinet("rabatinpt")
                  .prenom("medecin")
                  .nom("23")
                  .codeOrdreMedecin("Axdfgru45667X4")
                  .dateDeNaissance(LocalDate.parse("23/08/2005",formatter))
                  .medLogin("medecin21@gmail.com")
                  .specialites(List.of(specialite1,specialite2))
                  .ville(ville)
                  .build();
                   medecinRepository.save(medecin);
                   //when
          boolean exist=medecinRepository.existsById(1L);
          assertThat(exist).isTrue();

      }

}