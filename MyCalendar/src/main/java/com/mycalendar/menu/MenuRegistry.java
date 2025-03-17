package com.mycalendar.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Registre des actions de menu disponibles
 * Permet d'associer des actions à des clés pour éviter les conditionnels
 */
public class MenuRegistry {
    private final List<MenuAction> actions;
    
    /**
     * Crée un registre d'actions vide
     */
    public MenuRegistry() {
        this.actions = new ArrayList<>();
    }
    
    /**
     * Ajoute une action au registre
     * 
     * @param action L'action à ajouter
     */
    public void ajouterAction(MenuAction action) {
        actions.add(action);
    }
    
    /**
     * Affiche le menu et retourne l'action choisie
     * 
     * @param context Contexte de l'application
     * @param titre Titre du menu
     * @return L'action choisie ou null si aucune action n'est choisie
     */
    public MenuAction afficherMenu(MenuContext context, String titre) {
        System.out.println("\n=== " + titre + " ===");
        
        List<MenuAction> actionsDisponibles = new ArrayList<>();
        
        // Afficher uniquement les actions disponibles
        for (MenuAction action : actions) {
            if (action.estDisponible(context)) {
                actionsDisponibles.add(action);
                System.out.println((actionsDisponibles.size()) + " - " + action.getDescription());
            }
        }
        
        if (actionsDisponibles.isEmpty()) {
            System.out.println("Aucune action disponible.");
            return null;
        }
        
        System.out.print("Votre choix : ");
        
        try {
            int choix = Integer.parseInt(context.getScanner().nextLine());
            
            if (choix >= 1 && choix <= actionsDisponibles.size()) {
                return actionsDisponibles.get(choix - 1);
            } else {
                System.out.println("Choix invalide. Veuillez choisir un numéro entre 1 et " + actionsDisponibles.size());
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer un nombre.");
            return null;
        }
    }
}