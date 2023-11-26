package com.example.projetj2E.services;

import com.example.projetj2E.entites.*;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.RendezVousNotFound;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.hassing.HassingAndMatchingTester;
import com.example.projetj2E.models.*;
import com.example.projetj2E.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.*;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private RdvService rdvService;
    @Autowired
    private PatientServiceImpl patientServices;
    @Autowired
    private MedecinServices medecinServices;

    private final AdminRepository adminRepository;
    private final VerifierAuthentificationImpl verifierAuthentification;
    private final SpecialiteService specialiteService;
    private final AuthentificationService authentificationService;

    public AdminServiceImpl(AdminRepository adminRepository, SpecialiteService specialiteService
            , AuthentificationService authentificationService,
                            VerifierAuthentificationImpl verifierAuthentification) {
        this.adminRepository = adminRepository;
        this.specialiteService = specialiteService;
        this.authentificationService = authentificationService;
        this.verifierAuthentification = verifierAuthentification;
    }

    @Override
    public ResponseEntity<Object> chercherMedecin(@RequestHeader("token") String sessionid,
                                                  MedecinToSearch medecin)
            throws UserNotFoundException, HandleIncorrectAuthentification {
        if (!verifierAuthentification.verifyAuthentificationAdmin(sessionid)) {
            throw new HandleIncorrectAuthentification("Non Authentifie");
        }
        List<Medecin> medecins = medecinServices.trouverParNomEtPrenom(medecin.getNom().toLowerCase(),
                medecin.getPrenom().toLowerCase());
        if (!medecins.isEmpty()) {
            List<Map<String, Object>> medecinstrouves = new ArrayList<>();
            for (Medecin medecinFound : medecins) {
                Map<String, Object> medecin_json = new HashMap<>();
                medecin_json.put("nom", medecinFound.getNom());
                medecin_json.put("prenom", medecinFound.getPrenom());
                medecin_json.put("ville", medecinFound.getVille());
                medecin_json.put("sexe", medecinFound.getSexe());
                medecin_json.put("address_cabinet", medecinFound.getAdressCabinet());
                medecin_json.put("specialite", specialiteService.getMedecinSpecialites(medecinFound.getSpecialites()));
                medecin_json.put("id", medecinFound.getMedecinId());
                medecinstrouves.add(medecin_json);
            }
            return ResponseEntity.status(HttpStatus.OK).body(medecinstrouves);
        }
        throw new UserNotFoundException("medecin non trouvé");
    }


    @Override
    public ResponseEntity<String> saveAdmin(String login, String password) {
        Admin admin = Admin.builder()
                .login(login)
                .password(HassingAndMatchingTester.passwordtohash(password))
                .build();
        try{
            adminRepository.save(admin);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("ERROR");
        }
       return ResponseEntity.ok().body("saved");
    }

    @Override
    public ResponseEntity<String> accepterOrRejectMedecin(String sessionid, AcceptOrReject medecin)
            throws UserNotFoundException, HandleIncorrectAuthentification {
        //on authentifie d'abord l'admin
        if (!verifierAuthentification.verifyAuthentificationAdmin(sessionid)) {
            throw new HandleIncorrectAuthentification("Non Authentifie");
        }
        Optional<Medecin> optionalmedecin = medecinServices.findById(medecin.getId());
        if (optionalmedecin.isPresent()) {
            Medecin medecinFound = optionalmedecin.get();
            if (medecin.getStatusMedecin().equals("Rejeter")) {
                return medecinServices.rejeterMedecin(medecinFound);
            } else if (medecin.getStatusMedecin().equals("Accepter")) {
                return medecinServices.accepeterMedecin(medecinFound);
            }
        }
        throw new UserNotFoundException("medecin n'existe pas");

    }

    @Override
    public List<Map<String, Object>> medecinDemand(String sessionid)
            throws HandleIncorrectAuthentification, UserNotFoundException {
        if (!verifierAuthentification.verifyAuthentificationAdmin(sessionid)) {
            throw new HandleIncorrectAuthentification("Non Authentifie");
        }
        List<Medecin> demandes = medecinServices.findAllMedecinDemand();
        List<Map<String, Object>> mesDemandes = new ArrayList<>();
        for (Medecin medecin : demandes) {
            Map<String, Object> demande = new HashMap<>();
            demande.put("id", medecin.getMedecinId());
            demande.put("nom", medecin.getNom());
            demande.put("prenom", medecin.getPrenom());
            demande.put("specialite", specialiteService.getMedecinSpecialites(medecin.getSpecialites()));
            demande.put("login", medecin.getMedLogin());
            demande.put("adresscabinet", medecin.getAdressCabinet());
            demande.put("codeOrdreMedecin", medecin.getCodeOrdreMedecin());
            mesDemandes.add(demande);
        }
        return mesDemandes;
    }

    @Override
    public ResponseEntity<Object> chercherPatient(String sessionid, PatientToSearch patient)
            throws UserNotFoundException, HandleIncorrectAuthentification {
        if (!verifierAuthentification.verifyAuthentificationAdmin(sessionid)) {
            throw new HandleIncorrectAuthentification("Non Authentifie");
        }
        //puis on commence la recherche ville nom et prenom
        List<Patient> patients = patientServices.trouverParPrenomEtNom(patient.getPrenom().toLowerCase(), patient.getNom().toLowerCase());
        if (!patients.isEmpty()) {
            List<Map<String, Object>> patientstrouves = new ArrayList<>();
            for (Patient patientfound : patients) {
                Map<String, Object> patient_json = new HashMap<>();
                patient_json.put("id", patientfound.getPatientId());
                patient_json.put("email", patientfound.getPatientLogin());
                patient_json.put("nom", patientfound.getNom());
                patient_json.put("prenom", patientfound.getPrenom());
                patient_json.put("status", patientfound.getAutorisation());
                patient_json.put("date_de_naissance", patientfound.getDateDeNaissance());
                patientstrouves.add(patient_json);
            }
            return ResponseEntity.status(HttpStatus.OK).body(patientstrouves);
        }
        throw new UserNotFoundException("medecin non trouvé");
    }

    @Transactional
    @Override
    public ResponseEntity<Object> bloquerPatient(String sessionid,Long patientId)
            throws HandleIncorrectAuthentification, UserNotFoundException {
        if (!verifierAuthentification.verifyAuthentificationAdmin(sessionid)) {
            throw new HandleIncorrectAuthentification("Non Authentifie");
        }
        Optional<Patient> optionalPatient = patientServices.findById(patientId);
        if (optionalPatient.isEmpty()) {
            throw new UserNotFoundException("patient n'existe pas");
        } else {
            Patient patientFound = optionalPatient.get();
            patientFound.setAutorisation(Autorisation.NonAutoriser);
            List<RendezVous> listDesRdv = patientFound.getMesrendezvous();
            List<RendezVous> rdvModifier = new ArrayList<>();
            for (RendezVous rdv : listDesRdv) {
                rdvService.reporterRdv(rdv); // les rdv reportés sont ceux qui avait déja été accepter
                rdvService.supprimerRdv(rdv);
                rdvModifier.add(rdv);// on supprime les rdv qui étaient en attente en donnant leur status la valeur supprimer
            }
            patientFound.setMesrendezvous(rdvModifier);
            patientServices.save(patientFound);
            return ResponseEntity.status(HttpStatus.OK).body("bloquer");
        }
    }


    @Override
    public ResponseEntity<String> bloquerMedecin(String sessionid, Long medecinId)
            throws UserNotFoundException, HandleIncorrectAuthentification {
        //on authenfie d'abord l'admin
        if (!verifierAuthentification.verifyAuthentificationAdmin(sessionid)) {
            throw new HandleIncorrectAuthentification("Non Authentifie");
        }
        Optional<Medecin> optionalMedecin = medecinServices.findById(medecinId);
        if (optionalMedecin.isEmpty()) {
            throw new UserNotFoundException("Medecin n'existe pas");
        } else {
            Medecin medecinFound = optionalMedecin.get();
            medecinFound.setAutorisation(Autorisation.NonAutoriser);
            List<RendezVous> listDesRdv = medecinFound.getMesrendezvous();
            List<RendezVous> rdvModifier = new ArrayList<>();
            for (RendezVous rdv : listDesRdv) {
                rdvService.reporterRdv(rdv);
                rdvService.supprimerRdv(rdv);
                rdvModifier.add(rdv);
            }
            medecinFound.setMesrendezvous(rdvModifier);
            medecinServices.save(medecinFound);
            return ResponseEntity.status(HttpStatus.OK).body("bloquer");
        }
    }


    @Override
    public ResponseEntity<String> authentifierUser(User user)
            throws HandleIncorrectAuthentification, UserNotFoundException {
        Optional<Admin> optionalAdmin = adminRepository.findBylogin(user.getLogin());
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            String password = admin.getPassword();
            if (!HassingAndMatchingTester.passwordMatching(password, user.getPassword())) {
                throw new HandleIncorrectAuthentification("données invalide!");
            }
            String sessionId = authentificationService.creerSessionIdPourAdmin(admin.getLogin());
            admin.setSessionId(sessionId);
            try {
                adminRepository.save(admin);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseEntity.badRequest().body("ERROR");
            }
            return ResponseEntity.ok(sessionId);
        } else {
            throw new UserNotFoundException("email inexistant!");
        }
    }


    @Transactional
    @Override
    public ResponseEntity<Object> debloquerPatient(String sessionid, Long patientId)
            throws HandleIncorrectAuthentification, UserNotFoundException, RendezVousNotFound {
        if (!verifierAuthentification.verifyAuthentificationAdmin(sessionid)) {
            throw new HandleIncorrectAuthentification("Non Authentifie");
        }
        Optional<Patient> optionalPatient = patientServices.findById(patientId);
        if (optionalPatient.isEmpty()) {
            throw new UserNotFoundException("patient n'existe pas");
        } else {
            Patient patientFound = optionalPatient.get();
            patientFound.setAutorisation(Autorisation.Autoriser);
            List<RendezVous> listDesRdv = patientFound.getMesrendezvous();
            List<RendezVous> rdvModifier = new ArrayList<>();
            for (RendezVous rdv : listDesRdv) {
                rdvService.actualiserRdv(rdv); // on essai de voir si le date de rendezvous ou de demande ne sont pas
                rdvModifier.add(rdv);         // depassées et on active ou desactive les etats de demandes et status de rendezvous
            }
            rdvService.save(rdvModifier);
            patientFound.setMesrendezvous(rdvModifier);
            patientServices.save(patientFound);
            return ResponseEntity.status(HttpStatus.OK).body("debloquer");
        }
    }
}