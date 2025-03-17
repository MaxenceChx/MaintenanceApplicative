package mycalendar;

import com.mycalendar.UserManager;
import com.mycalendar.valueobjects.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {
    
    private UserManager userManager;
    
    @BeforeEach
    void setUp() {
        userManager = new UserManager();
    }
    
    @Test
    @DisplayName("Les utilisateurs prédéfinis doivent être créés et vérifiables")
    void testUtilisateursPredefinis() {
        // Les utilisateurs prédéfinis sont Roger/Chat et Pierre/KiRouhl
        assertTrue(userManager.verifierAuthentification("Roger", "Chat"));
        assertTrue(userManager.verifierAuthentification("Pierre", "KiRouhl"));
        assertFalse(userManager.verifierAuthentification("Roger", "MotDePasseIncorrect"));
        assertFalse(userManager.verifierAuthentification("UtilisateurInexistant", "MotDePasse"));
    }
    
    @Test
    @DisplayName("La création d'un compte utilisateur doit fonctionner")
    void testCreerCompte() {
        String nomUtilisateur = "Sophie";
        String motDePasse = "MotDePasse123";
        
        boolean resultat = userManager.creerCompte(nomUtilisateur, motDePasse);
        
        assertTrue(resultat);
        assertTrue(userManager.verifierAuthentification(nomUtilisateur, motDePasse));
        assertEquals(3, userManager.getNombreUtilisateurs()); // 2 prédéfinis + 1 nouveau
    }
    
    @Test
    @DisplayName("La création d'un compte avec un nom d'utilisateur déjà existant doit échouer")
    void testCreerCompteNomExistant() {
        String nomUtilisateur = "Roger";
        String motDePasse = "NouveauMotDePasse";
        
        boolean resultat = userManager.creerCompte(nomUtilisateur, motDePasse);
        
        assertFalse(resultat);
        assertFalse(userManager.verifierAuthentification(nomUtilisateur, motDePasse));
        assertTrue(userManager.verifierAuthentification(nomUtilisateur, "Chat"));
    }
    
    @Test
    @DisplayName("La création d'un compte avec des données invalides doit échouer")
    void testCreerCompteDonneesInvalides() {
        assertFalse(userManager.creerCompte("", "MotDePasse"));
        assertFalse(userManager.creerCompte("Utilisateur", ""));
        assertFalse(userManager.creerCompte(null, "MotDePasse"));
        assertFalse(userManager.creerCompte("Utilisateur", null));
    }
    
    @Test
    @DisplayName("La vérification d'authentification avec des identifiants corrects doit réussir")
    void testVerifierAuthentificationCorrect() {
        String nomUtilisateur = "Jean";
        String motDePasse = "MonMotDePasse";
        userManager.creerCompte(nomUtilisateur, motDePasse);
        
        assertTrue(userManager.verifierAuthentification(nomUtilisateur, motDePasse));
    }
    
    @Test
    @DisplayName("La vérification d'authentification avec un mot de passe incorrect doit échouer")
    void testVerifierAuthentificationMotDePasseIncorrect() {
        String nomUtilisateur = "Jean";
        String motDePasse = "MonMotDePasse";
        userManager.creerCompte(nomUtilisateur, motDePasse);
        
        assertFalse(userManager.verifierAuthentification(nomUtilisateur, "MotDePasseIncorrect"));
    }
    
    @Test
    @DisplayName("La vérification d'authentification avec un utilisateur inexistant doit échouer")
    void testVerifierAuthentificationUtilisateurInexistant() {
        assertFalse(userManager.verifierAuthentification("UtilisateurInexistant", "MotDePasse"));
    }
    
    @Test
    @DisplayName("La recherche d'un utilisateur existant doit réussir")
    void testRechercherUtilisateurExistant() {
        String nomUtilisateur = "Roger";
        
        Utilisateur utilisateur = userManager.rechercherUtilisateur(nomUtilisateur);
        
        assertNotNull(utilisateur);
        assertEquals(nomUtilisateur, utilisateur.getIdentifiant());
    }
    
    @Test
    @DisplayName("La recherche d'un utilisateur inexistant doit retourner null")
    void testRechercherUtilisateurInexistant() {
        Utilisateur utilisateur = userManager.rechercherUtilisateur("UtilisateurInexistant");
        
        assertNull(utilisateur);
    }
    
    @Test
    @DisplayName("Le nombre d'utilisateurs doit être correctement géré")
    void testNombreUtilisateurs() {
        assertEquals(2, userManager.getNombreUtilisateurs());
        
        userManager.creerCompte("Utilisateur1", "MotDePasse1");
        userManager.creerCompte("Utilisateur2", "MotDePasse2");
        
        assertEquals(4, userManager.getNombreUtilisateurs());
    }
    
    @Test
    @DisplayName("La création ne doit pas dépasser la capacité maximale")
    void testCapaciteMaximum() {
        int capaciteMaximale = UserManager.CAPACITE_MAX;
        
        int comptesACreer = capaciteMaximale - userManager.getNombreUtilisateurs();
        for (int i = 0; i < comptesACreer; i++) {
            assertTrue(userManager.creerCompte("User" + i, "Pass" + i));
        }
        
        assertFalse(userManager.creerCompte("UserEnTrop", "PassEnTrop"));
        assertEquals(capaciteMaximale, userManager.getNombreUtilisateurs());
    }
}