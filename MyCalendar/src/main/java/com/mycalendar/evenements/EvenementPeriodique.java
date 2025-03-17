package com.mycalendar.evenements;

import com.mycalendar.valueobjects.*;
import java.time.LocalDateTime;

/**
 * Classe représentant un événement périodique
 */
public class EvenementPeriodique extends EvenementAbstrait {
    
    /**
     * Constructeur pour un événement périodique avec un identifiant spécifique
     * 
     * @param id Identifiant unique de l'événement
     * @param titre Titre de l'événement
     * @param proprietaire Propriétaire de l'événement
     * @param date Date de première occurrence
     * @param heureDebut Heure de début
     * @param frequence Fréquence de répétition
     */
    public EvenementPeriodique(EventId id, TitreEvenement titre, Utilisateur proprietaire,
                             DateEvenement date, HeureDebut heureDebut, FrequenceEvenement frequence) {
        super(
            id, 
            titre, 
            proprietaire, 
            date, 
            heureDebut, 
            new DureeEvenement(0), // La durée n'est pas utilisée pour les événements périodiques
            new LieuEvenement(""), // Pas de lieu pour un événement périodique
            ParticipantsEvenement.avecUtilisateurs(java.util.Collections.emptyList()), // Pas de participants pour un événement périodique
            frequence
        );
        
        if (!frequence.estPeriodique()) {
            throw new IllegalArgumentException("Un événement périodique doit avoir une fréquence périodique");
        }
    }
    
    /**
     * Constructeur pour un événement périodique avec un identifiant généré automatiquement
     * 
     * @param titre Titre de l'événement
     * @param proprietaire Propriétaire de l'événement
     * @param date Date de première occurrence
     * @param heureDebut Heure de début
     * @param frequence Fréquence de répétition
     */
    public EvenementPeriodique(TitreEvenement titre, Utilisateur proprietaire,
                             DateEvenement date, HeureDebut heureDebut, FrequenceEvenement frequence) {
        this(EventId.generate(), titre, proprietaire, date, heureDebut, frequence);
    }
    
    @Override
    public TypeEvenement getType() {
        return TypeEvenement.PERIODIQUE;
    }
    
    @Override
    public boolean estEnConflitAvec(Evenement autreEvenement) {
        // Les événements périodiques ne sont jamais en conflit
        return false;
    }
    
    @Override
    public boolean aLieuPendant(LocalDateTime debut, LocalDateTime fin) {
        // Un événement périodique a lieu pendant une période si au moins une
        // de ses occurrences est comprise dans cette période
        LocalDateTime occurrence = getDateDebut();
        
        while (occurrence.isBefore(fin)) {
            if (!occurrence.isBefore(debut)) {
                return true;
            }
            occurrence = occurrence.plusDays(getFrequence().getJoursEntrePeriodes());
        }
        
        return false;
    }
    
    @Override
    public String description() {
        return String.format(
            "Événement périodique : %s (%s)",
            getTitre(),
            getFrequence()
        );
    }
}