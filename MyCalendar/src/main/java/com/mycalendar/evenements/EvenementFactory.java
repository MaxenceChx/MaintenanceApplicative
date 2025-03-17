package com.mycalendar.evenements;

import com.mycalendar.valueobjects.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory pour créer les différents types d'événements
 * Utilise le polymorphisme pour éviter les blocs conditionnels explicites
 */
public class EvenementFactory {
    
    // Interface fonctionnelle pour les créateurs d'événements
    private interface CreateurEvenement {
        Evenement creer(EventId id, TitreEvenement titre, Utilisateur proprietaire,
                      DateEvenement date, HeureDebut heureDebut, DureeEvenement duree,
                      LieuEvenement lieu, ParticipantsEvenement participants, 
                      FrequenceEvenement frequence);
    }
    
    // Map des créateurs d'événements, indexés par le code du type
    private static final Map<String, CreateurEvenement> CREATEURS = new HashMap<>();
    
    // Initialisation statique des créateurs
    static {
        // Créateur pour les rendez-vous personnels
        CREATEURS.put(TypeEvenement.RDV_PERSONNEL.getCode(), (id, titre, proprietaire, date, heureDebut, duree, lieu, participants, frequence) -> 
            new RendezVousPersonnel(id, titre, proprietaire, date, heureDebut, duree)
        );
        
        // Créateur pour les réunions
        CREATEURS.put(TypeEvenement.REUNION.getCode(), (id, titre, proprietaire, date, heureDebut, duree, lieu, participants, frequence) -> 
            new Reunion(id, titre, proprietaire, date, heureDebut, duree, lieu, participants)
        );
        
        // Créateur pour les événements périodiques
        CREATEURS.put(TypeEvenement.PERIODIQUE.getCode(), (id, titre, proprietaire, date, heureDebut, duree, lieu, participants, frequence) -> 
            new EvenementPeriodique(id, titre, proprietaire, date, heureDebut, frequence)
        );
    }
    
    /**
     * Crée un événement du type approprié avec un ID généré automatiquement
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
        return creerEvenement(EventId.generate(), type, titre, proprietaire, date, heureDebut, duree, lieu, participants, frequence);
    }
    
    /**
     * Crée un événement du type approprié avec un ID spécifique
     * 
     * @param id Identifiant de l'événement
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
    public static Evenement creerEvenement(EventId id, TypeEvenement type, TitreEvenement titre, Utilisateur proprietaire,
                                         DateEvenement date, HeureDebut heureDebut, DureeEvenement duree,
                                         LieuEvenement lieu, ParticipantsEvenement participants, FrequenceEvenement frequence) {
        return creerEvenement(id, type.getCode(), titre, proprietaire, date, heureDebut, duree, lieu, participants, frequence);
    }
    
    /**
     * Crée un événement du type approprié à partir du code du type
     * 
     * @param id Identifiant de l'événement
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
     * @throws IllegalArgumentException si le type d'événement est inconnu
     */
    public static Evenement creerEvenement(EventId id, String typeCode, TitreEvenement titre, Utilisateur proprietaire,
                                         DateEvenement date, HeureDebut heureDebut, DureeEvenement duree,
                                         LieuEvenement lieu, ParticipantsEvenement participants, FrequenceEvenement frequence) {
        CreateurEvenement createur = CREATEURS.get(typeCode);
        
        if (createur == null) {
            throw new IllegalArgumentException("Type d'événement inconnu: " + typeCode);
        }
        
        return createur.creer(id, titre, proprietaire, date, heureDebut, duree, lieu, participants, frequence);
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
        return creerEvenement(EventId.generate(), typeCode, titre, proprietaire, date, heureDebut, duree, lieu, participants, frequence);
    }
    
    /**
     * Enregistre un nouveau type d'événement dans la factory
     * 
     * @param typeCode Code du type d'événement
     * @param createur Fonction de création pour ce type d'événement
     */
    public static void enregistrerType(String typeCode, CreateurEvenement createur) {
        CREATEURS.put(typeCode, createur);
    }
    
    // Constructeur privé pour empêcher l'instanciation
    private EvenementFactory() {
        throw new UnsupportedOperationException("Cette classe utilitaire ne doit pas être instanciée");
    }
}