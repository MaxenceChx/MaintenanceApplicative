package com.mycalendar.menu.supprimer;

import com.mycalendar.evenements.Evenement;
import com.mycalendar.menu.MenuAction;
import com.mycalendar.menu.MenuContext;
import com.mycalendar.valueobjects.EventId;

import java.util.List;

/**
 * Action pour supprimer un événement
 */
public class SupprimerEvenementAction implements MenuAction {
    
    @Override
    public boolean executer(MenuContext context) {
        System.out.println("\n=== Suppression d'un événement ===");
        
        // Récupérer les événements de l'utilisateur
        List<Evenement> evenements = context.getCalendarManager().evenementsDeLUtilisateur(
            context.getUtilisateurConnecte()
        );
        
        if (evenements.isEmpty()) {
            System.out.println("Vous n'avez aucun événement à supprimer.");
            return true;
        }
        
        // Afficher la liste des événements
        System.out.println("Vos événements :");
        for (int i = 0; i < evenements.size(); i++) {
            Evenement e = evenements.get(i);
            System.out.println((i + 1) + " - " + e.description() + " (ID: " + e.getId() + ")");
        }
        
        // Demander à l'utilisateur de choisir un événement
        System.out.print("Entrez le numéro de l'événement à supprimer (0 pour annuler) : ");
        try {
            int choix = Integer.parseInt(context.getScanner().nextLine());
            
            if (choix < 0 || choix > evenements.size()) {
                System.out.println("Choix invalide.");
                return true;
            }
            
            if (choix == 0) {
                System.out.println("Suppression annulée.");
                return true;
            }
            
            // Récupérer l'ID de l'événement sélectionné
            EventId eventId = evenements.get(choix - 1).getId();
            
            // Supprimer l'événement
            boolean resultat = context.getCalendarManager().supprimerEvenement(eventId);
            
            if (resultat) {
                System.out.println("Événement supprimé avec succès.");
            } else {
                System.out.println("Échec de la suppression de l'événement.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer un nombre valide.");
        }
        
        return true;
    }
    
    @Override
    public boolean estDisponible(MenuContext context) {
        return context.estConnecte();
    }
    
    @Override
    public String getDescription() {
        return "Supprimer un événement";
    }
}