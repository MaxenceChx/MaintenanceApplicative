package com.mycalendar.menu.ajouter;

import com.mycalendar.menu.MenuAction;
import com.mycalendar.menu.MenuContext;
import com.mycalendar.valueobjects.DateEvenement;
import com.mycalendar.valueobjects.DureeEvenement;
import com.mycalendar.valueobjects.HeureDebut;
import com.mycalendar.valueobjects.TitreEvenement;

/**
 * Action pour ajouter un rendez-vous personnel
 */
public class AjouterRendezVousPersonnelAction implements MenuAction {
    
    @Override
    public boolean executer(MenuContext context) {
        try {
            // Lecture des données de l'événement
            System.out.print("Titre de l'événement : ");
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
            
            System.out.print("Durée (en minutes) : ");
            DureeEvenement duree = new DureeEvenement(Integer.parseInt(context.getScanner().nextLine()));
            
            // Ajout de l'événement au calendrier
            context.getCalendarManager().ajouterRendezVousPersonnel(
                titre, 
                context.getUtilisateurConnecte(), 
                date, 
                heureDebut, 
                duree
            );
            
            System.out.println("Rendez-vous personnel ajouté avec succès.");
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
        return "Ajouter un rendez-vous personnel";
    }
}