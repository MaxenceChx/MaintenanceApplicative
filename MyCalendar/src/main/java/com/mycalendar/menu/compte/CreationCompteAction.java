package com.mycalendar.menu.compte;

import com.mycalendar.menu.MenuAction;
import com.mycalendar.menu.MenuContext;
import com.mycalendar.valueobjects.Utilisateur;

/**
 * Action pour créer un compte utilisateur
 */
public class CreationCompteAction implements MenuAction {
    
    @Override
    public boolean executer(MenuContext context) {
        System.out.print("Nom d'utilisateur: ");
        String nomUtilisateur = context.getScanner().nextLine();
        System.out.print("Mot de passe: ");
        String motDePasse = context.getScanner().nextLine();
        System.out.print("Répéter mot de passe: ");
        String confirmation = context.getScanner().nextLine();
        
        if (!motDePasse.equals(confirmation)) {
            System.out.println("Les mots de passes ne correspondent pas...");
            return true;
        }
        
        if (context.getUserManager().creerCompte(nomUtilisateur, motDePasse)) {
            System.out.println("Compte créé avec succès !");
            Utilisateur utilisateur = context.getUserManager().rechercherUtilisateur(nomUtilisateur);
            context.setUtilisateurConnecte(utilisateur);
        } else {
            System.out.println("Impossible de créer le compte.");
        }
        
        return true;
    }
    
    @Override
    public boolean estDisponible(MenuContext context) {
        return !context.estConnecte();
    }
    
    @Override
    public String getDescription() {
        return "Créer un compte";
    }
}