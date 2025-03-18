package com.mycalendar.menu.compte;

import com.mycalendar.CalendarManager;
import com.mycalendar.UserManager;
import com.mycalendar.menu.MenuContext;
import com.mycalendar.menu.compte.CreationCompteAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class CreationCompteActionTest {
    
    private MenuContext context;
    private CalendarManager calendarManager;
    private UserManager userManager;
    
    @BeforeEach
    void setUp() {
        calendarManager = new CalendarManager();
        userManager = new UserManager();
    }
    
    @Test
    @DisplayName("L'action doit être disponible uniquement si aucun utilisateur n'est connecté")
    void testEstDisponible() {
        CreationCompteAction action = new CreationCompteAction();
        
        // Créer un contexte sans utilisateur connecté
        context = new MenuContext(calendarManager, userManager, new Scanner(System.in));
        assertTrue(action.estDisponible(context));
        
        // Connecter un utilisateur
        context.setUtilisateurConnecte(new com.mycalendar.valueobjects.Utilisateur("TestUser", "TestPass"));
        assertFalse(action.estDisponible(context));
    }
    
    @Test
    @DisplayName("L'action doit avoir une description appropriée")
    void testGetDescription() {
        CreationCompteAction action = new CreationCompteAction();
        
        assertEquals("Créer un compte", action.getDescription());
    }
    
    @Test
    @DisplayName("L'exécution de l'action avec des informations valides doit créer et connecter l'utilisateur")
    void testExecuterAvecInformationsValides() {
        // Simuler les entrées pour la création d'un compte
        String input = "NouvelUtilisateur\nMotDePasse123\nMotDePasse123\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        context = new MenuContext(calendarManager, userManager, scanner);
        
        CreationCompteAction action = new CreationCompteAction();
        
        // Vérifier qu'aucun utilisateur n'est connecté avant
        assertNull(context.getUtilisateurConnecte());
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'action s'est terminée correctement
        assertTrue(resultat);
        
        // Vérifier qu'un utilisateur est maintenant connecté
        assertNotNull(context.getUtilisateurConnecte());
        assertEquals("NouvelUtilisateur", context.getUtilisateurConnecte().getIdentifiant());
    }
    
    @Test
    @DisplayName("L'exécution de l'action avec des mots de passe différents ne doit pas créer de compte")
    void testExecuterAvecMotsDePasseDifferents() {
        // Simuler les entrées avec des mots de passe qui ne correspondent pas
        String input = "NouvelUtilisateur\nMotDePasse123\nMotDePasseDifferent\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        context = new MenuContext(calendarManager, userManager, scanner);
        
        int nombreUtilisateursAvant = userManager.getNombreUtilisateurs();
        
        CreationCompteAction action = new CreationCompteAction();
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'action s'est terminée correctement
        assertTrue(resultat);
        
        // Vérifier qu'aucun utilisateur n'est connecté
        assertNull(context.getUtilisateurConnecte());
        
        // Vérifier qu'aucun compte n'a été créé
        assertEquals(nombreUtilisateursAvant, userManager.getNombreUtilisateurs());
    }
    
    @Test
    @DisplayName("L'exécution de l'action avec un nom d'utilisateur déjà existant ne doit pas créer de compte")
    void testExecuterAvecNomExistant() {
        // Créer d'abord un utilisateur
        userManager.creerCompte("UtilisateurExistant", "MotDePasse");
        
        // Simuler les entrées avec un nom d'utilisateur qui existe déjà
        String input = "UtilisateurExistant\nMotDePasse123\nMotDePasse123\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        context = new MenuContext(calendarManager, userManager, scanner);
        
        int nombreUtilisateursAvant = userManager.getNombreUtilisateurs();
        
        CreationCompteAction action = new CreationCompteAction();
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'action s'est terminée correctement
        assertTrue(resultat);
        
        // Vérifier qu'aucun utilisateur n'est connecté
        assertNull(context.getUtilisateurConnecte());
        
        // Vérifier qu'aucun compte n'a été créé
        assertEquals(nombreUtilisateursAvant, userManager.getNombreUtilisateurs());
    }
}