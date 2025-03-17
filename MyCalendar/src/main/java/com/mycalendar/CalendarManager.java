package com.mycalendar;

import com.mycalendar.evenements.*;
import com.mycalendar.valueobjects.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gestionnaire de calendrier utilisant du polymorphisme pour le comportement variant
 */
public class CalendarManager {
    private final List<Evenement> evenements;

    /**
     * Constructeur du gestionnaire de calendrier
     */
    public CalendarManager() {
        this.evenements = new ArrayList<>();
    }

    /**
     * Ajoute un événement au calendrier
     * 
     * @param evenement L'événement à ajouter
     */
    public void ajouterEvenement(Evenement evenement) {
        evenements.add(evenement);
    }
    
    /**
     * Ajoute un rendez-vous personnel au calendrier
     * 
     * @param titre Titre du rendez-vous
     * @param proprietaire Propriétaire du rendez-vous
     * @param date Date du rendez-vous
     * @param heureDebut Heure de début
     * @param duree Durée du rendez-vous
     */
    public void ajouterRendezVousPersonnel(TitreEvenement titre, Utilisateur proprietaire, 
                                         DateEvenement date, HeureDebut heureDebut, DureeEvenement duree) {
        RendezVousPersonnel rdv = new RendezVousPersonnel(titre, proprietaire, date, heureDebut, duree);
        ajouterEvenement(rdv);
    }
    
    /**
     * Ajoute une réunion au calendrier
     * 
     * @param titre Titre de la réunion
     * @param proprietaire Propriétaire de la réunion
     * @param date Date de la réunion
     * @param heureDebut Heure de début
     * @param duree Durée de la réunion
     * @param lieu Lieu de la réunion
     * @param participants Participants à la réunion
     */
    public void ajouterReunion(TitreEvenement titre, Utilisateur proprietaire, 
                             DateEvenement date, HeureDebut heureDebut, DureeEvenement duree,
                             LieuEvenement lieu, ParticipantsEvenement participants) {
        Reunion reunion = new Reunion(titre, proprietaire, date, heureDebut, duree, lieu, participants);
        ajouterEvenement(reunion);
    }
    
    /**
     * Ajoute un événement périodique au calendrier
     * 
     * @param titre Titre de l'événement
     * @param proprietaire Propriétaire de l'événement
     * @param date Date de première occurrence
     * @param heureDebut Heure de début
     * @param frequence Fréquence de répétition
     */
    public void ajouterEvenementPeriodique(TitreEvenement titre, Utilisateur proprietaire, 
                                         DateEvenement date, HeureDebut heureDebut, FrequenceEvenement frequence) {
        EvenementPeriodique evenement = new EvenementPeriodique(titre, proprietaire, date, heureDebut, frequence);
        ajouterEvenement(evenement);
    }
    
    /**
     * Méthode de compatibilité pour ajouter un événement au calendrier
     * 
     * @param typeStr Type d'événement (chaîne)
     * @param title Titre de l'événement
     * @param proprietaire Utilisateur propriétaire (déjà authentifié)
     * @param dateDebut Date et heure de début
     * @param dureeMinutes Durée en minutes
     * @param lieu Lieu de l'événement
     * @param participants Participants séparés par des virgules
     * @param frequenceJours Fréquence en jours
     */
    public void ajouterEvent(String typeStr, String title, Utilisateur proprietaire, LocalDateTime dateDebut, int dureeMinutes,
                           String lieu, String participants, int frequenceJours) {
        Evenement evenement = EvenementFactory.creerEvenement(
            typeStr, title, proprietaire, dateDebut, dureeMinutes, lieu, participants, frequenceJours
        );
        ajouterEvenement(evenement);
    }
    
    /**
     * Méthode de compatibilité pour ajouter un événement au calendrier
     * 
     * @param typeStr Type d'événement (chaîne)
     * @param title Titre de l'événement
     * @param proprietaireStr Identifiant du propriétaire (chaîne)
     * @param dateDebut Date et heure de début
     * @param dureeMinutes Durée en minutes
     * @param lieu Lieu de l'événement
     * @param participants Participants séparés par des virgules
     * @param frequenceJours Fréquence en jours
     */
    public void ajouterEvent(String typeStr, String title, String proprietaireStr, LocalDateTime dateDebut, int dureeMinutes,
                            String lieu, String participants, int frequenceJours) {
        Evenement evenement = EvenementFactory.creerEvenement(
            typeStr, title, proprietaireStr, dateDebut, dureeMinutes, lieu, participants, frequenceJours
        );
        ajouterEvenement(evenement);
    }

    /**
     * Trouve les événements dans une période donnée
     * 
     * @param debut Début de la période
     * @param fin Fin de la période
     * @return Liste des événements dans la période
     */
    public List<Evenement> eventsDansPeriode(LocalDateTime debut, LocalDateTime fin) {
        return evenements.stream()
                .filter(e -> e.aLieuPendant(debut, fin))
                .collect(Collectors.toList());
    }

    /**
     * Vérifie s'il y a un conflit entre deux événements
     * 
     * @param e1 Premier événement
     * @param e2 Second événement
     * @return true s'il y a conflit, false sinon
     */
    public boolean conflit(Evenement e1, Evenement e2) {
        return e1.estEnConflitAvec(e2);
    }

    /**
     * Affiche tous les événements du calendrier
     */
    public void afficherEvenements() {
        for (Evenement e : evenements) {
            System.out.println(e.description());
        }
    }
    
    /**
     * Obtient la liste des événements
     * 
     * @return Liste des événements
     */
    public List<Evenement> getEvenements() {
        return new ArrayList<>(evenements);
    }
    
    /**
     * Filtre les événements par propriétaire
     * 
     * @param utilisateur Utilisateur propriétaire des événements à rechercher
     * @return Liste des événements appartenant à l'utilisateur
     */
    public List<Evenement> evenementsDeLUtilisateur(Utilisateur utilisateur) {
        return evenements.stream()
                .filter(e -> e.getProprietaire().equals(utilisateur))
                .collect(Collectors.toList());
    }
}