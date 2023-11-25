package com.example.projetj2E.services;

import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.RendezVous;
import com.example.projetj2E.erreur.RendezVousNotFound;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    void mettreAjourEtatRdv(RendezVous rdv);

    void actualiserRdv(RendezVous rdv) throws RendezVousNotFound;

    void save(List<RendezVous> rdvModifier);
}