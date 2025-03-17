package com.mycalendar.evenements;

import com.mycalendar.valueobjects.*;

/**
 * Classe représentant une réunion
 */
public class Reunion extends EvenementAbstrait {
    
    /**
     * Constructeur pour une réunion
     * 
     * @param titre Titre de la réunion
     * @param proprietaire Propriétaire de la réunion
     * @param date Date de la réunion
     * @param heureDebut Heure de début
     * @param duree Durée de la réunion
     * @param lieu Lieu de la réunion
     * @param participants Participants à la réunion
     */
    public Reunion(TitreEvenement titre, Utilisateur proprietaire,
                 DateEvenement date, HeureDebut heureDebut, DureeEvenement duree,
                 LieuEvenement lieu, ParticipantsEvenement participants) {
        super(
            titre, 
            proprietaire, 
            date, 
            heureDebut, 
            duree, 
            lieu, 
            participants, 
            FrequenceEvenement.NON_PERIODIQUE // Une réunion n'est pas périodique
        );
    }
    
    @Override
    public TypeEvenement getType() {
        return TypeEvenement.REUNION;
    }
    
    @Override
    public String description() {
        StringBuilder desc = new StringBuilder();
        desc.append("Réunion : ").append(getTitre());
        
        if (!getLieu().estVide()) {
            desc.append(" à ").append(getLieu());
        }
        
        if (!getParticipants().estVide()) {
            desc.append(" avec ").append(getParticipants());
        }
        
        return desc.toString();
    }
}