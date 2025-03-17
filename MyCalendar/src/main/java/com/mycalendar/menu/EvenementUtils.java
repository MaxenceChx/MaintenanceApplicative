package com.mycalendar.menu;

import java.util.List;

import com.mycalendar.evenements.Evenement;

/**
 * Méthode utilitaire pour afficher une liste d'événements
 * 
 * @param evenements Liste des événements à afficher
 */
public class EvenementUtils {
    public static void afficherListeEvenements(List<Evenement> evenements) {
        if (evenements.isEmpty()) {
            System.out.println("Aucun événement trouvé pour cette période.");
        } else {
            System.out.println("Événements trouvés : ");
            for (Evenement e : evenements) {
                System.out.println("- " + e.description());
            }
        }
    }
}