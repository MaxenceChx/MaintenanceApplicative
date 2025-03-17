package com.mycalendar.valueobjects;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Value Object représentant l'heure de début d'un événement
 */
public final class HeureDebut {
    private final int heure;
    private final int minute;
    
    /**
     * Crée une heure de début
     * 
     * @param heure L'heure (0-23)
     * @param minute La minute (0-59)
     * @throws IllegalArgumentException si les valeurs sont invalides
     */
    public HeureDebut(int heure, int minute) {
        if (heure < 0 || heure > 23) {
            throw new IllegalArgumentException("L'heure doit être comprise entre 0 et 23");
        }
        
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("La minute doit être comprise entre 0 et 59");
        }
        
        this.heure = heure;
        this.minute = minute;
    }
    
    /**
     * Crée une HeureDebut à partir d'un LocalTime
     * 
     * @param localTime L'heure
     * @return Une nouvelle instance de HeureDebut
     */
    public static HeureDebut fromLocalTime(LocalTime localTime) {
        return new HeureDebut(
            localTime.getHour(),
            localTime.getMinute()
        );
    }
    
    /**
     * Obtient l'heure
     * 
     * @return L'heure (0-23)
     */
    public int getHeure() {
        return heure;
    }
    
    /**
     * Obtient la minute
     * 
     * @return La minute (0-59)
     */
    public int getMinute() {
        return minute;
    }
    
    /**
     * Convertit en LocalTime
     * 
     * @return Un objet LocalTime équivalent
     */
    public LocalTime toLocalTime() {
        return LocalTime.of(heure, minute);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeureDebut that = (HeureDebut) o;
        return heure == that.heure && minute == that.minute;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(heure, minute);
    }
    
    @Override
    public String toString() {
        return String.format("%02d:%02d", heure, minute);
    }
}