package com.example.projetj2E.repository;

import com.example.projetj2E.entites.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous,Long> {


}
