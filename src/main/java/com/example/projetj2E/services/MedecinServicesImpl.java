package com.example.projetj2E.services;


import com.example.projetj2E.entites.*;
import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.RendezVousNotFound;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.hassing.HassingAndMatchingTester;
import com.example.projetj2E.models.MedecinModel;
import com.example.projetj2E.models.RdvToAcceptOrReject;
import com.example.projetj2E.models.User;
import com.example.projetj2E.repository.MedecinRepository;

import com.example.projetj2E.repository.RendezVousRepository;
import com.example.projetj2E.repository.SpecialiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.*;

@Service
public class MedecinServicesImpl implements MedecinServices {
    @Autowired
    private RendezVousRepository rendezVousRepository;
    @Autowired
    private RdvService rdvService;
    @Autowired
    private  VerifierAuthentificationImpl verifierAuthentification;

    private final MedecinRepository medecinRepository;
    private final SpecialiteService specialiteService;
    private final AuthentificationService authentificationService;
    private final SpecialiteRepository specialiteRepository;

    public MedecinServicesImpl(MedecinRepository medecinRepository,
                               SpecialiteService specialiteService,
                               SpecialiteRepository specialiteRepository,
                               AuthentificationService authentificationService) {
        this.medecinRepository = medecinRepository;
        this.specialiteService = specialiteService;
        this.specialiteRepository = specialiteRepository;
        this.authentificationService = authentificationService;
    }

    @Override
    public ResponseEntity<String> registerMedecin(MedecinModel medecinModel) throws GereExistEmailException {
        Medecin medecin = new Medecin();
        medecin.setAdressCabinet(medecinModel.getAdressCabinet());
        medecin.setCodeOrdreMedecin(medecinModel.getCodeOrdreMedecin());
        medecin.setNom(medecinModel.getNom());
        medecin.setSexe(medecinModel.getSexe());
        medecin.setPassword(HassingAndMatchingTester.passwordtohash(medecinModel.getPassword()));
        medecin.setPrenom(medecinModel.getPrenom());
        medecin.setVille(medecinModel.getVille());
        medecin.setDateDeNaissance(medecinModel.getDateDeNaissance());
        medecin.setStatusDemande(StatusMedecin.Attente);
        if ((medecinRepository.findByMedLogin(medecinModel.getMedLogin()).isPresent())) {
            throw new GereExistEmailException("email exist déja");
        }
        String sessionId=authentificationService.creerSessionIdPourMedecin(medecin.getMedLogin());
        medecin.setSessionId(sessionId);
        medecin.setMedLogin(medecinModel.getMedLogin());
        medecin.setAutorisation(Autorisation.NonAutoriser);
        List<Specialite> specialites = specialiteService.returnSpecialites(medecinModel.getSpecialites());
        medecin.setSpecialites(specialites);
        try{
            medecinRepository.save(medecin);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("ERROR");
        }


        return ResponseEntity.status(HttpStatus.OK).body(sessionId);

    }



