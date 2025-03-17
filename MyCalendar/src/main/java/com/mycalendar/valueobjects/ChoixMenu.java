package com.mycalendar.valueobjects;

import java.util.Objects;

/**
 * Value Object représentant un choix de menu
 */
public final class ChoixMenu {
    private final String valeur;
    
    /**
     * Crée un choix de menu
     * 
     * @param valeur La valeur du choix
     * @throws IllegalArgumentException si la valeur est null ou vide
     */
    public ChoixMenu(String valeur) {
        if (valeur == null || valeur.trim().isEmpty()) {
            throw new IllegalArgumentException("Le choix ne peut pas être vide");
        }
        
        this.valeur = valeur.trim();
    }
    
    /**
     * Obtient la valeur du choix
     * 
     * @return La valeur du choix
     */
    public String getValeur() {
        return valeur;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChoixMenu that = (ChoixMenu) o;
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