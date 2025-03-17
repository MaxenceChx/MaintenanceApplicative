package com.mycalendar.menu;

/**
 * Interface définissant une action de menu
 * Utilise le pattern Command pour remplacer les blocs conditionnels
 */
public interface MenuAction {
    /**
     * Exécute l'action du menu
     * 
     * @param context Contexte de l'application
     * @return true si l'application doit continuer, false pour quitter
     */
    boolean executer(MenuContext context);
    
    /**
     * Indique si cette action est disponible dans le contexte actuel
     * 
     * @param context Contexte de l'application
     * @return true si l'action est disponible, false sinon
     */
    boolean estDisponible(MenuContext context);
    
    /**
     * Obtient la description de l'action pour l'affichage dans le menu
     * 
     * @return Description de l'action
     */
    String getDescription();
}