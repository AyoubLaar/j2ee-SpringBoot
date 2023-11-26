package com.example.projetj2E.services;

import com.example.projetj2E.entites.Medecin;
import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.entites.RendezVous;
import com.example.projetj2E.entites.StatusRdv;
import com.example.projetj2E.erreur.RendezVousNotFound;
import com.example.projetj2E.models.RdvModel;
import com.example.projetj2E.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class RdvServiceImpl implements RdvService {

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Override
    public void accepterRdv(RendezVous rdv) {
        rdv.setStatusRdv(StatusRdv.Accepter);
        rendezVousRepository.save(rdv);
    }

    @Override
    public void rejeterRdv(RendezVous rdv) {
        rdv.setStatusRdv(StatusRdv.Rejeter);
        rendezVousRepository.save(rdv);
    }

    @Transactional
    @Override
    public void rejectAllTheSameTimRdvWith(RendezVous rdv) {
        List<RendezVous> listRdvDuMedecin = rendezVousRepository.findAllByMedecin(rdv.getMedecin());
        for (RendezVous rdvDuMedecin : listRdvDuMedecin) {
            if (rdvDuMedecin.getStatusRdv().equals(StatusRdv.Attente) &&
                    rdvDuMedecin.getDateRdv().equals(rdv.getDateRdv()) &&
                    rdvDuMedecin.getHeureRdv().equals(rdv.getHeureRdv())
            ) rejeterRdv(rdv);
        }
    }


    @Override
    public void reporterRdv(RendezVous rdv) {
        if (rdv.getStatusRdv().equals(StatusRdv.Accepter)) {
            rdv.setStatusRdv(StatusRdv.Annuler);
        }
    }

    @Override
    public void supprimerRdv(RendezVous rdv) {
            rdv.setStatusRdv(StatusRdv.Supprimer);

    }

    @Override
    public Map<LocalDate, List<Long>> findUnavailabilityForMedecin(Medecin medecin) {
        List<RendezVous> rdvDuMedecin = medecin.getMesrendezvous();
        Map<LocalDate,List<Long>> unavailableRdv = new HashMap<>();
        for (RendezVous rdv : rdvDuMedecin) {
            LocalDate date = rdv.getDateRdv();
            Long heure = (long) rdv.getHeureRdv().getHour();
            if(unavailableRdv.containsKey(date)){
                unavailableRdv.get(date).add(heure);
            }else{
                List<Long> liste_heures = new ArrayList<>();
                liste_heures.add(heure);
                unavailableRdv.put(date,liste_heures);
            }
        }
        return unavailableRdv;
    }
    @Transactional
    @Override
    public void actualiserRdv(RendezVous rdv) throws RendezVousNotFound {
        if ( (rdv.getStatusRdv().equals(StatusRdv.Annuler)||
                (rdv.getStatusRdv().equals(StatusRdv.Supprimer)) &&
                        rdv.getDateRdv().isAfter(LocalDate.now()))) {
            List<Long> heuresindispo = findUnavailabilityForMedecin(rdv.getMedecin()).get(rdv.getDateRdv());
            if (heuresindispo.contains( rdv.getHeureRdv())) {
                rdv.setStatusRdv(StatusRdv.Supprimer);
                throw  new RendezVousNotFound("!!!veillez choisir un autre rdv");
            } else {
                rdv.setStatusRdv(StatusRdv.Attente);
            }
        }
    }

    @Override
    public void save(List<RendezVous> rdv) {
        rendezVousRepository.saveAll(rdv);
    }

    @Override
    public boolean VerifyPatientHaveRdvAtSameTime(Patient patient,RdvModel rdvModel) {
         boolean aUnRdv=false;
         List<RendezVous> lisrdv=patient.getMesrendezvous();
         for (RendezVous rdv :lisrdv){
              if(rdvModel.getDateRdv().equals(rdv.getDateRdv())&&
                      rdv.getHeureRdv().equals(
                              LocalTime.of(Integer.parseInt(rdvModel.getHeureRdv()),0))
              ){
                 return true;
              }
         }return aUnRdv;
    }

    @Override
    public void annulerRdv(RendezVous rdv) {
        rdv.setStatusRdv(StatusRdv.Annuler);
        rendezVousRepository.save(rdv);
    }


    @Transactional
    @Override
    public void mettreAjourEtatRdv(RendezVous rdv) {
        if (rdv.getStatusRdv().equals(StatusRdv.Attente)) {
            if (rdv.getDateRdv().isBefore(LocalDate.now()) ||
                    (rdv.getDateRdv().isEqual(LocalDate.now()) && rdv.getHeureRdv().plusHours(1).isBefore(LocalTime.now()))) {
                rdv.setStatusRdv(StatusRdv.NonEffectuer);
                rendezVousRepository.save(rdv);
            }
        }
    }
}