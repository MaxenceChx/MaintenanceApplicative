package com.mycalendar.menu;

import java.util.Scanner;

import com.mycalendar.CalendarManager;
import com.mycalendar.UserManager;
import com.mycalendar.valueobjects.Utilisateur;

/**
 * Classe contenant le contexte de l'application
 */
public class MenuContext {
    private final CalendarManager calendarManager;
    private final UserManager userManager;
    private final Scanner scanner;
    private Utilisateur utilisateurConnecte;
    
    /**
     * Crée un contexte de menu
     * 
     * @param calendarManager Gestionnaire de calendrier
     * @param userManager Gestionnaire d'utilisateurs
     * @param scanner Scanner pour lire les entrées utilisateur
     */
    public MenuContext(CalendarManager calendarManager, UserManager userManager, Scanner scanner) {
        this.calendarManager = calendarManager;
        this.userManager = userManager;
        this.scanner = scanner;
        this.utilisateurConnecte = null;
    }
    
    /**
     * Obtient le gestionnaire de calendrier
     * 
     * @return Gestionnaire de calendrier
     */
    public CalendarManager getCalendarManager() {
        return calendarManager;
    }
    
    /**
     * Obtient le gestionnaire d'utilisateurs
     * 
     * @return Gestionnaire d'utilisateurs
     */
    public UserManager getUserManager() {
        return userManager;
    }
    
    /**
     * Obtient le scanner pour lire les entrées utilisateur
     * 
     * @return Scanner
     */
    public Scanner getScanner() {
        return scanner;
    }
    
    /**
     * Obtient l'utilisateur actuellement connecté
     * 
     * @return L'utilisateur connecté ou null si aucun utilisateur n'est connecté
     */
    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
    
    /**
     * Définit l'utilisateur connecté
     * 
     * @param utilisateur L'utilisateur connecté
     */
    public void setUtilisateurConnecte(Utilisateur utilisateur) {
        this.utilisateurConnecte = utilisateur;
    }
    
    /**
     * Vérifie si un utilisateur est connecté
     * 
     * @return true si un utilisateur est connecté, false sinon
     */
    public boolean estConnecte() {
        return utilisateurConnecte != null;
    }
}
