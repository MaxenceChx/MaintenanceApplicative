package com.mycalendar.evenements;

import com.mycalendar.valueobjects.*;
import java.time.LocalDateTime;

/**
 * Classe abstraite fournissant l'implémentation commune à tous les types d'événements
 */
public abstract class EvenementAbstrait implements Evenement {
    
    private final EventId id;
    private final TitreEvenement titre;
    private final Utilisateur proprietaire;
    private final DateEvenement date;
    private final HeureDebut heureDebut;
    private final DureeEvenement duree;
    private final LieuEvenement lieu;
    private final ParticipantsEvenement participants;
    private final FrequenceEvenement frequence;
    
    /**
     * Constructeur pour un événement abstrait
     * 
     * @param id Identifiant unique de l'événement
     * @param titre Titre de l'événement
     * @param proprietaire Propriétaire de l'événement
     * @param date Date de l'événement
     * @param heureDebut Heure de début
     * @param duree Durée de l'événement
     * @param lieu Lieu de l'événement
     * @param participants Participants à l'événement
     * @param frequence Fréquence de l'événement
     */
    protected EvenementAbstrait(EventId id, TitreEvenement titre, Utilisateur proprietaire,
                              DateEvenement date, HeureDebut heureDebut, DureeEvenement duree,
                              LieuEvenement lieu, ParticipantsEvenement participants, FrequenceEvenement frequence) {
        this.id = (id != null) ? id : EventId.generate();
        this.titre = titre;
        this.proprietaire = proprietaire;
        this.date = date;
        this.heureDebut = heureDebut;
        this.duree = duree;
        this.lieu = lieu;
        this.participants = participants;
        this.frequence = frequence;
    }
    
    @Override
    public EventId getId() {
        return id;
    }
    
    @Override
    public TitreEvenement getTitre() {
        return titre;
    }
    
    @Override
    public Utilisateur getProprietaire() {
        return proprietaire;
    }
    
    @Override
    public DateEvenement getDate() {
        return date;
    }
    
    @Override
    public HeureDebut getHeureDebut() {
        return heureDebut;
    }
    
    @Override
    public DureeEvenement getDuree() {
        return duree;
    }
    
    @Override
    public LieuEvenement getLieu() {
        return lieu;
    }
    
    @Override
    public ParticipantsEvenement getParticipants() {
        return participants;
    }
    
    @Override
    public FrequenceEvenement getFrequence() {
        return frequence;
    }
    
    @Override
    public LocalDateTime getDateDebut() {
        return LocalDateTime.of(
            date.toLocalDate(),
            heureDebut.toLocalTime()
        );
    }
    
    @Override
    public boolean estEnConflitAvec(Evenement autreEvenement) {
        // L'implémentation par défaut est que deux événements sont en conflit
        // s'ils se chevauchent temporellement
        LocalDateTime fin = getDateDebut().plusMinutes(getDuree().getMinutes());
        LocalDateTime autreDebut = autreEvenement.getDateDebut();
        LocalDateTime autreFin = autreDebut.plusMinutes(autreEvenement.getDuree().getMinutes());
        
        return getDateDebut().isBefore(autreFin) && fin.isAfter(autreDebut);
    }
    
    @Override
    public boolean aLieuPendant(LocalDateTime debut, LocalDateTime fin) {
        // L'implémentation par défaut est qu'un événement a lieu pendant une période
        // si sa date de début est comprise dans cette période
        return !getDateDebut().isBefore(debut) && !getDateDebut().isAfter(fin);
    }
    
    /**
     * Méthode utilitaire pour obtenir le type sous forme de chaîne
     * 
     * @return Code du type d'événement
     */
    public String getTypeString() {
        return getType().getCode();
    }
    
    /**
     * Méthode utilitaire pour obtenir l'identifiant du propriétaire
     * 
     * @return Identifiant du propriétaire
     */
    public String getProprietaireString() {
        return proprietaire.getIdentifiant();
    }
    
    /**
     * Méthode utilitaire pour obtenir la durée en minutes
     * 
     * @return Durée en minutes
     */
    public int getDureeMinutes() {
        return duree.getMinutes();
    }
    
    /**
     * Méthode utilitaire pour obtenir le titre sous forme de chaîne
     * 
     * @return Titre sous forme de chaîne
     */
    public String getTitreString() {
        return titre.getValeur();
    }
    
    /**
     * Méthode utilitaire pour obtenir le lieu sous forme de chaîne
     * 
     * @return Lieu sous forme de chaîne
     */
    public String getLieuString() {
        return lieu.getValeur();
    }
    
    /**
     * Méthode utilitaire pour obtenir les participants sous forme de chaîne
     * 
     * @return Participants sous forme de chaîne
     */
    public String getParticipantsString() {
        return participants.toString();
    }
    
    /**
     * Méthode utilitaire pour obtenir la fréquence en jours
     * 
     * @return Fréquence en jours
     */
    public int getFrequenceJours() {
        return frequence.getJoursEntrePeriodes();
    }
}