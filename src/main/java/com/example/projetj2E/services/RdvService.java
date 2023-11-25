package com.example.projetj2E.services;

import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.RendezVous;
import com.example.projetj2E.entites.StatusDemandeRdv;
import com.example.projetj2E.entites.StatusRdv;

import java.time.LocalTime;
import java.util.List;

public interface RdvService {

    void accepterRdv(RendezVous rdv);

    void rejeterRdv(RendezVous rdv);

    void rejectAllTheSameTimRdvWith(RendezVous rdv);

    boolean verifieValidRdv(RendezVous rdv);

    void reporterRdv(RendezVous rdv);

    void supprimerRdv(RendezVous rdv);

    List<LocalTime> findUnavailabilityForMedecin(Medecin medecin);
}