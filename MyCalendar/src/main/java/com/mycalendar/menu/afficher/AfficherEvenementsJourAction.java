package com.mycalendar.menu.afficher;

import java.time.LocalDateTime;
import java.util.List;

import com.mycalendar.evenements.Evenement;
import com.mycalendar.menu.EvenementUtils;
import com.mycalendar.menu.MenuAction;
import com.mycalendar.menu.MenuContext;

/**
 * Action pour afficher les événements d'un jour spécifique
 */
class AfficherEvenementsJourAction implements MenuAction {
    
    @Override
    public boolean executer(MenuContext context) {
        try {
            System.out.print("Entrez l'année (AAAA) : ");
            int annee = Integer.parseInt(context.getScanner().nextLine());
            System.out.print("Entrez le mois (1-12) : ");
            int mois = Integer.parseInt(context.getScanner().nextLine());
            System.out.print("Entrez le jour (1-31) : ");
            int jour = Integer.parseInt(context.getScanner().nextLine());
            
            LocalDateTime debutJour = LocalDateTime.of(annee, mois, jour, 0, 0);
            LocalDateTime finJour = debutJour.plusDays(1).minusSeconds(1);
            
            List<Evenement> evenements = context.getCalendarManager().eventsDansPeriode(debutJour, finJour);
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
        return "Afficher les événements d'un JOUR précis";
    }
}
