package mycalendar;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.mycalendar.valueobjects.DateEvenement;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DateEvenementTest {
    
    @Test
    @DisplayName("La création d'une date valide doit fonctionner")
    void testCreationDateValide() {
        int annee = 2025;
        int mois = 4;
        int jour = 15;
        
        DateEvenement date = new DateEvenement(annee, mois, jour);
        
        assertEquals(annee, date.getAnnee());
        assertEquals(mois, date.getMois());
        assertEquals(jour, date.getJour());
        assertEquals(LocalDate.of(annee, mois, jour), date.toLocalDate());
    }
    
    @Test
    @DisplayName("La création à partir d'un LocalDate doit fonctionner")
    void testCreationDepuisLocalDate() {
        LocalDate localDate = LocalDate.of(2025, 5, 20);
        
        DateEvenement date = DateEvenement.fromLocalDate(localDate);
        
        assertEquals(localDate.getYear(), date.getAnnee());
        assertEquals(localDate.getMonthValue(), date.getMois());
        assertEquals(localDate.getDayOfMonth(), date.getJour());
        assertEquals(localDate, date.toLocalDate());
    }
    
    @Test
    @DisplayName("Deux instances avec les mêmes valeurs doivent être égales")
    void testEgalite() {
        DateEvenement date1 = new DateEvenement(2025, 6, 10);
        DateEvenement date2 = new DateEvenement(2025, 6, 10);
        
        assertEquals(date1, date2);
        assertEquals(date1.hashCode(), date2.hashCode());
    }
    
    @Test
    @DisplayName("Deux instances avec des valeurs différentes ne doivent pas être égales")
    void testNonEgalite() {
        DateEvenement date1 = new DateEvenement(2025, 6, 10);
        DateEvenement date2 = new DateEvenement(2025, 6, 11);
        
        assertNotEquals(date1, date2);
    }
    
    @Test
    @DisplayName("La méthode toString doit retourner un format lisible")
    void testToString() {
        DateEvenement date = new DateEvenement(2025, 7, 15);
        
        assertTrue(date.toString().contains("15"));
        assertTrue(date.toString().contains("7"));
        assertTrue(date.toString().contains("2025"));
    }
    
    @Test
    @DisplayName("La date doit être immuable - tentative de modification via ses getters")
    void testImmuable() {
        DateEvenement date = new DateEvenement(2025, 8, 20);
        LocalDate localDate = date.toLocalDate();
        
        LocalDate modifiedDate = localDate.plusDays(1);
        
        assertEquals(2025, date.getAnnee());
        assertEquals(8, date.getMois());
        assertEquals(20, date.getJour());
    }
    
    @ParameterizedTest
    @ValueSource(ints = {0, 13, -1, 99})
    @DisplayName("La création avec un mois invalide doit lancer une exception")
    void testMoisInvalide(int moisInvalide) {
        assertThrows(IllegalArgumentException.class, () -> {
            new DateEvenement(2025, moisInvalide, 15);
        });
    }
    
    @ParameterizedTest
    @ValueSource(ints = {0, 32, -1, 99})
    @DisplayName("La création avec un jour invalide doit lancer une exception")
    void testJourInvalide(int jourInvalide) {
        assertThrows(IllegalArgumentException.class, () -> {
            new DateEvenement(2025, 5, jourInvalide);
        });
    }
    
    @Test
    @DisplayName("La validation du jour 31 doit prendre en compte le mois")
    void testJour31Valide() {
        assertDoesNotThrow(() -> new DateEvenement(2025, 1, 31));
        assertDoesNotThrow(() -> new DateEvenement(2025, 3, 31));
        assertDoesNotThrow(() -> new DateEvenement(2025, 5, 31));
        
        assertThrows(IllegalArgumentException.class, () -> new DateEvenement(2025, 2, 31));
        
        assertThrows(IllegalArgumentException.class, () -> new DateEvenement(2025, 4, 31));
    }
    
    @Test
    @DisplayName("La validation du 29 février doit prendre en compte les années bissextiles")
    void testFevrier29() {
        assertDoesNotThrow(() -> new DateEvenement(2024, 2, 29));
        
        assertThrows(IllegalArgumentException.class, () -> new DateEvenement(2025, 2, 29));
    }
}