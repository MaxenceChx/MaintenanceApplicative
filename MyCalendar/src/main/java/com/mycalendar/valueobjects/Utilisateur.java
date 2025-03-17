package com.mycalendar.valueobjects;

import java.util.Objects;

/**
 * Value Object représentant un utilisateur du système
 */
public final class Utilisateur {
    private final String identifiant;
    private final String motDePasse;
    
    /**
     * Crée un utilisateur
     * 
     * @param identifiant Identifiant de l'utilisateur
     * @param motDePasse Mot de passe de l'utilisateur
     * @throws IllegalArgumentException si l'identifiant ou le mot de passe est null ou vide
     */
    public Utilisateur(String identifiant, String motDePasse) {
        if (identifiant == null || identifiant.trim().isEmpty()) {
            throw new IllegalArgumentException("L'identifiant de l'utilisateur ne peut pas être vide");
        }
        
        if (motDePasse == null || motDePasse.isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide");
        }
        
        this.identifiant = identifiant.trim();
        this.motDePasse = motDePasse;
    }
    
    /**
     * Obtient l'identifiant de l'utilisateur
     * 
     * @return L'identifiant de l'utilisateur
     */
    public String getIdentifiant() {
        return identifiant;
    }
    
    /**
     * Vérifie si le mot de passe fourni correspond
     * 
     * @param motDePasseCandidat Mot de passe à vérifier
     * @return true si le mot de passe correspond, false sinon
     */
    public boolean verifierMotDePasse(String motDePasseCandidat) {
        return this.motDePasse.equals(motDePasseCandidat);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        // Deux utilisateurs sont considérés comme égaux s'ils ont le même identifiant
        // indépendamment de leur mot de passe
        Utilisateur that = (Utilisateur) o;
        return Objects.equals(identifiant, that.identifiant);
    }
    
    @Override
    public int hashCode() {
        // Le hashCode ne doit dépendre que de l'identifiant, pas du mot de passe
        return Objects.hash(identifiant);
    }
    
    @Override
    public String toString() {
        // Ne pas inclure le mot de passe dans la représentation textuelle
        return "Utilisateur: " + identifiant;
    }
}