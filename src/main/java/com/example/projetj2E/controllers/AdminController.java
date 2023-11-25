package com.example.projetj2E.controllers;
import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.*;
import com.example.projetj2E.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/signup")
    @CrossOrigin
    public ResponseEntity<String> saveAdmin(@RequestBody User user)
             {
        return  adminService.saveAdmin(user);

    }
    @PostMapping("/signin")
    @CrossOrigin
    public ResponseEntity<String> authentifierUser(@RequestBody User admin)
            throws HandleIncorrectAuthentification, UserNotFoundException {
        return adminService.authentifierUser(admin);

    }
    @PostMapping("dashboard/recherche/medecin")
    @CrossOrigin
    public ResponseEntity<Object> chercherMedecin(@RequestHeader("token") String sessionid,
                                                  @RequestBody MedecinToSearch medecin)
            throws UserNotFoundException, HandleIncorrectAuthentification {
        return adminService.chercherMedecin(sessionid,medecin);
    }
    @PostMapping("dashboard/recherche/patient")
    @CrossOrigin
    public ResponseEntity<Object> chercherPatient(@RequestHeader("token") String sessionid,
                                                  @RequestBody PatientToSearch patient )
            throws UserNotFoundException, HandleIncorrectAuthentification {
        return adminService.chercherPatient(sessionid,patient);
    }
    @PutMapping("dashboard/recherche/patient")
    @CrossOrigin
    public ResponseEntity<Object> bloquerPatient(@RequestHeader("token") String sessionid,
                                                   @RequestBody PatientId patientId )
            throws UserNotFoundException, HandleIncorrectAuthentification {
        return adminService.bloquerPatient(sessionid,patientId);
    }

    @PutMapping("/dashboard/recherche/medecin")
    @CrossOrigin
    public ResponseEntity<String> bloquerMedecin(@RequestHeader("token") String sessionid,
                                                   @RequestBody MedecinId medecinId)
            throws UserNotFoundException, HandleIncorrectAuthentification {
        return adminService.bloquerMedecin(sessionid,medecinId);
    }

    @PutMapping("/dashboard/demands")
    @CrossOrigin
    public ResponseEntity<String> accepterOrRejectMedecin(@RequestHeader("token") String sessionid,
                                                          @RequestBody AcceptOrReject medecin)
            throws UserNotFoundException, HandleIncorrectAuthentification {
        return adminService.accepterOrRejectMedecin(sessionid,medecin);
    }
    @GetMapping("/dashboard/demands")
    @CrossOrigin
    public List<Map<String,Object>> medecinDemand(@RequestHeader("token") String sessionid)
            throws UserNotFoundException, HandleIncorrectAuthentification {
          return adminService.medecinDemand(sessionid);
    }


}
