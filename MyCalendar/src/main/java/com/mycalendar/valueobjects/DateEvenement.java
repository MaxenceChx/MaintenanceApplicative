package com.mycalendar.valueobjects;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Value Object représentant la date d'un événement
 */
public final class DateEvenement {
    private final int annee;
    private final int mois;
    private final int jour;
    
    /**
     * Crée une date d'événement
     * 
     * @param annee L'année
     * @param mois Le mois (1-12)
     * @param jour Le jour (1-31, selon le mois)
     * @throws IllegalArgumentException si la date est invalide
     */
    public DateEvenement(int annee, int mois, int jour) {
        // Validation de base des paramètres
        if (mois < 1 || mois > 12) {
            throw new IllegalArgumentException("Le mois doit être compris entre 1 et 12");
        }
        
        if (jour < 1 || jour > 31) {
            throw new IllegalArgumentException("Le jour doit être compris entre 1 et 31");
        }
        
        // Utiliser LocalDate pour valider que le jour est valide pour ce mois/année
        try {
            LocalDate.of(annee, mois, jour);
        } catch (Exception e) {
            throw new IllegalArgumentException("Date invalide: " + e.getMessage());
        }
        
        this.annee = annee;
        this.mois = mois;
        this.jour = jour;
    }
    
    /**
     * Crée une DateEvenement à partir d'un LocalDate
     * 
     * @param localDate La date
     * @return Une nouvelle instance de DateEvenement
     */
    public static DateEvenement fromLocalDate(LocalDate localDate) {
        return new DateEvenement(
            localDate.getYear(),
            localDate.getMonthValue(),
            localDate.getDayOfMonth()
        );
    }
    
    /**
     * Obtient l'année
     * 
     * @return L'année
     */
    public int getAnnee() {
        return annee;
    }
    
    /**
     * Obtient le mois
     * 
     * @return Le mois (1-12)
     */
    public int getMois() {
        return mois;
    }
    
    /**
     * Obtient le jour
     * 
     * @return Le jour (1-31)
     */
    public int getJour() {
        return jour;
    }
    
    /**
     * Convertit en LocalDate
     * 
     * @return Un objet LocalDate équivalent
     */
    public LocalDate toLocalDate() {
        return LocalDate.of(annee, mois, jour);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateEvenement that = (DateEvenement) o;
        return annee == that.annee &&
               mois == that.mois &&
               jour == that.jour;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(annee, mois, jour);
    }
    
    @Override
    public String toString() {
        return jour + "/" + mois + "/" + annee;
    }
}