package com.mycalendar;

import com.mycalendar.valueobjects.Utilisateur;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    
    public static final int CAPACITE_MAX = 99;
    
    private final List<Utilisateur> utilisateurs;
    
    public UserManager() {
        utilisateurs = new ArrayList<>();
        
        // Ajouter les utilisateurs prédéfinis
        creerCompte("Roger", "Chat");
        creerCompte("Pierre", "KiRouhl");
    }
    
    /**
     * Crée un nouveau compte utilisateur
     * 
     * @param nomUtilisateur Nom d'utilisateur
     * @param motDePasse Mot de passe
     * @return true si le compte a été créé, false si le nom d'utilisateur existe déjà ou si la limite est atteinte
     */
    public boolean creerCompte(String nomUtilisateur, String motDePasse) {
        // Vérifier si l'utilisateur existe déjà
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getIdentifiant().equals(nomUtilisateur)) {
                return false; // L'utilisateur existe déjà
            }
        }
        
        // Vérifier si la limite d'utilisateurs est atteinte
        if (utilisateurs.size() >= CAPACITE_MAX) {
            return false; // Limite d'utilisateurs atteinte
        }
        
        // Créer le compte
        try {
            Utilisateur nouvelUtilisateur = new Utilisateur(nomUtilisateur, motDePasse);
            utilisateurs.add(nouvelUtilisateur);
            return true;
        } catch (IllegalArgumentException e) {
            return false; // Données d'utilisateur invalides
        }
    }
    
    /**
     * Vérifie si les identifiants correspondent à un utilisateur valide
     * 
     * @param nomUtilisateur Nom d'utilisateur
     * @param motDePasse Mot de passe
     * @return true si l'authentification est réussie, false sinon
     */
    public boolean verifierAuthentification(String nomUtilisateur, String motDePasse) {
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getIdentifiant().equals(nomUtilisateur) && 
                utilisateur.verifierMotDePasse(motDePasse)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Recherche un utilisateur par son identifiant
     * 
     * @param identifiant Identifiant de l'utilisateur
     * @return L'utilisateur trouvé ou null si aucun utilisateur ne correspond
     */
    public Utilisateur rechercherUtilisateur(String identifiant) {
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getIdentifiant().equals(identifiant)) {
                return utilisateur;
            }
        }
        return null;
    }
    
    /**
     * Retourne le nombre d'utilisateurs enregistrés
     * 
     * @return Nombre d'utilisateurs
     */
    public int getNombreUtilisateurs() {
        return utilisateurs.size();
    }
}