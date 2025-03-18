package com.mycalendar.menu.compte;

import com.mycalendar.CalendarManager;
import com.mycalendar.UserManager;
import com.mycalendar.menu.MenuContext;
import com.mycalendar.menu.compte.ConnexionAction;
import com.mycalendar.valueobjects.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ConnexionActionTest {
    
    private MenuContext context;
    private CalendarManager calendarManager;
    private UserManager userManager;
    
    @BeforeEach
    void setUp() {
        calendarManager = new CalendarManager();
        userManager = new UserManager();
        
        // Créer un utilisateur pour le test
        userManager.creerCompte("TestUser", "TestPass");
    }
    
    @Test
    @DisplayName("L'action doit être disponible uniquement si aucun utilisateur n'est connecté")
    void testEstDisponible() {
        ConnexionAction action = new ConnexionAction();
        
        // Créer un contexte sans utilisateur connecté
        context = new MenuContext(calendarManager, userManager, new Scanner(System.in));
        assertTrue(action.estDisponible(context));
        
        // Connecter un utilisateur
        context.setUtilisateurConnecte(new Utilisateur("TestUser", "TestPass"));
        assertFalse(action.estDisponible(context));
    }
    
    @Test
    @DisplayName("L'action doit avoir une description appropriée")
    void testGetDescription() {
        ConnexionAction action = new ConnexionAction();
        
        assertEquals("Se connecter", action.getDescription());
    }
    
    @Test
    @DisplayName("L'exécution de l'action avec des identifiants valides doit connecter l'utilisateur")
    void testExecuterAvecIdentifiantsValides() {
        // Simuler les entrées d'un utilisateur valide
        String input = "TestUser\nTestPass\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        context = new MenuContext(calendarManager, userManager, scanner);
        
        ConnexionAction action = new ConnexionAction();
        
        // Vérifier qu'aucun utilisateur n'est connecté avant
        assertNull(context.getUtilisateurConnecte());
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'action s'est terminée correctement
        assertTrue(resultat);
        
        // Vérifier qu'un utilisateur est maintenant connecté
        assertNotNull(context.getUtilisateurConnecte());
        assertEquals("TestUser", context.getUtilisateurConnecte().getIdentifiant());
    }
    
    @Test
    @DisplayName("L'exécution de l'action avec des identifiants invalides ne doit pas connecter l'utilisateur")
    void testExecuterAvecIdentifiantsInvalides() {
        // Simuler les entrées d'un utilisateur invalide
        String input = "FauxUser\nFauxPass\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        context = new MenuContext(calendarManager, userManager, scanner);
        
        ConnexionAction action = new ConnexionAction();
        
        // Vérifier qu'aucun utilisateur n'est connecté avant
        assertNull(context.getUtilisateurConnecte());
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'action s'est terminée correctement
        assertTrue(resultat);
        
        // Vérifier qu'aucun utilisateur n'est connecté après
        assertNull(context.getUtilisateurConnecte());
    }
}