package com.example.projetj2E.repository;

import com.example.projetj2E.entites.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin,Long> {

      Optional<Medecin>  findByMedLogin(String email);
}
