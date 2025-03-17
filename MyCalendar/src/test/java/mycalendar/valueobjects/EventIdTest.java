package mycalendar.valueobjects;

import org.junit.jupiter.api.Test;

import com.mycalendar.valueobjects.EventId;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class EventIdTest {
    
    @Test
    @DisplayName("Deux EventId générés doivent être uniques")
    void testUnicite() {
        EventId id1 = EventId.generate();
        EventId id2 = EventId.generate();
        
        assertNotEquals(id1, id2);
        assertNotEquals(id1.getValeur(), id2.getValeur());
    }
    
    @Test
    @DisplayName("La création à partir d'une valeur existante doit fonctionner")
    void testCreationAvecValeur() {
        String valeur = "abc123";
        
        EventId id = new EventId(valeur);
        
        assertEquals(valeur, id.getValeur());
    }
    
    @Test
    @DisplayName("La création à partir d'une valeur nulle ou vide doit échouer")
    void testCreationValeurInvalide() {
        assertThrows(IllegalArgumentException.class, () -> new EventId(null));
        assertThrows(IllegalArgumentException.class, () -> new EventId(""));
        assertThrows(IllegalArgumentException.class, () -> new EventId("   "));
    }
    
    @Test
    @DisplayName("Deux instances avec la même valeur doivent être égales")
    void testEgalite() {
        EventId id1 = new EventId("abc123");
        EventId id2 = new EventId("abc123");
        
        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }
    
    @Test
    @DisplayName("Deux instances avec des valeurs différentes ne doivent pas être égales")
    void testNonEgalite() {
        EventId id1 = new EventId("abc123");
        EventId id2 = new EventId("def456");
        
        assertNotEquals(id1, id2);
    }
    
    @Test
    @DisplayName("La méthode toString doit retourner la valeur de l'ID")
    void testToString() {
        String valeur = "abc123";
        EventId id = new EventId(valeur);
        
        assertEquals(valeur, id.toString());
    }
}