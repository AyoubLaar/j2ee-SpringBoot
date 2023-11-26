package com.example.projetj2E.services;

import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.entites.RendezVous;
import com.example.projetj2E.erreur.RendezVousNotFound;
import com.example.projetj2E.models.RdvModel;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface RdvService {

    void accepterRdv(RendezVous rdv);

    void rejeterRdv(RendezVous rdv);

    void rejectAllTheSameTimRdvWith(RendezVous rdv);

    void reporterRdv(RendezVous rdv);

    void supprimerRdv(RendezVous rdv);

    Map<LocalDate, List<Long>> findUnavailabilityForMedecin(Medecin medecin);

    @Transactional
    void mettreAjourEtatRdv(RendezVous rdv);

    void actualiserRdv(RendezVous rdv) throws RendezVousNotFound;

    void save(List<RendezVous> rdvModifier);


   boolean VerifyPatientHaveRdvAtSameTime(Patient patient,RdvModel rdvModel);

    void annulerRdv(RendezVous rdvFound);
}