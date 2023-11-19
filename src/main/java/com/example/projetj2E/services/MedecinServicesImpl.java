package com.example.projetj2E.services;


import com.example.projetj2E.entites.EtatDemandeMedecin;
import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.Specialite;
import com.example.projetj2E.models.MedecinModel;
import com.example.projetj2E.repository.MedecinRepository;
import com.example.projetj2E.repository.SpecialiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedecinServicesImpl implements MedecinServices {
    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
     private  SpecialiteService specialiteService;

    @Autowired
    private SpecialiteRepository specialiteRepository;
    @Override
    public Medecin registerMedecin(MedecinModel medecinModel) {


              Medecin medecin=new Medecin();
              medecin.setAdressCabinet(medecinModel.getAdressCabinet());
              medecin.setCodeOrdreMedecin(medecinModel.getCodeOrdreMedecin());
              medecin.setNom(medecinModel.getNom());
              medecin.setSexe(medecinModel.getSexe());
              medecin.setPassword(medecinModel.getPassword());
              medecin.setPrenom(medecinModel.getPrenom());
              medecin.setVille(medecinModel.getVille());
              medecin.setMedLogin(medecinModel.getMedLogin());
              medecin.setDateDeNaissance(medecinModel.getDateDeNaissance());
              medecin.setStatusDemande(EtatDemandeMedecin.Attente);
              //try catch
              medecinRepository.save(medecin);

              Optional<Medecin> medecinsaved=medecinRepository.findByMedLogin(medecinModel.getMedLogin());
              List<Specialite> specialites=specialiteService.returnSpecialites(medecinModel.getSpecialites(),medecinsaved);
              specialiteRepository.saveAll(specialites);// a éliminer seulement pour tester
              medecin.setSpecialites(specialites);
              medecinRepository.save(medecin);
        return medecin;

    }
}
