package com.mycalendar.valueobjects;

import java.util.Objects;

/**
 * Value Object représentant la fréquence d'un événement périodique
 */
public final class FrequenceEvenement {
    public static final FrequenceEvenement NON_PERIODIQUE = new FrequenceEvenement(0);
    
    private final int joursEntrePeriodes;
    
    /**
     * Crée une fréquence d'événement
     * 
     * @param joursEntrePeriodes Nombre de jours entre les occurrences (0 pour non périodique)
     * @throws IllegalArgumentException si la valeur est négative
     */
    public FrequenceEvenement(int joursEntrePeriodes) {
        if (joursEntrePeriodes < 0) {
            throw new IllegalArgumentException("La fréquence ne peut pas être négative");
        }
        
        this.joursEntrePeriodes = joursEntrePeriodes;
    }
    
    /**
     * Crée une fréquence quotidienne
     * 
     * @return Une nouvelle instance de FrequenceEvenement
     */
    public static FrequenceEvenement quotidienne() {
        return new FrequenceEvenement(1);
    }
    
    /**
     * Crée une fréquence hebdomadaire
     * 
     * @return Une nouvelle instance de FrequenceEvenement
     */
    public static FrequenceEvenement hebdomadaire() {
        return new FrequenceEvenement(7);
    }
    
    /**
     * Crée une fréquence bihebdomadaire
     * 
     * @return Une nouvelle instance de FrequenceEvenement
     */
    public static FrequenceEvenement bihebdomadaire() {
        return new FrequenceEvenement(14);
    }
    
    /**
     * Crée une fréquence mensuelle (approximative)
     * 
     * @return Une nouvelle instance de FrequenceEvenement
     */
    public static FrequenceEvenement mensuelle() {
        return new FrequenceEvenement(30);
    }
    
    /**
     * Obtient le nombre de jours entre les occurrences
     * 
     * @return Nombre de jours
     */
    public int getJoursEntrePeriodes() {
        return joursEntrePeriodes;
    }
    
    /**
     * Vérifie si l'événement est périodique
     * 
     * @return true si périodique, false sinon
     */
    public boolean estPeriodique() {
        return joursEntrePeriodes > 0;
    }
    
    /**
     * Obtient une description textuelle de la fréquence
     * 
     * @return Description de la fréquence
     */
    public String getDescription() {
        if (!estPeriodique()) {
            return "Non périodique";
        }
        
        switch (joursEntrePeriodes) {
            case 1:
                return "Quotidienne";
            case 7:
                return "Hebdomadaire";
            case 14:
                return "Bihebdomadaire";
            case 30:
            case 31:
                return "Mensuelle";
            default:
                return "Tous les " + joursEntrePeriodes + " jours";
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrequenceEvenement that = (FrequenceEvenement) o;
        return joursEntrePeriodes == that.joursEntrePeriodes;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(joursEntrePeriodes);
    }
    
    @Override
    public String toString() {
        return getDescription();
    }
}