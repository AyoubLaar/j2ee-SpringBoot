package com.example.projetj2E.services;

import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.Specialite;
import com.example.projetj2E.repository.SpecialiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialiteServiceImp implements SpecialiteService{

    private final SpecialiteRepository specialiteRepository;

    public SpecialiteServiceImp(SpecialiteRepository specialiteRepository) {
        this.specialiteRepository = specialiteRepository;
    }

    @Override
    public List<Specialite> returnSpecialites(List<String> nomsSpecialites) {
        List<Specialite> specialites = new ArrayList<>();

        for (String nom : nomsSpecialites) {
            Specialite specialite = Specialite.builder()
                    .nomDuSpecialite(nom).build();
            specialites.add(specialite);
        }

       return specialites;
    }

    @Override
    public List<String> getAllSpecialites() {
        List<Specialite> specialites=specialiteRepository.findAll();

        List<String> stringSpecialites=new ArrayList<>();

        for (Specialite specialite : specialites) {
            stringSpecialites.add(specialite.getNomDuSpecialite());
        }

        return stringSpecialites;

    }

    @Override
    public List<String> getMedecinSpecialites(List<Specialite> specialites) {

        List<String> stringSpecialites=new ArrayList<>();

        for (Specialite specialite : specialites) {
            stringSpecialites.add(specialite.getNomDuSpecialite());
        }

        return stringSpecialites;

    }

}
