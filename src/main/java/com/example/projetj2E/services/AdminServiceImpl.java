package com.example.projetj2E.services;

import com.example.projetj2E.entites.*;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.hassing.HassingAndMatchingTester;
import com.example.projetj2E.models.AcceptOrReject;
import com.example.projetj2E.models.MedecinToDelete;
import com.example.projetj2E.models.User;
import com.example.projetj2E.repository.AdminRepository;
import com.example.projetj2E.repository.MedecinRepository;


import com.example.projetj2E.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.*;

import static com.example.projetj2E.entites.Autorisation.NonAutoriser;


@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private RendezVousRepository rendezVousRepository;
    private final AdminRepository adminRepository;
    private final VerifierAuthentificationImpl verifierAuthentification;
    private final MedecinRepository medecinRepository;

    private final SpecialiteService specialiteService;

    private final AuthentificationService authentificationService;

    public AdminServiceImpl(AdminRepository adminRepository, MedecinRepository medecinRepository, SpecialiteService specialiteService, AuthentificationService authentificationService, VerifierAuthentificationImpl verifierAuthentification) {
        this.adminRepository = adminRepository;
        this.medecinRepository = medecinRepository;
        this.specialiteService = specialiteService;
        this.authentificationService = authentificationService;

        this.verifierAuthentification = verifierAuthentification;
    }

    @Override
    public ResponseEntity<Object> chercherMedecin(@RequestHeader("token") String sessionid, MedecinToDelete medecinToDelete) throws UserNotFoundException, HandleIncorrectAuthentification {
        //on authentifie d'abord l'admin
        if(!verifierAuthentification.verifyAuthentificationAdmin(sessionid)){
            throw new HandleIncorrectAuthentification("Non Authentifie");
        }
        //puis on commence la recherche ville nom et prenom
        String nom= medecinToDelete.getNom();
        String prenom=medecinToDelete.getPrenom();
        List<Medecin> medecins = medecinRepository.findAllByPrenomAndNom(prenom,nom);
        if (medecins.isEmpty()) {
            throw new UserNotFoundException("medecin non trouvé");
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
    public ResponseEntity<String> supprimerMedecin(String sessionid, MedecinToDelete medecinToDelete) throws UserNotFoundException, HandleIncorrectAuthentification {
        //on authenfie d'abord l'admin
        if(!verifierAuthentification.verifyAuthentificationAdmin(sessionid)){
            throw new HandleIncorrectAuthentification("Non Authentifie");
        }
        Optional<Medecin> optionalmedecin=medecinRepository.findById(medecinToDelete.getId());
        if (optionalmedecin.isEmpty()){
            throw new UserNotFoundException("medecin n'existe pas");
        }
        Medecin medecin=optionalmedecin.get();
        List<RendezVous> rendezVous=medecin.getMesrendezvous();
        List<RendezVous> rendouZVousSupprimer=new ArrayList<>();
        for( RendezVous rdv : rendezVous){
            if(rdv.getStatusDemandeRdv().equals(StatusDemandeRdv.Accepeter)){
                rdv.setStatusDemandeRdv(StatusDemandeRdv.Supprimer);
                rendouZVousSupprimer.add(rdv);
            }
        }
        medecin.setMesrendezvous(rendouZVousSupprimer);
        medecin.setAutorisation(Autorisation.NonAutoriser);
        medecinRepository.save(medecin);
        return ResponseEntity.status(HttpStatus.OK).body("deleted");
    }

    @Override
    public ResponseEntity<String> saveAdmin(User user) {
        Admin admin=Admin.builder()
                .login(user.getLogin())
                .password(HassingAndMatchingTester.passwordtohash(user.getPassword()))
                .build();
        adminRepository.save(admin);
        return ResponseEntity.status(HttpStatus.OK).body("saved");
    }

    @Override
    public ResponseEntity<String> accepterOrRejectMedecin(String sessionid, AcceptOrReject medecin) throws UserNotFoundException, HandleIncorrectAuthentification {
        //on authentifie d'abord l'admin
        if(!verifierAuthentification.verifyAuthentificationAdmin(sessionid)){
            throw new HandleIncorrectAuthentification("Non Authentifie");
        }
        Optional<Medecin> optionalmedecin=medecinRepository.findById(medecin.getId());
        if (optionalmedecin.isEmpty()){
            throw new UserNotFoundException("medecin n'existe pas");
        }
        if(medecin.getStatusMedecin().equals("Rejeter"))
        {
            optionalmedecin.get().setCodeOrdreMedecin(null);
            optionalmedecin.get().setStatusDemande(StatusMedecin.Rejeter);
            medecinRepository.save(optionalmedecin.get());
            return ResponseEntity.status(HttpStatus.OK).body("effectuer");
        }
        optionalmedecin.get().setStatusDemande(StatusMedecin.Valider);
        optionalmedecin.get().setAutorisation(Autorisation.Autoriser);
        medecinRepository.save(optionalmedecin.get());
        return ResponseEntity.status(HttpStatus.OK).body("effectuer");
    }

    @Override
    public List<Map<String,Object>> medecinDemand(String sessionid) throws HandleIncorrectAuthentification, UserNotFoundException {
        if(!verifierAuthentification.verifyAuthentificationAdmin(sessionid)){
            throw new HandleIncorrectAuthentification("Non Authentifie");
        }
        List<Medecin> medecins =medecinRepository.findAllByStatusDemande(StatusMedecin.Attente);
        List<Map<String,Object>> mesdemandes=new ArrayList<>();
        for(Medecin medecin:medecins){
            Map<String,Object> demande=new HashMap<>();
            demande.put("id",medecin.getMedecinId());
            demande.put("nom",medecin.getNom());
            demande.put("prenom",medecin.getPrenom());
            demande.put("specialite",specialiteService.getMedecinSpecialites(medecin.getSpecialites()));
            demande.put("login",medecin.getMedLogin());
            demande.put("adresscabinet",medecin.getAdressCabinet());
            demande.put("codeOrdreMedecin",medecin.getCodeOrdreMedecin());
            mesdemandes.add(demande);
        }
        return mesdemandes;
    }


    @Override
    public ResponseEntity<String> authentifierUser(User user) throws HandleIncorrectAuthentification, UserNotFoundException {
        Optional<Admin> optionalAdmin = adminRepository.findBylogin(user.getLogin());
        if(optionalAdmin.isPresent()){
            Admin admin  = optionalAdmin.get();
            String password=admin.getPassword();
            if(!HassingAndMatchingTester.passwordMatching(password, user.getPassword()))
            {
                throw new HandleIncorrectAuthentification("données invalide!");
            }
            String sessionId = authentificationService.creerSessionIdPourAdmin(admin.getLogin());
            admin.setSessionId(sessionId);
            try {
                adminRepository.save(admin);
            }catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseEntity.badRequest().body("ERROR");
            }
            return ResponseEntity.ok(sessionId);
        }else{
            throw new UserNotFoundException("email inexistant!");
        }
    }


}
