package com.mycalendar.valueobjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.mycalendar.valueobjects.HeureDebut;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class HeureDebutTest {
    
    @Test
    @DisplayName("La création d'une heure valide doit fonctionner")
    void testCreationHeureValide() {
        int heure = 14;
        int minute = 30;
        
        HeureDebut heureDebut = new HeureDebut(heure, minute);
        
        assertEquals(heure, heureDebut.getHeure());
        assertEquals(minute, heureDebut.getMinute());
        assertEquals(LocalTime.of(heure, minute), heureDebut.toLocalTime());
    }
    
    @Test
    @DisplayName("La création à partir d'un LocalTime doit fonctionner")
    void testCreationDepuisLocalTime() {
        LocalTime localTime = LocalTime.of(9, 45);
        
        HeureDebut heureDebut = HeureDebut.fromLocalTime(localTime);
        
        assertEquals(localTime.getHour(), heureDebut.getHeure());
        assertEquals(localTime.getMinute(), heureDebut.getMinute());
        assertEquals(localTime, heureDebut.toLocalTime());
    }
    
    @Test
    @DisplayName("Deux instances avec les mêmes valeurs doivent être égales")
    void testEgalite() {
        HeureDebut heure1 = new HeureDebut(10, 15);
        HeureDebut heure2 = new HeureDebut(10, 15);
        
        assertEquals(heure1, heure2);
        assertEquals(heure1.hashCode(), heure2.hashCode());
    }
    
    @Test
    @DisplayName("Deux instances avec des valeurs différentes ne doivent pas être égales")
    void testNonEgalite() {
        HeureDebut heure1 = new HeureDebut(10, 15);
        HeureDebut heure2 = new HeureDebut(10, 16);
        
        assertNotEquals(heure1, heure2);
    }
    
    @Test
    @DisplayName("La méthode toString doit retourner un format lisible")
    void testToString() {
        HeureDebut heure = new HeureDebut(15, 45);
        
        assertTrue(heure.toString().contains("15"));
        assertTrue(heure.toString().contains("45"));
    }
    
    @Test
    @DisplayName("L'heure doit être immuable - tentative de modification via ses getters")
    void testImmuable() {
        HeureDebut heure = new HeureDebut(16, 30);
        LocalTime localTime = heure.toLocalTime();
        
        LocalTime modifiedTime = localTime.plusHours(1);
        
        assertEquals(16, heure.getHeure());
        assertEquals(30, heure.getMinute());
    }
    
    @ParameterizedTest
    @ValueSource(ints = {-1, 24, 99})
    @DisplayName("La création avec une heure invalide doit lancer une exception")
    void testHeureInvalide(int heureInvalide) {
        assertThrows(IllegalArgumentException.class, () -> {
            new HeureDebut(heureInvalide, 30);
        });
    }
    
    @ParameterizedTest
    @ValueSource(ints = {-1, 60, 99})
    @DisplayName("La création avec une minute invalide doit lancer une exception")
    void testMinuteInvalide(int minuteInvalide) {
        assertThrows(IllegalArgumentException.class, () -> {
            new HeureDebut(12, minuteInvalide);
        });
    }
    
    @Test
    @DisplayName("Les limites pour heure et minute doivent être acceptées")
    void testLimites() {
        assertDoesNotThrow(() -> new HeureDebut(0, 0));
        assertDoesNotThrow(() -> new HeureDebut(23, 59));
    }
}
