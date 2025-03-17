package mycalendar.valueobjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.mycalendar.valueobjects.DureeEvenement;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class DureeEvenementTest {
    
    @Test
    @DisplayName("La création d'une durée valide doit fonctionner")
    void testCreationDureeValide() {
        int minutes = 60;
        
        DureeEvenement duree = new DureeEvenement(minutes);
        
        assertEquals(minutes, duree.getMinutes());
        assertEquals(Duration.ofMinutes(minutes), duree.toDuration());
    }
    
    @Test
    @DisplayName("La création avec heures et minutes doit fonctionner")
    void testCreationAvecHeuresEtMinutes() {
        int heures = 1;
        int minutes = 30;
        
        DureeEvenement duree = DureeEvenement.ofHeuresEtMinutes(heures, minutes);
        
        assertEquals(heures * 60 + minutes, duree.getMinutes());
        assertEquals(Duration.ofMinutes(heures * 60 + minutes), duree.toDuration());
    }
    
    @Test
    @DisplayName("La création à partir d'une Duration doit fonctionner")
    void testCreationDepuisDuration() {
        Duration duration = Duration.ofMinutes(45);
        
        DureeEvenement duree = DureeEvenement.fromDuration(duration);
        
        assertEquals(45, duree.getMinutes());
        assertEquals(duration, duree.toDuration());
    }
    
    @Test
    @DisplayName("Deux instances avec les mêmes valeurs doivent être égales")
    void testEgalite() {
        DureeEvenement duree1 = new DureeEvenement(120);
        DureeEvenement duree2 = new DureeEvenement(120);
        
        assertEquals(duree1, duree2);
        assertEquals(duree1.hashCode(), duree2.hashCode());
    }
    
    @Test
    @DisplayName("Deux instances avec des valeurs différentes ne doivent pas être égales")
    void testNonEgalite() {
        DureeEvenement duree1 = new DureeEvenement(120);
        DureeEvenement duree2 = new DureeEvenement(121);
        
        assertNotEquals(duree1, duree2);
    }
    
    @Test
    @DisplayName("La méthode toString doit retourner un format lisible")
    void testToString() {
        DureeEvenement duree = new DureeEvenement(90);
        
        String representation = duree.toString();
        assertTrue(representation.contains("90") || representation.contains("1h30"));
    }
    
    @Test
    @DisplayName("Obtenir une représentation formatée de la durée")
    void testFormatage() {
        DureeEvenement duree1 = new DureeEvenement(90);
        DureeEvenement duree2 = new DureeEvenement(45);
        
        assertEquals("1h30", duree1.formatHeuresMinutes());
        assertEquals("45min", duree2.formatHeuresMinutes());
    }
    
    @Test
    @DisplayName("La durée doit être immuable - tentative de modification via ses getters")
    void testImmuable() {
        DureeEvenement duree = new DureeEvenement(60);
        Duration duration = duree.toDuration();
        
        Duration modifiedDuration = duration.plusMinutes(30);
        
        assertEquals(60, duree.getMinutes());
    }
    
    @ParameterizedTest
    @ValueSource(ints = {-1, -60})
    @DisplayName("La création avec une durée négative doit lancer une exception")
    void testDureeNegative(int minutesNegatives) {
        assertThrows(IllegalArgumentException.class, () -> {
            new DureeEvenement(minutesNegatives);
        });
    }
    
    @Test
    @DisplayName("La création avec une durée de 0 minute doit être possible")
    void testDureeZero() {
        assertDoesNotThrow(() -> new DureeEvenement(0));
    }
    
    @Test
    @DisplayName("La durée très longue doit être acceptée")
    void testDureeTresLongue() {
        assertDoesNotThrow(() -> new DureeEvenement(24 * 60 * 7));
    }
}