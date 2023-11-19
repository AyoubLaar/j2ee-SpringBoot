package com.example.projetj2E.services;


import com.example.projetj2E.entites.EtatDemandeMedecin;
import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.models.MedecinModel;
import com.example.projetj2E.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedecinServicesImpl implements MedecinServices {
    @Autowired
    private MedecinRepository medecinRepository;
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
              medecinRepository.save(medecin);
        return medecin;

    }
}
