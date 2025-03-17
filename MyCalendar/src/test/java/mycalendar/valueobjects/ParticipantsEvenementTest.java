package mycalendar.valueobjects;

import org.junit.jupiter.api.Test;

import com.mycalendar.valueobjects.ParticipantsEvenement;
import com.mycalendar.valueobjects.Utilisateur;

import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParticipantsEvenementTest {
    
    @Test
    @DisplayName("La création à partir d'une liste d'Utilisateurs doit fonctionner")
    void testCreationListeUtilisateurs() {
        Utilisateur roger = new Utilisateur("Roger", "Chat");
        Utilisateur pierre = new Utilisateur("Pierre", "KiRouhl");
        Utilisateur sophie = new Utilisateur("Sophie", "Pass123");
        List<Utilisateur> listeUtilisateurs = Arrays.asList(roger, pierre, sophie);
        
        ParticipantsEvenement participants = ParticipantsEvenement.avecUtilisateurs(listeUtilisateurs);
        
        assertEquals(3, participants.getNombreParticipants());
        assertTrue(participants.contientUtilisateur(roger));
        assertTrue(participants.contientUtilisateur(pierre));
        assertTrue(participants.contientUtilisateur(sophie));
    }
    
    @Test
    @DisplayName("La création avec une liste vide doit fonctionner")
    void testCreationListeUtilisateursVide() {
        ParticipantsEvenement participants = ParticipantsEvenement.avecUtilisateurs(Collections.emptyList());
        
        assertTrue(participants.estVide());
        assertEquals(0, participants.getNombreParticipants());
    }
    
    @Test
    @DisplayName("La création avec une liste contenant des valeurs null doit les filtrer")
    void testFiltreUtilisateursNull() {
        Utilisateur roger = new Utilisateur("Roger", "Chat");
        Utilisateur pierre = new Utilisateur("Pierre", "KiRouhl");
        List<Utilisateur> listeAvecNull = new ArrayList<>();
        listeAvecNull.add(roger);
        listeAvecNull.add(null);
        listeAvecNull.add(pierre);
        
        ParticipantsEvenement participants = ParticipantsEvenement.avecUtilisateurs(listeAvecNull);
        
        assertEquals(2, participants.getNombreParticipants());
        assertTrue(participants.contientUtilisateur(roger));
        assertTrue(participants.contientUtilisateur(pierre));
    }
    
    @Test
    @DisplayName("Deux instances avec les mêmes utilisateurs doivent être égales")
    void testEgalite() {
        Utilisateur roger = new Utilisateur("Roger", "Chat");
        Utilisateur pierre = new Utilisateur("Pierre", "KiRouhl");
        
        ParticipantsEvenement participants1 = ParticipantsEvenement.avecUtilisateurs(Arrays.asList(roger, pierre));
        ParticipantsEvenement participants2 = ParticipantsEvenement.avecUtilisateurs(Arrays.asList(roger, pierre));
        
        assertEquals(participants1, participants2);
        assertEquals(participants1.hashCode(), participants2.hashCode());
    }
    
    @Test
    @DisplayName("Deux instances avec des utilisateurs différents ne doivent pas être égales")
    void testNonEgalite() {
        Utilisateur roger = new Utilisateur("Roger", "Chat");
        Utilisateur pierre = new Utilisateur("Pierre", "KiRouhl");
        Utilisateur sophie = new Utilisateur("Sophie", "Pass123");
        
        ParticipantsEvenement participants1 = ParticipantsEvenement.avecUtilisateurs(Arrays.asList(roger, pierre));
        ParticipantsEvenement participants2 = ParticipantsEvenement.avecUtilisateurs(Arrays.asList(roger, sophie));
        
        assertNotEquals(participants1, participants2);
    }
    
    @Test
    @DisplayName("La méthode getUtilisateurs doit retourner une liste immuable")
    void testGetUtilisateursImmuable() {
        Utilisateur roger = new Utilisateur("Roger", "Chat");
        Utilisateur pierre = new Utilisateur("Pierre", "KiRouhl");
        List<Utilisateur> liste = new ArrayList<>(Arrays.asList(roger, pierre));
        
        ParticipantsEvenement participants = ParticipantsEvenement.avecUtilisateurs(liste);
        
        liste.add(new Utilisateur("Sophie", "Pass123"));
        
        assertEquals(2, participants.getNombreParticipants());
        
        List<Utilisateur> listeRetournee = participants.getUtilisateurs();
        assertThrows(UnsupportedOperationException.class, () -> {
            listeRetournee.add(new Utilisateur("Sophie", "Pass123"));
        });
    }
    
    @Test
    @DisplayName("La méthode contientUtilisateur doit détecter correctement la présence d'un utilisateur")
    void testContientUtilisateur() {
        Utilisateur roger = new Utilisateur("Roger", "Chat");
        Utilisateur pierre = new Utilisateur("Pierre", "KiRouhl");
        Utilisateur sophie = new Utilisateur("Sophie", "Pass123");
        
        ParticipantsEvenement participants = ParticipantsEvenement.avecUtilisateurs(Arrays.asList(roger, pierre));
        
        assertTrue(participants.contientUtilisateur(roger));
        assertTrue(participants.contientUtilisateur(pierre));
        assertFalse(participants.contientUtilisateur(sophie));
        assertFalse(participants.contientUtilisateur(null));
        
        Utilisateur autrePierre = new Utilisateur("Pierre", "AutreMotDePasse");
        assertTrue(participants.contientUtilisateur(autrePierre));
    }
    
    @Test
    @DisplayName("La méthode contientUtilisateurParIdentifiant doit fonctionner")
    void testContientUtilisateurParIdentifiant() {
        Utilisateur roger = new Utilisateur("Roger", "Chat");
        Utilisateur pierre = new Utilisateur("Pierre", "KiRouhl");
        
        ParticipantsEvenement participants = ParticipantsEvenement.avecUtilisateurs(Arrays.asList(roger, pierre));
        
        assertTrue(participants.contientUtilisateurParIdentifiant("Roger"));
        assertTrue(participants.contientUtilisateurParIdentifiant("Pierre"));
        assertFalse(participants.contientUtilisateurParIdentifiant("Sophie"));
        assertFalse(participants.contientUtilisateurParIdentifiant(""));
        assertFalse(participants.contientUtilisateurParIdentifiant(null));
    }
    
    @Test
    @DisplayName("La méthode toString doit retourner les identifiants des utilisateurs")
    void testToString() {
        Utilisateur roger = new Utilisateur("Roger", "Chat");
        Utilisateur pierre = new Utilisateur("Pierre", "KiRouhl");
        
        ParticipantsEvenement participants = ParticipantsEvenement.avecUtilisateurs(Arrays.asList(roger, pierre));
        
        String representation = participants.toString();
        assertTrue(representation.contains("Roger"));
        assertTrue(representation.contains("Pierre"));
    }
    
    @Test
    @DisplayName("La rétrocompatibilité avec les chaînes de caractères doit fonctionner")
    void testCompatibiliteChainesDeCaracteres() {
        String chaine = "Roger, Pierre, Sophie";
        
        ParticipantsEvenement participants = ParticipantsEvenement.fromString(chaine);
        
        assertEquals(3, participants.getNombreParticipants());
        assertTrue(participants.contientUtilisateurParIdentifiant("Roger"));
        assertTrue(participants.contientUtilisateurParIdentifiant("Pierre"));
        assertTrue(participants.contientUtilisateurParIdentifiant("Sophie"));
    }
}