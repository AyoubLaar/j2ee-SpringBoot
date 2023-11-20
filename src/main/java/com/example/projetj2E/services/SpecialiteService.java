package com.example.projetj2E.services;

import com.example.projetj2E.entites.Specialite;

import java.util.List;

public interface SpecialiteService {

    List<Specialite> returnSpecialites(List<String> nomsSpecialites);

    List<String> getAllSpecialites();

    public List<String> getMedecinSpecialites(List<Specialite> specialites);
}
