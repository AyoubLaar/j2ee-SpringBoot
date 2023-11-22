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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.*;


@Service
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;

    private final AdminServiceImpl adminService;
    private final MedecinRepository medecinRepository;

    private final SpecialiteService specialiteService;

    private final AuthentificationService authentificationService;

    public AdminServiceImpl(AdminRepository adminRepository, MedecinRepository medecinRepository, SpecialiteService specialiteService, AuthentificationService authentificationService, AdminServiceImpl adminService) {
        this.adminRepository = adminRepository;
        this.medecinRepository = medecinRepository;
        this.specialiteService = specialiteService;
        this.authentificationService = authentificationService;
        this.adminService = adminService;
    }


    @Override
    public ResponseEntity<Object> chercherMedecin(@RequestHeader("token") String sessionid, MedecinToDelete medecinToDelete) throws UserNotFoundException, HandleIncorrectAuthentification {
        //on authentifie d'abord l'admin
        if(adminService.verifyAuthentification(sessionid)){
            throw  new HandleIncorrectAuthentification("authentification incorrect");
        }
        //puis on commence la recherche ville nom et prenom
        Ville ville = new Ville(medecinToDelete.getVille());
        String nom= medecinToDelete.getNom();
        String prenom=medecinToDelete.getPrenom();
        List<Medecin> medecins = medecinRepository.findAllByPrenomAndNomAndVille(prenom,nom,ville);
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
        if(adminService.verifyAuthentification(sessionid)){
            throw  new HandleIncorrectAuthentification("authentification incorrect");
        }
        Optional<Medecin> optionalmedecin=medecinRepository.findById(medecinToDelete.getId());
        if (optionalmedecin.isEmpty()){
            throw new UserNotFoundException("medecin n'existe pas");
        }
        Medecin medecin=optionalmedecin.get();
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
        if(adminService.verifyAuthentification(sessionid)){
            throw  new HandleIncorrectAuthentification("authentification incorrect");
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
        //on vérifie d'abord l'authentification
        if(adminService.verifyAuthentification(sessionid)){
            throw  new HandleIncorrectAuthentification("authentification incorrect");
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
            String sessionId = authentificationService.creerSessionIdPourPatient(admin.getLogin());
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

    @Override
    public boolean verifyAuthentification(String sessionid) throws UserNotFoundException, HandleIncorrectAuthentification {
        Admin admin= authentificationService.toAdmin(sessionid);
        User user= User.builder()
                .login(admin.getLogin())
                .password(admin.getPassword())
                .build();
        ResponseEntity<String> reponse  =adminService.authentifierUser(user);
        return !Objects.equals(reponse.getBody(), sessionid);
    }
}
