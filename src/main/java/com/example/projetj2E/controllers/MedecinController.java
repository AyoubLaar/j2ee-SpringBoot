package com.example.projetj2E.controllers;


import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.erreur.GereExistEmailException;
import com.example.projetj2E.erreur.HandleIncorrectAuthentification;
import com.example.projetj2E.erreur.UserNotFoundException;
import com.example.projetj2E.models.MedecinModel;
import com.example.projetj2E.models.RdvModel;
import com.example.projetj2E.models.User;
import com.example.projetj2E.services.MedecinServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/medecin")
public class MedecinController {


    @Autowired
    private MedecinServices medecinServices;

    @PostMapping("/signup")
    public ResponseEntity<String> registerMedecin(@RequestBody MedecinModel medecinModel) throws GereExistEmailException {
        Medecin medecin = medecinServices.registerMedecin(medecinModel);
        return ResponseEntity.status(HttpStatus.OK).body("Succes");
    }

    @PostMapping("/signin")
    @CrossOrigin
    public String authentifierUser(@RequestBody User medecin) throws HandleIncorrectAuthentification {
        return medecinServices.authentifierUser(medecin);

    }
    @PostMapping("/dashboard/mesdemandes")
    public ResponseEntity<Object>  mesDemandeDeRdv(@RequestHeader("token") String sessionid) throws UserNotFoundException {
              return medecinServices.mesDemandeDeRdv(sessionid);
    }


 /*  @PostMapping("/mesdemandes/rdv")
   @CrossOrigin
    public ResponseEntity<String>  accepter(@RequestHeader("token") String sessionid,@RequestBody RdvModel rdvModel){

    }

*/
}
