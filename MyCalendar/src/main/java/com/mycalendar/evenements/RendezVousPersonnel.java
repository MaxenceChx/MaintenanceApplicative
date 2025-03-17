package com.mycalendar.evenements;

import com.mycalendar.valueobjects.*;

/**
 * Classe représentant un rendez-vous personnel
 */
public class RendezVousPersonnel extends EvenementAbstrait {
    
    /**
     * Constructeur pour un rendez-vous personnel avec un identifiant spécifique
     * 
     * @param id Identifiant unique du rendez-vous
     * @param titre Titre du rendez-vous
     * @param proprietaire Propriétaire du rendez-vous
     * @param date Date du rendez-vous
     * @param heureDebut Heure de début
     * @param duree Durée du rendez-vous
     */
    public RendezVousPersonnel(EventId id, TitreEvenement titre, Utilisateur proprietaire,
                             DateEvenement date, HeureDebut heureDebut, DureeEvenement duree) {
        super(
            id, 
            titre, 
            proprietaire, 
            date, 
            heureDebut, 
            duree, 
            new LieuEvenement(""), // Pas de lieu pour un rendez-vous personnel
            ParticipantsEvenement.avecUtilisateurs(java.util.Collections.emptyList()), // Pas de participants pour un rendez-vous personnel
            FrequenceEvenement.NON_PERIODIQUE // Un rendez-vous personnel n'est pas périodique
        );
    }
    
    /**
     * Constructeur pour un rendez-vous personnel avec un identifiant généré automatiquement
     * 
     * @param titre Titre du rendez-vous
     * @param proprietaire Propriétaire du rendez-vous
     * @param date Date du rendez-vous
     * @param heureDebut Heure de début
     * @param duree Durée du rendez-vous
     */
    public RendezVousPersonnel(TitreEvenement titre, Utilisateur proprietaire,
                             DateEvenement date, HeureDebut heureDebut, DureeEvenement duree) {
        this(EventId.generate(), titre, proprietaire, date, heureDebut, duree);
    }
    
    @Override
    public TypeEvenement getType() {
        return TypeEvenement.RDV_PERSONNEL;
    }
    
    @Override
    public String description() {
        return String.format(
            "RDV : %s le %s à %s",
            getTitre(),
            getDate(),
            getHeureDebut()
        );
    }
}