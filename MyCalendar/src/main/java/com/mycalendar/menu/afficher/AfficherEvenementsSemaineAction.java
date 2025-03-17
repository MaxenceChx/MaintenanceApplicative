package com.mycalendar.menu.afficher;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

import com.mycalendar.evenements.Evenement;
import com.mycalendar.menu.EvenementUtils;
import com.mycalendar.menu.MenuAction;
import com.mycalendar.menu.MenuContext;

/**
 * Action pour afficher les événements d'une semaine spécifique
 */
class AfficherEvenementsSemaineAction implements MenuAction {
    
    @Override
    public boolean executer(MenuContext context) {
        try {
            System.out.print("Entrez l'année (AAAA) : ");
            int annee = Integer.parseInt(context.getScanner().nextLine());
            System.out.print("Entrez le numéro de semaine (1-52) : ");
            int semaine = Integer.parseInt(context.getScanner().nextLine());
            
            LocalDateTime debutSemaine = LocalDateTime.now()
                    .withYear(annee)
                    .with(WeekFields.of(Locale.FRANCE).weekOfYear(), semaine)
                    .with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 1)
                    .withHour(0).withMinute(0);
            LocalDateTime finSemaine = debutSemaine.plusDays(7).minusSeconds(1);
            
            List<Evenement> evenements = context.getCalendarManager().eventsDansPeriode(debutSemaine, finSemaine);
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
        return "Afficher les événements d'une SEMAINE précise";
    }
}