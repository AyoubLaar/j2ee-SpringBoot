package com.example.projetj2E.services;

import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.RendezVous;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface RdvService {

    void accepterRdv(RendezVous rdv);

    void rejeterRdv(RendezVous rdv);

    void rejectAllTheSameTimRdvWith(RendezVous rdv);

    boolean verifieValidRdv(RendezVous rdv);

    void reporterRdv(RendezVous rdv);

    void supprimerRdv(RendezVous rdv);

    Map<LocalDate, List<Long>> findUnavailabilityForMedecin(Medecin medecin);
}