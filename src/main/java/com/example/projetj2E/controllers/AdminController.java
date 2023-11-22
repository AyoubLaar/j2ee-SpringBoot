package com.example.projetj2E.controllers;

import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.*;
import com.example.projetj2E.services.AdminServiceImpl;
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
    private AdminServiceImpl adminService;

    @PostMapping("/signup")
    public ResponseEntity<String> saveAdmin(@RequestBody User user) throws GereExistEmailException {
        return  adminService.saveAdmin(user);

    }

    @PostMapping("/signin")
    @CrossOrigin
    public ResponseEntity<String> authentifierUser(@RequestBody User admin) throws HandleIncorrectAuthentification, UserNotFoundException {
        return adminService.authentifierUser(admin);

    }

    @PostMapping("dashboard/recherche")
    public ResponseEntity<Object> chercherMedecin(@RequestHeader("token") String sessionid,@RequestBody MedecinToDelete medecinToDelete) throws UserNotFoundException, HandleIncorrectAuthentification {
        return adminService.chercherMedecin(sessionid,medecinToDelete);
    }

    @PutMapping("/dashboard/recherche")
    public ResponseEntity<String> supprimerMedecin(@RequestHeader("token") String sessionid, @RequestBody MedecinToDelete medecinToDelete) throws UserNotFoundException, HandleIncorrectAuthentification {
        return adminService.supprimerMedecin(sessionid,medecinToDelete);
    }


    @PutMapping("/dashboard/demands")
    public ResponseEntity<String> accepterOrRejectMedecin(@RequestHeader("token") String sessionid, @RequestBody AcceptOrReject medecin) throws UserNotFoundException, HandleIncorrectAuthentification {
        return adminService.accepterOrRejectMedecin(sessionid,medecin);
    }

    @GetMapping("/dashboard/demands")
    public List<Map<String,Object>> medecinDemand(@RequestHeader("token") String sessionid) throws UserNotFoundException, HandleIncorrectAuthentification {
          return adminService.medecinDemand(sessionid);
    }
}
