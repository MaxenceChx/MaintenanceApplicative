package mycalendar.valueobjects;

import com.mycalendar.valueobjects.PrioriteTache;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class PrioriteTacheTest {
    
    @Test
    @DisplayName("Les différents niveaux de priorité doivent être accessibles")
    void testNiveauxPriorite() {
        assertEquals("HAUTE", PrioriteTache.HAUTE.getCode());
        assertEquals("MOYENNE", PrioriteTache.MOYENNE.getCode());
        assertEquals("BASSE", PrioriteTache.BASSE.getCode());
        
        assertEquals("Haute", PrioriteTache.HAUTE.getLibelle());
        assertEquals("Moyenne", PrioriteTache.MOYENNE.getLibelle());
        assertEquals("Basse", PrioriteTache.BASSE.getLibelle());
    }
    
    @Test
    @DisplayName("La conversion à partir d'une chaîne doit fonctionner")
    void testFromString() {
        assertEquals(PrioriteTache.HAUTE, PrioriteTache.fromString("HAUTE"));
        assertEquals(PrioriteTache.MOYENNE, PrioriteTache.fromString("MOYENNE"));
        assertEquals(PrioriteTache.BASSE, PrioriteTache.fromString("BASSE"));
        
        assertThrows(IllegalArgumentException.class, () -> {
            PrioriteTache.fromString("INVALIDE");
        });
    }
    
    @Test
    @DisplayName("La méthode toString doit retourner le libellé")
    void testToString() {
        assertEquals("Haute", PrioriteTache.HAUTE.toString());
        assertEquals("Moyenne", PrioriteTache.MOYENNE.toString());
        assertEquals("Basse", PrioriteTache.BASSE.toString());
    }
}