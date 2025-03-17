package com.mycalendar.menu.ajouter;

import com.mycalendar.menu.MenuAction;
import com.mycalendar.menu.MenuContext;
import com.mycalendar.valueobjects.*;

/**
 * Action pour ajouter une tâche
 */
public class AjouterTacheAction implements MenuAction {
    
    @Override
    public boolean executer(MenuContext context) {
        try {
            // Lecture des données de la tâche
            System.out.print("Titre de la tâche : ");
            TitreEvenement titre = new TitreEvenement(context.getScanner().nextLine());
            
            System.out.print("Année (AAAA) : ");
            int annee = Integer.parseInt(context.getScanner().nextLine());
            System.out.print("Mois (1-12) : ");
            int mois = Integer.parseInt(context.getScanner().nextLine());
            System.out.print("Jour (1-31) : ");
            int jour = Integer.parseInt(context.getScanner().nextLine());
            DateEvenement date = new DateEvenement(annee, mois, jour);
            
            System.out.print("Heure début (0-23) : ");
            int heure = Integer.parseInt(context.getScanner().nextLine());
            System.out.print("Minute début (0-59) : ");
            int minute = Integer.parseInt(context.getScanner().nextLine());
            HeureDebut heureDebut = new HeureDebut(heure, minute);
            
            System.out.print("Durée estimée (en minutes) : ");
            DureeEvenement duree = new DureeEvenement(Integer.parseInt(context.getScanner().nextLine()));
            
            System.out.println("Priorité :");
            System.out.println("1 - Haute");
            System.out.println("2 - Moyenne");
            System.out.println("3 - Basse");
            System.out.print("Votre choix : ");
            int choixPriorite = Integer.parseInt(context.getScanner().nextLine());
            
            PrioriteTache priorite;
            switch (choixPriorite) {
                case 1:
                    priorite = PrioriteTache.HAUTE;
                    break;
                case 3:
                    priorite = PrioriteTache.BASSE;
                    break;
                default:
                    priorite = PrioriteTache.MOYENNE;
                    break;
            }
            
            // Ajout de la tâche au calendrier
            context.getCalendarManager().ajouterTache(
                titre, 
                context.getUtilisateurConnecte(), 
                date, 
                heureDebut, 
                duree,
                priorite
            );
            
            System.out.println("Tâche ajoutée avec succès.");
        } catch (NumberFormatException e) {
            System.out.println("Erreur de format de nombre: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur dans les données: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Une erreur est survenue: " + e.getMessage());
        }
        
        return true;
    }
    
    @Override
    public boolean estDisponible(MenuContext context) {
        return context.estConnecte();
    }
    
    @Override
    public String getDescription() {
        return "Ajouter une tâche";
    }
}