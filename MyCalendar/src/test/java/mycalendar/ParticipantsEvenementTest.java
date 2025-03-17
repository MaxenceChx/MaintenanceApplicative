package mycalendar;

import org.junit.jupiter.api.Test;

import com.mycalendar.valueobjects.ParticipantsEvenement;

import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParticipantsEvenementTest {
    
    @Test
    @DisplayName("La création à partir d'une liste de noms doit fonctionner")
    void testCreationListeNoms() {
        List<String> liste = Arrays.asList("Pierre", "Sophie", "Jean");
        
        ParticipantsEvenement participants = new ParticipantsEvenement(liste);
        
        assertEquals(3, participants.getNombreParticipants());
        assertEquals(liste, participants.getParticipants());
    }
    
    @Test
    @DisplayName("La création à partir d'une chaîne délimitée doit fonctionner")
    void testCreationChaineDelimitee() {
        String chaine = "Pierre, Sophie, Jean";
        
        ParticipantsEvenement participants = ParticipantsEvenement.fromString(chaine);
        
        assertEquals(3, participants.getNombreParticipants());
        assertEquals("Pierre, Sophie, Jean", participants.toStringDelimite());
    }
    
    @Test
    @DisplayName("La création avec une liste vide doit fonctionner")
    void testCreationListeVide() {
        ParticipantsEvenement participants = new ParticipantsEvenement(Collections.emptyList());
        
        assertTrue(participants.estVide());
        assertEquals(0, participants.getNombreParticipants());
    }
    
    @Test
    @DisplayName("La création avec une chaîne vide doit fonctionner")
    void testCreationChaineVide() {
        ParticipantsEvenement participants = ParticipantsEvenement.fromString("");
        
        assertTrue(participants.estVide());
        assertEquals(0, participants.getNombreParticipants());
    }
    
    @Test
    @DisplayName("La création avec une liste contenant des valeurs null ou vides doit les filtrer")
    void testFiltreValeursInvalides() {
        List<String> liste = Arrays.asList("Pierre", null, "", "  ", "Sophie");
        
        ParticipantsEvenement participants = new ParticipantsEvenement(liste);
        
        assertEquals(2, participants.getNombreParticipants());
        assertTrue(participants.getParticipants().contains("Pierre"));
        assertTrue(participants.getParticipants().contains("Sophie"));
    }
    
    @Test
    @DisplayName("Deux instances avec les mêmes participants doivent être égales")
    void testEgalite() {
        ParticipantsEvenement participants1 = new ParticipantsEvenement(Arrays.asList("Pierre", "Sophie"));
        ParticipantsEvenement participants2 = new ParticipantsEvenement(Arrays.asList("Pierre", "Sophie"));
        
        assertEquals(participants1, participants2);
        assertEquals(participants1.hashCode(), participants2.hashCode());
    }
    
    @Test
    @DisplayName("Deux instances avec des participants différents ne doivent pas être égales")
    void testNonEgalite() {
        ParticipantsEvenement participants1 = new ParticipantsEvenement(Arrays.asList("Pierre", "Sophie"));
        ParticipantsEvenement participants2 = new ParticipantsEvenement(Arrays.asList("Pierre", "Jean"));
        
        assertNotEquals(participants1, participants2);
    }
    
    @Test
    @DisplayName("La méthode toString doit retourner la liste délimitée")
    void testToString() {
        ParticipantsEvenement participants = new ParticipantsEvenement(Arrays.asList("Pierre", "Sophie"));
        
        assertEquals("Pierre, Sophie", participants.toString());
    }
    
    @Test
    @DisplayName("Les noms des participants doivent être nettoyés des espaces")
    void testNettoyageEspaces() {
        List<String> liste = Arrays.asList(" Pierre ", "  Sophie  ");
        
        ParticipantsEvenement participants = new ParticipantsEvenement(liste);
        
        List<String> resultat = participants.getParticipants();
        assertEquals("Pierre", resultat.get(0));
        assertEquals("Sophie", resultat.get(1));
    }
    
    @Test
    @DisplayName("La liste des participants doit être immuable")
    void testListeImmuable() {
        List<String> liste = new java.util.ArrayList<>(Arrays.asList("Pierre", "Sophie"));
        ParticipantsEvenement participants = new ParticipantsEvenement(liste);
        
        liste.add("Jean");
        
        assertEquals(2, participants.getNombreParticipants());
        assertFalse(participants.getParticipants().contains("Jean"));
        
        assertThrows(UnsupportedOperationException.class, () -> {
            participants.getParticipants().add("Michel");
        });
    }
}