    @Override
    public ResponseEntity<String> authentifierUser(User medecin) throws HandleIncorrectAuthentification, UserNotFoundException {
        Optional<Medecin> optionalMedecin = medecinRepository.findByMedLogin(medecin.getLogin());
        if (optionalMedecin.isPresent()) {
            Medecin medecinfound=optionalMedecin.get();
            String userPassword = medecinfound.getPassword();
            if (!HassingAndMatchingTester.passwordMatching(userPassword, medecin.getPassword()) || medecinfound.getAutorisation().equals(Autorisation.NonAutoriser) ) {
                throw new HandleIncorrectAuthentification("Saisir des données correct ou votre compte a été  bloqué");
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
    public ResponseEntity<Object> mesDemandeDeRdv(@RequestHeader("token") String sessionid) throws UserNotFoundException, HandleIncorrectAuthentification {
        if (verifierAuthentification.verifyAuthentificationMedecin(sessionid)) {
            Medecin medecin = authentificationService.toMedecin(sessionid);
            List<RendezVous> tousmesrdv = medecin.getMesrendezvous();
            List<Map<String, Object>> medemandes = new ArrayList<>();
            for (RendezVous demande : tousmesrdv) {
                rdvService.mettreAjourEtatRdv(demande);
                if (demande.getStatusRdv().equals(StatusRdv.Attente)
                ) {
                    Map<String, Object> Rendezvous_json = new HashMap<>();
                    Rendezvous_json.put("rdvId", demande.getRdvId());
                    Patient patient = demande.getPatient();
                    Rendezvous_json.put("nom", patient.getNom());
                    Rendezvous_json.put("prenom", patient.getPrenom());
                    Rendezvous_json.put("telephone", patient.getTelephone());
                    Rendezvous_json.put("email", patient.getPatientLogin());
                    Rendezvous_json.put("dateRdv", demande.getDateRdv());
                    Rendezvous_json.put("heureRdv", demande.getHeureRdv());
                    medemandes.add(Rendezvous_json);
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(medemandes);
        } else {
            throw new HandleIncorrectAuthentification("Non authentifié");
        }
    }

    @Override
    public List<Medecin> findAllMedecinDemand() {
         return medecinRepository.findAllByStatusDemande(StatusMedecin.Attente);
    }

    @Transactional
    @Override
    public ResponseEntity<String> acceptOrRejectDemand(RdvToAcceptOrReject rdv, String sessionId)
            throws HandleIncorrectAuthentification, UserNotFoundException, RendezVousNotFound {
        if (!verifierAuthentification.verifyAuthentificationMedecin(sessionId)){
            throw new HandleIncorrectAuthentification("Non authentifié");
        }
        Optional<RendezVous> optionalRendezVous=rendezVousRepository.findById(rdv.getRdvId());
        if(optionalRendezVous.isPresent()){
            RendezVous  rdvFound=optionalRendezVous.get();
            if(rdv.getStatusRdv().equals("Accepter")){
                rdvService.accepterRdv(rdvFound);
                rdvService.rejectAllTheSameTimRdvWith(rdvFound);
                return  ResponseEntity.status(HttpStatus.OK).body("accepter");
            } else if (rdv.getStatusRdv().equals("Rejeter")) {
                rdvService.rejeterRdv(rdvFound);
                return ResponseEntity.status(HttpStatus.OK).body("rejeter");
            }
        }
        throw new RendezVousNotFound("rdv n'existe pas");
    }

    @Override
    public ResponseEntity<String> accepeterMedecin(Medecin medecin) {
        medecin.setStatusDemande(StatusMedecin.Valider);
        medecin.setAutorisation(Autorisation.Autoriser);
        medecinRepository.save(medecin);
        return ResponseEntity.status(HttpStatus.OK).body("accepter");
    }

    @Override
    public ResponseEntity<String> rejeterMedecin(Medecin medecin) {
        medecin.setStatusDemande(StatusMedecin.Rejeter);
        medecinRepository.save(medecin);
        return ResponseEntity.status(HttpStatus.OK).body("rejeter");
    }

    @Override
    public List<Medecin> trouverParNomEtPrenom(String nom, String prenom) {
        return  medecinRepository.findAllByPrenomAndNom(prenom,nom);
    }

    @Override
    public ResponseEntity<Object> mesRdv(String sessionid) throws HandleIncorrectAuthentification, UserNotFoundException {
        if (!verifierAuthentification.verifyAuthentificationMedecin(sessionid)){
            throw new HandleIncorrectAuthentification("Non authentifié");
        }
        Medecin medecin= authentificationService.toMedecin(sessionid);
        List<RendezVous> tousmesrdv=medecin.getMesrendezvous();
        List<Map<String, Object>> mesRdv= new ArrayList<>();
        for (RendezVous demande : tousmesrdv) {
            if (demande.getStatusRdv().equals(StatusRdv.Accepter))
            {
                Map<String, Object> Rendezvous_json = new HashMap<>();
                Rendezvous_json.put("rdvId",demande.getRdvId());
                Patient patient =demande.getPatient();
                Rendezvous_json.put("statusRdv",demande.getStatusRdv());
                Rendezvous_json.put("nom",patient.getNom());
                Rendezvous_json.put("prenom",patient.getPrenom());
                Rendezvous_json.put("telephone",patient.getTelephone());
                Rendezvous_json.put("email",patient.getPatientLogin());
                Rendezvous_json.put("dateRdv",demande.getDateRdv());
                Rendezvous_json.put("heureRdv",demande.getHeureRdv());
                mesRdv.add(Rendezvous_json);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(mesRdv);
    }

    @Override
    public void  save(Medecin medecin) {
        medecinRepository.save(medecin);
    }

    @Override
    public Optional<Medecin> findById(Long id) {
        return medecinRepository.findById(id);
    }

    @Override
    public Optional<Medecin> findByMedecinLogin(String login) {
        return medecinRepository.findByMedLogin(login);
    }

    @Override
    public ResponseEntity<String> annulerRdv(Long rdvId, String sessionId) throws HandleIncorrectAuthentification, UserNotFoundException, RendezVousNotFound {
        if (!verifierAuthentification.verifyAuthentificationMedecin(sessionId)){
            throw new HandleIncorrectAuthentification("Non authentifié");
        }
        Optional<RendezVous> optionalRendezVous=rendezVousRepository.findById(rdvId);
        if(optionalRendezVous.isPresent()) {
            RendezVous rdvFound = optionalRendezVous.get();
            rdvService.annulerRdv(rdvFound);
            return  ResponseEntity.status(HttpStatus.OK).body("accepter");
        }throw new RendezVousNotFound("rdv n'existe pas");

}
}