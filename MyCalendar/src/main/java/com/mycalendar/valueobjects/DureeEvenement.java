package com.mycalendar.valueobjects;

import java.time.Duration;
import java.util.Objects;

/**
 * Value Object représentant la durée d'un événement
 */
public final class DureeEvenement {
    private final int minutes;
    
    /**
     * Crée une durée d'événement
     * 
     * @param minutes La durée en minutes
     * @throws IllegalArgumentException si la durée est négative
     */
    public DureeEvenement(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("La durée ne peut pas être négative");
        }
        
        this.minutes = minutes;
    }
    
    /**
     * Crée une durée en spécifiant les heures et les minutes
     * 
     * @param heures Le nombre d'heures
     * @param minutes Le nombre de minutes supplémentaires
     * @return Une nouvelle instance de DureeEvenement
     */
    public static DureeEvenement ofHeuresEtMinutes(int heures, int minutes) {
        return new DureeEvenement(heures * 60 + minutes);
    }
    
    /**
     * Crée une DureeEvenement à partir d'une Duration
     * 
     * @param duration La durée
     * @return Une nouvelle instance de DureeEvenement
     */
    public static DureeEvenement fromDuration(Duration duration) {
        return new DureeEvenement((int) duration.toMinutes());
    }
    
    /**
     * Obtient la durée en minutes
     * 
     * @return Le nombre total de minutes
     */
    public int getMinutes() {
        return minutes;
    }
    
    /**
     * Convertit en Duration
     * 
     * @return Un objet Duration équivalent
     */
    public Duration toDuration() {
        return Duration.ofMinutes(minutes);
    }
    
    /**
     * Formate la durée en heures et minutes
     * 
     * @return Une représentation formatée (ex: "1h30" ou "45min")
     */
    public String formatHeuresMinutes() {
        if (minutes < 60) {
            return minutes + "min";
        } else {
            int heures = minutes / 60;
            int minutesRestantes = minutes % 60;
            
            if (minutesRestantes == 0) {
                return heures + "h";
            } else {
                return heures + "h" + minutesRestantes;
            }
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DureeEvenement that = (DureeEvenement) o;
        return minutes == that.minutes;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(minutes);
    }
    
    @Override
    public String toString() {
        return formatHeuresMinutes();
    }
}
