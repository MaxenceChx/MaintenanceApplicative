package com.mycalendar.menu.afficher;

import java.time.LocalDateTime;
import java.util.List;

import com.mycalendar.evenements.Evenement;
import com.mycalendar.menu.EvenementUtils;
import com.mycalendar.menu.MenuAction;
import com.mycalendar.menu.MenuContext;

/**
 * Action pour afficher les événements d'un mois spécifique
 */
class AfficherEvenementsMoisAction implements MenuAction {
    
    @Override
    public boolean executer(MenuContext context) {
        try {
            System.out.print("Entrez l'année (AAAA) : ");
            int annee = Integer.parseInt(context.getScanner().nextLine());
            System.out.print("Entrez le mois (1-12) : ");
            int mois = Integer.parseInt(context.getScanner().nextLine());
            
            LocalDateTime debutMois = LocalDateTime.of(annee, mois, 1, 0, 0);
            LocalDateTime finMois = debutMois.plusMonths(1).minusSeconds(1);
            
            List<Evenement> evenements = context.getCalendarManager().eventsDansPeriode(debutMois, finMois);
            EvenementUtils.afficherListeEvenements(evenements);
        } catch (NumberFormatException e) {
            System.out.println("Format de nombre invalide");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
        
        return true;
    }
    
    @Override
    public boolean estDisponible(MenuContext context) {
        return context.estConnecte();
    }
    
    @Override
    public String getDescription() {
        return "Afficher les événements d'un MOIS précis";
    }
}