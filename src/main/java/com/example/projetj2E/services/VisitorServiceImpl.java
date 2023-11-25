package com.example.projetj2E.services;

import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.Specialite;
import com.example.projetj2E.entites.Ville;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.MedecinToSearch;
import com.example.projetj2E.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VisitorServiceImpl implements VisitorService{

    private final MedecinRepository medecinRepository;

    private final SpecialiteService specialiteService;

    public VisitorServiceImpl(MedecinRepository medecinRepository, SpecialiteService specialiteService) {
        this.medecinRepository = medecinRepository;
        this.specialiteService = specialiteService;
    }

    @Override
    public ResponseEntity<Object> rechercherMedecinvisitor( MedecinToSearch medecinToSearch) throws UserNotFoundException {
        Specialite medspecialite = Specialite.builder()
                .nomDuSpecialite(medecinToSearch.getSpecialite())
                .build();
        Ville ville = new Ville(medecinToSearch.getVille());
        if(!(medecinToSearch.getNom()==null)){
            List<Medecin> medecins = medecinRepository.findAllByVilleAndSpecialitesAndNom(ville, medspecialite
                    ,medecinToSearch.getNom());
            if (medecins.isEmpty()) {
                throw new UserNotFoundException("Medecin non trouvé");
            }
            List<Map<String, Object>> medecinstrouves = new ArrayList<>();
            for (Medecin medecin : medecins) {
                Map<String, Object> medecin_json = new HashMap<>();
                medecin_json.put("nom", medecin.getNom());
                medecin_json.put("prenom", medecin.getPrenom());
                medecin_json.put("ville",medecin.getVille());
                medecin_json.put("sexe",medecin.getSexe());
                medecin_json.put("address_cabinet",medecin.getAdressCabinet());
                medecin_json.put("specialite",specialiteService.getMedecinSpecialites(medecin.getSpecialites()));
                medecin_json.put("id",medecin.getMedecinId())  ;
                medecinstrouves.add(medecin_json);
            }
            return ResponseEntity.status(HttpStatus.OK).body(medecinstrouves);
        }

        List<Medecin> medecins = medecinRepository.findAllByVilleAndSpecialites(ville, medspecialite);

        if (medecins.isEmpty()) {
            throw new UserNotFoundException("Medecin non trouvé");
        }
        List<Map<String, Object>> medecinstrouves = new ArrayList<>();
        for (Medecin medecin : medecins) {
            Map<String, Object> medecin_json = new HashMap<>();
            medecin_json.put("nom", medecin.getNom());
            medecin_json.put("prenom", medecin.getPrenom());
            medecin_json.put("ville",medecin.getVille().getNomVille());
            medecin_json.put("sexe",medecin.getSexe());
            medecin_json.put("address_cabinet",medecin.getAdressCabinet());
            medecin_json.put("specialite",specialiteService.getMedecinSpecialites(medecin.getSpecialites()));
            medecin_json.put("id",medecin.getMedecinId())  ;
            medecinstrouves.add(medecin_json);
        }
        return ResponseEntity.status(HttpStatus.OK).body(medecinstrouves);

    }
}
