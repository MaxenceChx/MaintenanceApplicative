package com.mycalendar.evenements;

import com.mycalendar.valueobjects.*;
import java.time.LocalDateTime;

/**
 * Interface représentant un événement dans le calendrier
 */
public interface Evenement {
    
    /**
     * Obtient l'identifiant unique de l'événement
     * 
     * @return Identifiant de l'événement
     */
    EventId getId();
    
    /**
     * Obtient le type de l'événement
     * 
     * @return Type d'événement
     */
    TypeEvenement getType();
    
    /**
     * Obtient le titre de l'événement
     * 
     * @return Titre de l'événement
     */
    TitreEvenement getTitre();
    
    /**
     * Obtient l'utilisateur propriétaire de l'événement
     * 
     * @return Utilisateur propriétaire
     */
    Utilisateur getProprietaire();
    
    /**
     * Obtient la date de l'événement
     * 
     * @return Date de l'événement
     */
    DateEvenement getDate();
    
    /**
     * Obtient l'heure de début de l'événement
     * 
     * @return Heure de début
     */
    HeureDebut getHeureDebut();
    
    /**
     * Obtient la durée de l'événement
     * 
     * @return Durée de l'événement
     */
    DureeEvenement getDuree();
    
    /**
     * Obtient le lieu de l'événement
     * 
     * @return Lieu de l'événement
     */
    LieuEvenement getLieu();
    
    /**
     * Obtient les participants à l'événement
     * 
     * @return Participants à l'événement
     */
    ParticipantsEvenement getParticipants();
    
    /**
     * Obtient la fréquence de l'événement
     * 
     * @return Fréquence de l'événement
     */
    FrequenceEvenement getFrequence();
    
    /**
     * Obtient la date et l'heure de début de l'événement
     * 
     * @return Date et heure de début
     */
    LocalDateTime getDateDebut();
    
    /**
     * Vérifie si cet événement est en conflit avec un autre événement
     * 
     * @param autreEvenement L'autre événement à vérifier
     * @return true s'il y a conflit, false sinon
     */
    boolean estEnConflitAvec(Evenement autreEvenement);
    
    /**
     * Détermine si l'événement a lieu pendant une période donnée
     * 
     * @param debut Début de la période
     * @param fin Fin de la période
     * @return true si l'événement a lieu pendant la période, false sinon
     */
    boolean aLieuPendant(LocalDateTime debut, LocalDateTime fin);
    
    /**
     * Génère une description de l'événement
     * 
     * @return Description formatée de l'événement
     */
    String description();
}