package com.mycalendar;

import com.mycalendar.menu.*;
import com.mycalendar.menu.afficher.AfficherEvenementsAction;
import com.mycalendar.menu.ajouter.AjouterEvenementPeriodiqueAction;
import com.mycalendar.menu.ajouter.AjouterRendezVousPersonnelAction;
import com.mycalendar.menu.ajouter.AjouterReunionAction;
import com.mycalendar.menu.ajouter.AjouterTacheAction;
import com.mycalendar.menu.compte.ConnexionAction;
import com.mycalendar.menu.compte.CreationCompteAction;
import com.mycalendar.menu.compte.DeconnexionAction;
import com.mycalendar.menu.supprimer.SupprimerEvenementAction;

import java.util.Scanner;

/**
 * Classe principale de l'application
 * Utilise une architecture polymorphique pour éliminer les conditionnels explicites
 */
public class Main {
    public static void main(String[] args) {
        // Création des objets principaux
        CalendarManager calendar = new CalendarManager();
        UserManager userManager = new UserManager();
        Scanner scanner = new Scanner(System.in);
        
        // Création du contexte
        MenuContext context = new MenuContext(calendar, userManager, scanner);
        
        // Création des registres de menu
        MenuRegistry menuNonConnecte = creerMenuNonConnecte();
        MenuRegistry menuConnecte = creerMenuConnecte();
        
        boolean continuer = true;
        
        while (continuer) {
            // Affichage du logo
            afficherLogo();
            
            // Sélection du menu approprié selon l'état de connexion
            MenuRegistry menuActuel = context.estConnecte() ? menuConnecte : menuNonConnecte;
            
            // Titre du menu en fonction de l'état de connexion
            String titre = context.estConnecte() 
                ? "Menu Gestionnaire d'Événements - " + context.getUtilisateurConnecte().getIdentifiant()
                : "Menu Principal";
            
            // Affichage du menu et récupération de l'action choisie
            MenuAction actionChoisie = menuActuel.afficherMenu(context, titre);
            
            // Exécution de l'action si une action a été choisie
            if (actionChoisie != null) {
                continuer = actionChoisie.executer(context);
            }
        }
        
        scanner.close();
        System.out.println("Au revoir !");
    }
    
    /**
     * Crée le menu pour les utilisateurs non connectés
     * 
     * @return Le registre des actions pour utilisateurs non connectés
     */
    private static MenuRegistry creerMenuNonConnecte() {
        MenuRegistry registry = new MenuRegistry();
        registry.ajouterAction(new ConnexionAction());
        registry.ajouterAction(new CreationCompteAction());
        return registry;
    }
    
    /**
     * Crée le menu pour les utilisateurs connectés
     * 
     * @return Le registre des actions pour utilisateurs connectés
     */
    private static MenuRegistry creerMenuConnecte() {
        MenuRegistry registry = new MenuRegistry();
        registry.ajouterAction(new AfficherEvenementsAction());
        registry.ajouterAction(new AjouterRendezVousPersonnelAction());
        registry.ajouterAction(new AjouterReunionAction());
        registry.ajouterAction(new AjouterEvenementPeriodiqueAction());
        registry.ajouterAction(new AjouterTacheAction());
        registry.ajouterAction(new SupprimerEvenementAction());
        registry.ajouterAction(new DeconnexionAction());
        return registry;
    }
    
    /**
     * Affiche le logo de l'application
     */
    private static void afficherLogo() {
        System.out.println("  _____         _                   _                __  __");
        System.out.println(" / ____|       | |                 | |              |  \\/  |");
        System.out.println(
                "| |       __ _ | |  ___  _ __    __| |  __ _  _ __  | \\  / |  __ _  _ __    __ _   __ _   ___  _ __");
        System.out.println(
                "| |      / _` || | / _ \\| '_ \\  / _` | / _` || '__| | |\\/| | / _` || '_ \\  / _` | / _` | / _ \\| '__|");
        System.out.println(
                "| |____ | (_| || ||  __/| | | || (_| || (_| || |    | |  | || (_| || | | || (_| || (_| ||  __/| |");
        System.out.println(
                " \\_____| \\__,_||_| \\___||_| |_| \\__,_| \\__,_||_|    |_|  |_| \\__,_||_| |_| \\__,_| \\__, | \\___||_|");
        System.out.println(
                "                                                                                   __/ |");
        System.out.println(
                "                                                                                  |___/");
    }
}