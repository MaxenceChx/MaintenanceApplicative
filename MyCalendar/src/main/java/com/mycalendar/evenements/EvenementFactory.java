package com.mycalendar.evenements;

import com.mycalendar.valueobjects.*;
import java.time.LocalDateTime;

/**
 * Factory pour créer les différents types d'événements
 */
public class EvenementFactory {
    
    /**
     * Crée un événement du type approprié
     * 
     * @param type Type d'événement
     * @param titre Titre de l'événement
     * @param proprietaire Propriétaire de l'événement
     * @param date Date de l'événement
     * @param heureDebut Heure de début
     * @param duree Durée de l'événement
     * @param lieu Lieu de l'événement
     * @param participants Participants à l'événement
     * @param frequence Fréquence pour événement périodique
     * @return L'événement créé
     */
    public static Evenement creerEvenement(TypeEvenement type, TitreEvenement titre, Utilisateur proprietaire,
                                         DateEvenement date, HeureDebut heureDebut, DureeEvenement duree,
                                         LieuEvenement lieu, ParticipantsEvenement participants, FrequenceEvenement frequence) {
        return creerEvenement(type.getCode(), titre, proprietaire, date, heureDebut, duree, lieu, participants, frequence);
    }
    
    /**
     * Crée un événement du type approprié à partir du code du type
     * 
     * @param typeCode Code du type d'événement
     * @param titre Titre de l'événement
     * @param proprietaire Propriétaire de l'événement
     * @param date Date de l'événement
     * @param heureDebut Heure de début
     * @param duree Durée de l'événement
     * @param lieu Lieu de l'événement
     * @param participants Participants à l'événement
     * @param frequence Fréquence pour événement périodique
     * @return L'événement créé
     */
    public static Evenement creerEvenement(String typeCode, TitreEvenement titre, Utilisateur proprietaire,
                                         DateEvenement date, HeureDebut heureDebut, DureeEvenement duree,
                                         LieuEvenement lieu, ParticipantsEvenement participants, FrequenceEvenement frequence) {
        TypeEvenement type = TypeEvenement.fromString(typeCode);
        
        return creerEvenementSelonType(type, titre, proprietaire, date, heureDebut, duree, lieu, participants, frequence);
    }
    
    /**
     * Crée un événement du type approprié
     * 
     * @param type Type d'événement
     * @param titre Titre de l'événement
     * @param proprietaire Propriétaire de l'événement
     * @param date Date de l'événement
     * @param heureDebut Heure de début
     * @param duree Durée de l'événement
     * @param lieu Lieu de l'événement
     * @param participants Participants à l'événement
     * @param frequence Fréquence pour événement périodique
     * @return L'événement créé
     */
    private static Evenement creerEvenementSelonType(TypeEvenement type, TitreEvenement titre, Utilisateur proprietaire,
                                                  DateEvenement date, HeureDebut heureDebut, DureeEvenement duree,
                                                  LieuEvenement lieu, ParticipantsEvenement participants, FrequenceEvenement frequence) {
        switch (type) {
            case RDV_PERSONNEL:
                return new RendezVousPersonnel(titre, proprietaire, date, heureDebut, duree);
                
            case REUNION:
                return new Reunion(titre, proprietaire, date, heureDebut, duree, lieu, participants);
                
            case PERIODIQUE:
                return new EvenementPeriodique(titre, proprietaire, date, heureDebut, frequence);
                
            default:
                throw new IllegalArgumentException("Type d'événement non pris en charge : " + type);
        }
    }
    
    /**
     * Méthode de compatibilité pour créer un événement à partir des données "anciennes"
     * 
     * @param typeStr Type d'événement (chaîne)
     * @param title Titre de l'événement
     * @param proprietaireStr Identifiant du propriétaire
     * @param dateDebut Date et heure de début
     * @param dureeMinutes Durée en minutes
     * @param lieu Lieu de l'événement
     * @param participants Participants séparés par des virgules
     * @param frequenceJours Fréquence en jours
     * @return L'événement créé
     */
    public static Evenement creerEvenement(String typeStr, String title, String proprietaireStr,
                                         LocalDateTime dateDebut, int dureeMinutes,
                                         String lieu, String participants, int frequenceJours) {
        return creerEvenement(
            typeStr,
            new TitreEvenement(title),
            new Utilisateur(proprietaireStr, "—PLACEHOLDER—"), // Mot de passe placeholder
            DateEvenement.fromLocalDate(dateDebut.toLocalDate()),
            HeureDebut.fromLocalTime(dateDebut.toLocalTime()),
            new DureeEvenement(dureeMinutes),
            new LieuEvenement(lieu),
            ParticipantsEvenement.fromString(participants),
            new FrequenceEvenement(frequenceJours)
        );
    }
    
    /**
     * Méthode de compatibilité pour créer un événement à partir des données "anciennes"
     * avec un utilisateur déjà créé
     * 
     * @param typeStr Type d'événement (chaîne)
     * @param title Titre de l'événement
     * @param proprietaire Propriétaire de l'événement (déjà authentifié)
     * @param dateDebut Date et heure de début
     * @param dureeMinutes Durée en minutes
     * @param lieu Lieu de l'événement
     * @param participants Participants séparés par des virgules
     * @param frequenceJours Fréquence en jours
     * @return L'événement créé
     */
    public static Evenement creerEvenement(String typeStr, String title, Utilisateur proprietaire,
                                         LocalDateTime dateDebut, int dureeMinutes,
                                         String lieu, String participants, int frequenceJours) {
        return creerEvenement(
            typeStr,
            new TitreEvenement(title),
            proprietaire,
            DateEvenement.fromLocalDate(dateDebut.toLocalDate()),
            HeureDebut.fromLocalTime(dateDebut.toLocalTime()),
            new DureeEvenement(dureeMinutes),
            new LieuEvenement(lieu),
            ParticipantsEvenement.fromString(participants),
            new FrequenceEvenement(frequenceJours)
        );
    }
}