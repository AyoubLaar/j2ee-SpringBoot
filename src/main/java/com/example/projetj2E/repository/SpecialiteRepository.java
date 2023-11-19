package com.example.projetj2E.repository;

import com.example.projetj2E.entites.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialiteRepository extends JpaRepository<Specialite,String> {


}
