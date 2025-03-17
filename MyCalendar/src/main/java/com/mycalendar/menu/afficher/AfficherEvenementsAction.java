package com.mycalendar.menu.afficher;

import java.util.ArrayList;
import java.util.List;

import com.mycalendar.menu.MenuAction;
import com.mycalendar.menu.MenuContext;

/**
 * Action pour afficher les événements
 */
public class AfficherEvenementsAction implements MenuAction {
    
    @Override
    public boolean executer(MenuContext context) {
        System.out.println("\n=== Menu de visualisation d'Événements ===");
        List<MenuAction> actionsVisualisations = new ArrayList<>();
        actionsVisualisations.add(new AfficherTousEvenementsAction());
        actionsVisualisations.add(new AfficherEvenementsMoisAction());
        actionsVisualisations.add(new AfficherEvenementsSemaineAction());
        actionsVisualisations.add(new AfficherEvenementsJourAction());
        
        for (int i = 0; i < actionsVisualisations.size(); i++) {
            MenuAction action = actionsVisualisations.get(i);
            if (action.estDisponible(context)) {
                System.out.println((i + 1) + " - " + action.getDescription());
            }
        }
        System.out.println((actionsVisualisations.size() + 1) + " - Retour");
        System.out.print("Votre choix : ");
        
        try {
            int choix = Integer.parseInt(context.getScanner().nextLine());
            if (choix >= 1 && choix <= actionsVisualisations.size()) {
                actionsVisualisations.get(choix - 1).executer(context);
            }
        } catch (NumberFormatException e) {
            System.out.println("Choix invalide");
        }
        
        return true;
    }
    
    @Override
    public boolean estDisponible(MenuContext context) {
        return context.estConnecte();
    }
    
    @Override
    public String getDescription() {
        return "Voir les événements";
    }
}