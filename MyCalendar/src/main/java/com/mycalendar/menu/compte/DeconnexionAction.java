package com.mycalendar.menu.compte;

import com.mycalendar.menu.MenuAction;
import com.mycalendar.menu.MenuContext;

/**
 * Action pour se déconnecter
 */
public class DeconnexionAction implements MenuAction {
    
    @Override
    public boolean executer(MenuContext context) {
        System.out.println("Déconnexion ! Voulez-vous continuer ? (oui/non)");
        boolean continuer = context.getScanner().nextLine().trim().equalsIgnoreCase("oui");
        context.setUtilisateurConnecte(null);
        return continuer;
    }
    
    @Override
    public boolean estDisponible(MenuContext context) {
        return context.estConnecte();
    }
    
    @Override
    public String getDescription() {
        return "Se déconnecter";
    }
}
