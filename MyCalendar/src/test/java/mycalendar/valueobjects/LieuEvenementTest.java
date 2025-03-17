package mycalendar.valueobjects;

import org.junit.jupiter.api.Test;

import com.mycalendar.valueobjects.LieuEvenement;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class LieuEvenementTest {
    
    @Test
    @DisplayName("La création d'un lieu valide doit fonctionner")
    void testCreationLieuValide() {
        String valeur = "Salle de conférence";
        
        LieuEvenement lieu = new LieuEvenement(valeur);
        
        assertEquals(valeur, lieu.getValeur());
    }
    
    @Test
    @DisplayName("La création d'un lieu vide doit fonctionner (pour les RDV personnels)")
    void testCreationLieuVide() {
        LieuEvenement lieu = new LieuEvenement("");
        
        assertEquals("", lieu.getValeur());
        assertTrue(lieu.estVide());
    }
    
    @Test
    @DisplayName("Deux instances avec les mêmes valeurs doivent être égales")
    void testEgalite() {
        LieuEvenement lieu1 = new LieuEvenement("Salle A");
        LieuEvenement lieu2 = new LieuEvenement("Salle A");
        
        assertEquals(lieu1, lieu2);
        assertEquals(lieu1.hashCode(), lieu2.hashCode());
    }
    
    @Test
    @DisplayName("Deux instances avec des valeurs différentes ne doivent pas être égales")
    void testNonEgalite() {
        LieuEvenement lieu1 = new LieuEvenement("Salle A");
        LieuEvenement lieu2 = new LieuEvenement("Salle B");
        
        assertNotEquals(lieu1, lieu2);
    }
    
    @Test
    @DisplayName("La méthode toString doit retourner la valeur du lieu")
    void testToString() {
        String valeur = "Café du coin";
        LieuEvenement lieu = new LieuEvenement(valeur);
        
        assertEquals(valeur, lieu.toString());
    }
    
    @Test
    @DisplayName("La création avec une valeur null doit être traitée comme chaîne vide")
    void testLieuNull() {
        LieuEvenement lieu = new LieuEvenement(null);
        
        assertEquals("", lieu.getValeur());
        assertTrue(lieu.estVide());
    }
    
    @Test
    @DisplayName("Le lieu doit être correctement nettoyé des espaces inutiles")
    void testLieuNettoyage() {
        String valeurAvecEspaces = "  Salle de conférence  ";
        
        LieuEvenement lieu = new LieuEvenement(valeurAvecEspaces);
        
        assertEquals("Salle de conférence", lieu.getValeur());
    }
    
    @Test
    @DisplayName("Un lieu avec seulement des espaces doit être considéré comme vide")
    void testLieuAvecEspacesUniquement() {
        String valeurEspaces = "    ";
        
        LieuEvenement lieu = new LieuEvenement(valeurEspaces);
        
        assertEquals("", lieu.getValeur());
        assertTrue(lieu.estVide());
    }
}