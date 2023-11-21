package com.example.projetj2E.repository;

import com.example.projetj2E.entites.Etatrdv;
import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous,Long> {

    List<RendezVous> findAllByDateRdvAndAndHeureRdvAndStatusRdv(LocalDate daterdv, LocalTime heureRdv, Etatrdv etatrdv);

    List<RendezVous> findAllByMedecinAndStatusRdv(Medecin medecin,Etatrdv etatrdv);
}

