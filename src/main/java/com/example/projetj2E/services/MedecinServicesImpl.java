package com.example.projetj2E.services;


import com.example.projetj2E.entites.*;
import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.GereMedecinNotFound;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.hassing.HassingAndMatchingTester;
import com.example.projetj2E.models.MedecinModel;
import com.example.projetj2E.models.MedecinToSearch;
import com.example.projetj2E.models.RdvModel;
import com.example.projetj2E.models.User;
import com.example.projetj2E.repository.MedecinRepository;
import com.example.projetj2E.repository.RendezVousRepository;
import com.example.projetj2E.repository.SpecialiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.*;

@Service
public class MedecinServicesImpl implements MedecinServices {
    private final MedecinRepository medecinRepository;

    private final SpecialiteService specialiteService;

    @Autowired
    private AuthentificationService authentificationService;
    private RendezVousRepository rendezVousRepository;
    private final SpecialiteRepository specialiteRepository;

    public MedecinServicesImpl(MedecinRepository medecinRepository, SpecialiteService specialiteService, SpecialiteRepository specialiteRepository) {
        this.medecinRepository = medecinRepository;
        this.specialiteService = specialiteService;
        this.specialiteRepository = specialiteRepository;
    }

    @Override
    public Medecin registerMedecin(MedecinModel medecinModel) throws GereExistEmailException {


        Medecin medecin = new Medecin();
        medecin.setAdressCabinet(medecinModel.getAdressCabinet());
        medecin.setCodeOrdreMedecin(medecinModel.getCodeOrdreMedecin());
        medecin.setNom(medecinModel.getNom());
        medecin.setSexe(medecinModel.getSexe());
        medecin.setPassword(HassingAndMatchingTester.passwordtohash(medecinModel.getPassword()));
        medecin.setPrenom(medecinModel.getPrenom());
        medecin.setVille(medecinModel.getVille());
        medecin.setDateDeNaissance(medecinModel.getDateDeNaissance());
        medecin.setStatusDemande(EtatDemandeMedecin.Attente);
        if ((medecinRepository.findByMedLogin(medecinModel.getMedLogin()).isPresent())) {
            throw new GereExistEmailException("il y a déja un utilisateur avec cet email");
        }
        medecin.setMedLogin(medecinModel.getMedLogin());
        // medecinRepository.save(medecin);
        // Optional<Medecin> medecinsaved = medecinRepository.findByMedLogin(medecinModel.getMedLogin());
        List<Specialite> specialites = specialiteService.returnSpecialites(medecinModel.getSpecialites());
        specialiteRepository.saveAll(specialites);// a éliminer seulement pour tester
        medecin.setSpecialites(specialites);
        medecinRepository.save(medecin);
        return medecin;

    }

    @Override
    public ResponseEntity<String> authentifierUser(User medecin) throws HandleIncorrectAuthentification, UserNotFoundException {
        Optional<Medecin> optionalMedecin = medecinRepository.findByMedLogin(medecin.getLogin());
        if (optionalMedecin.isPresent()) {
            Medecin medecinfound=optionalMedecin.get();
            String userPassword = medecinfound.getPassword();
            if (!HassingAndMatchingTester.passwordMatching(userPassword, medecin.getPassword())) {
                throw new HandleIncorrectAuthentification("votre mot de passe ou login n'est pas correct");
            }
            String sessionId = authentificationService.creerSessionIdPourMedecin(optionalMedecin.get().getMedLogin());
            medecinfound.setSessionId(sessionId);
            try {
                medecinRepository.save(medecinfound);
            }catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseEntity.badRequest().body("ERROR");
            }
            return ResponseEntity.ok(sessionId);
        }else{
            throw new UserNotFoundException("email inexistant!");
        }

    }

    @Override
    public ResponseEntity<Object> rechercherMedecin(MedecinToSearch medecinToSearch) throws GereMedecinNotFound {
        Specialite medspecialite = Specialite.builder()
                .nomDuSpecialite(medecinToSearch.getSpecialite())
                .build();
        Ville ville = new Ville(medecinToSearch.getVille());
        if(!(medecinToSearch.getNom()==null)){
            List<Medecin> medecins = medecinRepository.findAllByVilleAndSpecialitesAndNom(ville, medspecialite
                    ,medecinToSearch.getNom());
            if (medecins.isEmpty()) {
                throw new GereMedecinNotFound("Nous n'avons pas trouver un medecin avec le profil demandé");
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
            throw new GereMedecinNotFound("Nous n'avons pas trouver un medecin avec le profil demandé");
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

    @Override
    public ResponseEntity<Object> mesDemandeDeRdv(@RequestHeader("token") String sessionid) throws UserNotFoundException {
           Medecin medecin= authentificationService.toMedecin(sessionid);
           List<RendezVous> mesdemandes=rendezVousRepository.findAllByMedecinAndStatusRdv(medecin,Etatrdv.Attente);
           List<Map<String, Object>> medemandestrouves= new ArrayList<>();
           for (RendezVous demande : mesdemandes) {
            Map<String, Object> Rendezvous_json = new HashMap<>();
              Rendezvous_json.put("rdvId",demande.getRdvId());
              Rendezvous_json.put("patient",demande.getPatient());
              Rendezvous_json.put("dateRdv",demande.getDateRdv());
              Rendezvous_json.put("heureRdv",demande.getHeureRdv());
             medemandestrouves.add(Rendezvous_json);
        }
        return ResponseEntity.status(HttpStatus.OK).body(medemandestrouves);
    }
}