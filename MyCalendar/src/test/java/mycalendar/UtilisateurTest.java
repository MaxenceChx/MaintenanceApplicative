package mycalendar;

import org.junit.jupiter.api.Test;

import com.mycalendar.valueobjects.Utilisateur;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class UtilisateurTest {
    
    @Test
    @DisplayName("La création d'un utilisateur valide doit fonctionner")
    void testCreationUtilisateurValide() {
        String identifiant = "Roger";
        String motDePasse = "Chat";
        
        Utilisateur utilisateur = new Utilisateur(identifiant, motDePasse);
        
        assertEquals(identifiant, utilisateur.getIdentifiant());
        assertTrue(utilisateur.verifierMotDePasse(motDePasse));
    }
    
    @Test
    @DisplayName("La vérification d'un mot de passe incorrect doit échouer")
    void testVerificationMotDePasseIncorrect() {
        Utilisateur utilisateur = new Utilisateur("Pierre", "KiRouhl");
        
        assertFalse(utilisateur.verifierMotDePasse("MotDePasseIncorrect"));
    }
    
    @Test
    @DisplayName("Deux utilisateurs avec les mêmes identifiants doivent être égaux")
    void testEgalite() {
        Utilisateur utilisateur1 = new Utilisateur("Roger", "MotDePasse1");
        Utilisateur utilisateur2 = new Utilisateur("Roger", "MotDePasse2");
        
        assertEquals(utilisateur1, utilisateur2);
        assertEquals(utilisateur1.hashCode(), utilisateur2.hashCode());
    }
    
    @Test
    @DisplayName("Deux utilisateurs avec des identifiants différents ne doivent pas être égaux")
    void testNonEgalite() {
        Utilisateur utilisateur1 = new Utilisateur("Roger", "Chat");
        Utilisateur utilisateur2 = new Utilisateur("Pierre", "Chat");
        
        assertNotEquals(utilisateur1, utilisateur2);
    }
    
    @Test
    @DisplayName("La méthode toString ne doit pas exposer le mot de passe")
    void testToString() {
        String identifiant = "Sophie";
        String motDePasse = "MotDePasseSecret";
        Utilisateur utilisateur = new Utilisateur(identifiant, motDePasse);
        
        String representation = utilisateur.toString();
        assertTrue(representation.contains(identifiant));
        assertFalse(representation.contains(motDePasse));
    }
    
    @Test
    @DisplayName("La création avec un identifiant null doit lancer une exception")
    void testIdentifiantNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Utilisateur(null, "MotDePasse");
        });
    }
    
    @Test
    @DisplayName("La création avec un identifiant vide doit lancer une exception")
    void testIdentifiantVide() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Utilisateur("", "MotDePasse");
        });
    }
    
    @Test
    @DisplayName("La création avec un identifiant ne contenant que des espaces doit lancer une exception")
    void testIdentifiantEspaces() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Utilisateur("   ", "MotDePasse");
        });
    }
    
    @Test
    @DisplayName("La création avec un mot de passe null doit lancer une exception")
    void testMotDePasseNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Utilisateur("Roger", null);
        });
    }
    
    @Test
    @DisplayName("La création avec un mot de passe vide doit lancer une exception")
    void testMotDePasseVide() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Utilisateur("Roger", "");
        });
    }
    
    @Test
    @DisplayName("L'identifiant de l'utilisateur doit être correctement nettoyé des espaces inutiles")
    void testIdentifiantNettoyage() {
        String identifiantAvecEspaces = "  Roger  ";
        
        Utilisateur utilisateur = new Utilisateur(identifiantAvecEspaces, "MotDePasse");
        
        assertEquals("Roger", utilisateur.getIdentifiant());
    }
}