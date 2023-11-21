package com.example.projetj2E.services;

import com.example.projetj2E.entites.Admin;
import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.entites.Ville;
import com.example.projetj2E.erreur.GereMedecinNotFound;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.hassing.HassingAndMatchingTester;
import com.example.projetj2E.models.MedecinToDelete;
import com.example.projetj2E.models.MedecinToSearch;
import com.example.projetj2E.models.User;
import com.example.projetj2E.repository.AdminRepository;
import com.example.projetj2E.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private SpecialiteService specialiteService;

    @Autowired
    private AuthentificationService authentificationService;
    @Override
    public void supprimerMedecin(MedecinToSearch medecinToSearch)  {



    }

    @Override
    public ResponseEntity<Object> chercherMedecin(MedecinToDelete medecinToDelete) throws GereMedecinNotFound {
        Ville ville = new Ville(medecinToDelete.getVille());
        String nom= medecinToDelete.getNom();
        String prenom=medecinToDelete.getPrenom();
        List<Medecin> medecins = medecinRepository.findAllByPrenomAndNomAndVille(prenom,nom,ville);
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
}
