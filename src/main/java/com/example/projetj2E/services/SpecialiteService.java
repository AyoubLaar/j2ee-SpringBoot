package com.example.projetj2E.services;

import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.Specialite;

import java.util.List;
import java.util.Optional;

public interface SpecialiteService {

    List<Specialite> returnSpecialites(List<String> nomsSpecialites, Optional<Medecin> medecin);

    List<String> getAllSpecialites();
}
