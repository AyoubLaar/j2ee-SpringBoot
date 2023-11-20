package com.example.projetj2E.repository;

import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.Specialite;
import com.example.projetj2E.entites.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin,Long> {
      Optional<Medecin>  findByMedLogin(String email);

      List<Medecin> findAllByVilleAndSpecialites(Ville ville, Specialite specialites);
      List<Medecin> findAllByVilleAndSpecialitesAndNom(Ville ville, Specialite specialites,String nom);

}
