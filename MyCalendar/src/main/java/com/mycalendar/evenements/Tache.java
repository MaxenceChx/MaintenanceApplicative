package com.mycalendar.evenements;

import com.mycalendar.valueobjects.*;

/**
 * Classe représentant une tâche à effectuer
 */
public class Tache extends EvenementAbstrait {
    
    private final PrioriteTache priorite;
    
    /**
     * Constructeur pour une tâche avec un identifiant spécifique
     * 
     * @param id Identifiant unique de la tâche
     * @param titre Titre de la tâche
     * @param proprietaire Propriétaire de la tâche
     * @param date Date de la tâche
     * @param heureDebut Heure de début
     * @param duree Durée estimée de la tâche
     * @param priorite Priorité de la tâche
     */
    public Tache(EventId id, TitreEvenement titre, Utilisateur proprietaire,
                 DateEvenement date, HeureDebut heureDebut, DureeEvenement duree,
                 PrioriteTache priorite) {
        super(
            id, 
            titre, 
            proprietaire, 
            date, 
            heureDebut, 
            duree, 
            new LieuEvenement(""), // Pas de lieu pour une tâche
            ParticipantsEvenement.avecUtilisateurs(java.util.Collections.emptyList()), // Pas de participants pour une tâche
            FrequenceEvenement.NON_PERIODIQUE // Une tâche n'est pas périodique
        );
        
        this.priorite = priorite != null ? priorite : PrioriteTache.MOYENNE;
    }
    
    /**
     * Constructeur pour une tâche avec un identifiant généré automatiquement
     * 
     * @param titre Titre de la tâche
     * @param proprietaire Propriétaire de la tâche
     * @param date Date de la tâche
     * @param heureDebut Heure de début
     * @param duree Durée estimée de la tâche
     * @param priorite Priorité de la tâche
     */
    public Tache(TitreEvenement titre, Utilisateur proprietaire,
                 DateEvenement date, HeureDebut heureDebut, DureeEvenement duree,
                 PrioriteTache priorite) {
        this(EventId.generate(), titre, proprietaire, date, heureDebut, duree, priorite);
    }
    
    /**
     * Obtient la priorité de la tâche
     * 
     * @return Priorité de la tâche
     */
    public PrioriteTache getPriorite() {
        return priorite;
    }
    
    @Override
    public TypeEvenement getType() {
        return TypeEvenement.TACHE;
    }
    
    @Override
    public boolean estEnConflitAvec(Evenement autreEvenement) {
        // Les tâches de basse priorité ne bloquent pas le calendrier
        if (this.priorite == PrioriteTache.BASSE) {
            return false;
        }
        
        // Pour les autres priorités, utiliser le comportement par défaut
        return super.estEnConflitAvec(autreEvenement);
    }
    
    @Override
    public String description() {
        return String.format(
            "Tâche : %s (priorité %s)",
            getTitre(),
            getPriorite().toString().toLowerCase()
        );
    }
}