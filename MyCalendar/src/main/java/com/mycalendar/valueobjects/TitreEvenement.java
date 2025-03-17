package com.mycalendar.valueobjects;

import java.util.Objects;

/**
 * Value Object représentant le titre d'un événement
 */
public final class TitreEvenement {
    private static final int LONGUEUR_MAX = 100;
    private final String valeur;
    
    /**
     * Crée un titre d'événement
     * 
     * @param valeur Le texte du titre
     * @throws IllegalArgumentException si le titre est null ou vide
     */
    public TitreEvenement(String valeur) {
        if (valeur == null || valeur.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
        }
        
        // Limiter la longueur du titre si nécessaire
        if (valeur.length() > LONGUEUR_MAX) {
            this.valeur = valeur.substring(0, LONGUEUR_MAX - 3) + "...";
        } else {
            this.valeur = valeur;
        }
    }
    
    /**
     * Obtient le texte du titre
     * 
     * @return La valeur du titre
     */
    public String getValeur() {
        return valeur;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TitreEvenement that = (TitreEvenement) o;
        return Objects.equals(valeur, that.valeur);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(valeur);
    }
    
    @Override
    public String toString() {
        return valeur;
    }
}