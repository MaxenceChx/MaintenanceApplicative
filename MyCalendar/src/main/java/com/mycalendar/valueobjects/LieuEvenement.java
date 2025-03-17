package com.mycalendar.valueobjects;

import java.util.Objects;

/**
 * Value Object représentant le lieu d'un événement
 */
public final class LieuEvenement {
    private final String valeur;
    
    /**
     * Crée un lieu d'événement
     * 
     * @param valeur Le nom du lieu (peut être vide)
     */
    public LieuEvenement(String valeur) {
        if (valeur == null || valeur.trim().isEmpty()) {
            this.valeur = "";
        } else {
            this.valeur = valeur.trim();
        }
    }
    
    /**
     * Obtient le nom du lieu
     * 
     * @return La valeur du lieu
     */
    public String getValeur() {
        return valeur;
    }
    
    /**
     * Vérifie si le lieu est vide
     * 
     * @return true si le lieu est vide, false sinon
     */
    public boolean estVide() {
        return valeur.isEmpty();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LieuEvenement that = (LieuEvenement) o;
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
