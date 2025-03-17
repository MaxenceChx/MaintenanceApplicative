package com.mycalendar.menu.compte;

import com.mycalendar.menu.MenuAction;
import com.mycalendar.menu.MenuContext;
import com.mycalendar.valueobjects.Utilisateur;

/**
 * Action pour se connecter à l'application
 */
public class ConnexionAction implements MenuAction {
    
    @Override
    public boolean executer(MenuContext context) {
        System.out.print("Nom d'utilisateur: ");
        String nomUtilisateur = context.getScanner().nextLine();
        System.out.print("Mot de passe: ");
        String motDePasse = context.getScanner().nextLine();

        if (context.getUserManager().verifierAuthentification(nomUtilisateur, motDePasse)) {
            Utilisateur utilisateur = context.getUserManager().rechercherUtilisateur(nomUtilisateur);
            context.setUtilisateurConnecte(utilisateur);
            System.out.println("Connexion réussie !");
        } else {
            System.out.println("Nom d'utilisateur ou mot de passe incorrect.");
        }
        
        return true;
    }
    
    @Override
    public boolean estDisponible(MenuContext context) {
        return !context.estConnecte();
    }
    
    @Override
    public String getDescription() {
        return "Se connecter";
    }
}