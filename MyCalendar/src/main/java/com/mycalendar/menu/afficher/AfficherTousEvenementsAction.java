package com.mycalendar.menu.afficher;

import com.mycalendar.menu.MenuAction;
import com.mycalendar.menu.MenuContext;

/**
 * Action pour afficher tous les événements
 */
class AfficherTousEvenementsAction implements MenuAction {
    
    @Override
    public boolean executer(MenuContext context) {
        context.getCalendarManager().afficherEvenements();
        return true;
    }
    
    @Override
    public boolean estDisponible(MenuContext context) {
        return context.estConnecte();
    }
    
    @Override
    public String getDescription() {
        return "Afficher TOUS les événements";
    }
}
