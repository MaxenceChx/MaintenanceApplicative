package mycalendar;

import org.junit.jupiter.api.Test;

import com.mycalendar.valueobjects.TitreEvenement;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class TitreEvenementTest {
    
    @Test
    @DisplayName("La création d'un titre valide doit fonctionner")
    void testCreationTitreValide() {
        String valeur = "Réunion d'équipe";
        
        TitreEvenement titre = new TitreEvenement(valeur);
        
        assertEquals(valeur, titre.getValeur());
    }
    
    @Test
    @DisplayName("Deux instances avec les mêmes valeurs doivent être égales")
    void testEgalite() {
        TitreEvenement titre1 = new TitreEvenement("Rendez-vous médecin");
        TitreEvenement titre2 = new TitreEvenement("Rendez-vous médecin");
        
        assertEquals(titre1, titre2);
        assertEquals(titre1.hashCode(), titre2.hashCode());
    }
    
    @Test
    @DisplayName("Deux instances avec des valeurs différentes ne doivent pas être égales")
    void testNonEgalite() {
        TitreEvenement titre1 = new TitreEvenement("Rendez-vous médecin");
        TitreEvenement titre2 = new TitreEvenement("Rendez-vous dentiste");
        
        assertNotEquals(titre1, titre2);
    }
    
    @Test
    @DisplayName("La méthode toString doit retourner la valeur du titre")
    void testToString() {
        String valeur = "Sprint planning";
        TitreEvenement titre = new TitreEvenement(valeur);
        
        assertEquals(valeur, titre.toString());
    }
    
    @Test
    @DisplayName("La création avec une valeur null doit lancer une exception")
    void testTitreNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TitreEvenement(null);
        });
    }
    
    @Test
    @DisplayName("La création avec une chaîne vide doit lancer une exception")
    void testTitreVide() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TitreEvenement("");
        });
    }
    
    @Test
    @DisplayName("La création avec une chaîne ne contenant que des espaces doit lancer une exception")
    void testTitreEspaces() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TitreEvenement("   ");
        });
    }
    
    @Test
    @DisplayName("La création d'un titre avec des caractères spéciaux doit fonctionner")
    void testTitreAvecCaracteresSpeciaux() {
        String valeur = "Réunion #42 & test @ projet";
        
        TitreEvenement titre = new TitreEvenement(valeur);
        
        assertEquals(valeur, titre.getValeur());
    }
    
    @Test
    @DisplayName("Un titre trop long doit être tronqué")
    void testTitreTropLong() {
        String valeurLongue = "Un titre extrêmement long qui dépasse la limite fixée pour les titres dans notre application " +
                              "de calendrier, car nous voulons éviter des titres qui prennent trop de place dans l'interface " +
                              "utilisateur et qui pourraient causer des problèmes d'affichage.";
        
        TitreEvenement titre = new TitreEvenement(valeurLongue);
        
        assertTrue(titre.getValeur().length() <= 100);
        assertTrue(titre.getValeur().endsWith("..."));
        assertTrue(valeurLongue.startsWith(titre.getValeur().substring(0, titre.getValeur().length() - 3))); // Vérifie que c'est bien le début du titre original
    }
}