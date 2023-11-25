package com.example.projetj2E.services;

import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.RendezVous;
import com.example.projetj2E.entites.StatusDemandeRdv;
import com.example.projetj2E.entites.StatusRdv;
import com.example.projetj2E.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RdvServiceImpl implements RdvService {

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Override
    public void accepterRdv(RendezVous rdv) {
        rdv.setStatusDemandeRdv(StatusDemandeRdv.Accepter);
        rendezVousRepository.save(rdv);
    }

    @Override
    public void rejeterRdv(RendezVous rdv) {
        rdv.setStatusDemandeRdv(StatusDemandeRdv.Rejeter);
        rdv.setStatusRdv(StatusRdv.Rejeter);
        rendezVousRepository.save(rdv);
    }

    @Transactional
    @Override
    public void rejectAllTheSameTimRdvWith(RendezVous rdv) {
        List<RendezVous> rendezVous = rendezVousRepository.findAllByMedecin(rdv.getMedecin());
        for (RendezVous rendezVs : rendezVous) {
            if (rendezVs.getStatusDemandeRdv().equals(StatusDemandeRdv.Attente) &&
                    rendezVs.getDateRdv().equals(rdv.getDateRdv()) &&
                    rendezVs.getHeureRdv().equals(rdv.getHeureRdv())
            ) rejeterRdv(rdv);
        }
    }

    @Override
    public boolean verifieValidRdv(RendezVous rdv) {
        return ((rdv.getStatusRdv().equals(StatusRdv.Attente) ||
                rdv.getStatusRdv().equals(StatusRdv.reporter)) &&
                rdv.getStatusDemandeRdv().equals(StatusDemandeRdv.Accepter));
    }

    @Override
    public void reporterRdv(RendezVous rdv) {
        if (rdv.getStatusDemandeRdv().equals(StatusDemandeRdv.Accepter) &&
                (rdv.getStatusRdv().equals(StatusRdv.Attente))
        ) {
            rdv.setStatusRdv(StatusRdv.reporter);

        }
    }

    @Override
    public void supprimerRdv(RendezVous rdv) {
        if (rdv.getStatusDemandeRdv().equals(StatusDemandeRdv.Attente)) {
            rdv.setStatusDemandeRdv(StatusDemandeRdv.Supprimer);
        }
    }

    @Override
    public List<LocalTime> findUnavailabilityForMedecin(Medecin medecin) {
        List<RendezVous> rdvDuMedecin = medecin.getMesrendezvous();
        List<LocalTime> unavailableRdv = new ArrayList<>();
        for (RendezVous rdv : rdvDuMedecin) {
            if (rdv.getStatusDemandeRdv().equals(StatusDemandeRdv.Accepter)) {
                unavailableRdv.add(rdv.getHeureRdv());
            }
        }
        return unavailableRdv;
    }
}