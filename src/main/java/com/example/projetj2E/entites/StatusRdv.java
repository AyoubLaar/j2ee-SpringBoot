package com.example.projetj2E.entites;

public enum StatusRdv {
    Attente,Effectuer,Rejeter,Reporter,Annuler,NonEffectuer

    /*  le rdv peut être en Attente car pas encore fait
         le rdv peut etre deja effectuer
         le rdv peut etre rejeter par le medecin
         le rdv peut etre reporte suite au suspension du compte du medecin ou patient par l'admin
         le rdv peut etre annuler soit par le medecin ou le patient

     */
}
