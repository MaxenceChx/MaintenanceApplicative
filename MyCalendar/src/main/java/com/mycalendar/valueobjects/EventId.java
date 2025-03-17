package com.mycalendar.valueobjects;

import java.util.Objects;
import java.util.UUID;

/**
 * Value Object représentant l'identifiant unique d'un événement
 */
public final class EventId {
    private final String valeur;
    
    /**
     * Crée un identifiant avec une valeur spécifique
     * 
     * @param valeur La valeur de l'identifiant
     * @throws IllegalArgumentException si la valeur est null ou vide
     */
    public EventId(String valeur) {
        if (valeur == null || valeur.trim().isEmpty()) {
            throw new IllegalArgumentException("L'identifiant ne peut pas être vide");
        }
        
        this.valeur = valeur;
    }
    
    /**
     * Génère un nouvel identifiant unique
     * 
     * @return Nouvel identifiant unique
     */
    public static EventId generate() {
        return new EventId(UUID.randomUUID().toString());
    }
    
    /**
     * Obtient la valeur de l'identifiant
     * 
     * @return La valeur de l'identifiant
     */
    public String getValeur() {
        return valeur;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventId eventId = (EventId) o;
        return Objects.equals(valeur, eventId.valeur);
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