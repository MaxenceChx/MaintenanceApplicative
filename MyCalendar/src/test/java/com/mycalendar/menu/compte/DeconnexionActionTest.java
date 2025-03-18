package com.mycalendar.menu.compte;

import com.mycalendar.CalendarManager;
import com.mycalendar.UserManager;
import com.mycalendar.menu.MenuContext;
import com.mycalendar.menu.compte.DeconnexionAction;
import com.mycalendar.valueobjects.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class DeconnexionActionTest {
    
    private MenuContext context;
    private CalendarManager calendarManager;
    private UserManager userManager;
    private Utilisateur utilisateur;
    
    @BeforeEach
    void setUp() {
        calendarManager = new CalendarManager();
        userManager = new UserManager();
        utilisateur = new Utilisateur("TestUser", "TestPass");
    }
    
    @Test
    @DisplayName("L'action doit être disponible uniquement si un utilisateur est connecté")
    void testEstDisponible() {
        DeconnexionAction action = new DeconnexionAction();
        
        // Créer un contexte avec un utilisateur connecté
        context = new MenuContext(calendarManager, userManager, new Scanner(System.in));
        context.setUtilisateurConnecte(utilisateur);
        assertTrue(action.estDisponible(context));
        
        // Déconnecter l'utilisateur
        context.setUtilisateurConnecte(null);
        assertFalse(action.estDisponible(context));
    }
    
    @Test
    @DisplayName("L'action doit avoir une description appropriée")
    void testGetDescription() {
        DeconnexionAction action = new DeconnexionAction();
        
        assertEquals("Se déconnecter", action.getDescription());
    }
    
    @Test
    @DisplayName("L'exécution de l'action avec réponse 'oui' doit déconnecter l'utilisateur et continuer")
    void testExecuterAvecOui() {
        // Simuler la réponse "oui"
        String input = "oui\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        context = new MenuContext(calendarManager, userManager, scanner);
        context.setUtilisateurConnecte(utilisateur);
        
        DeconnexionAction action = new DeconnexionAction();
        
        // Vérifier qu'un utilisateur est connecté avant
        assertNotNull(context.getUtilisateurConnecte());
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'application doit continuer
        assertTrue(resultat);
        
        // Vérifier que l'utilisateur est déconnecté
        assertNull(context.getUtilisateurConnecte());
    }
    
    @Test
    @DisplayName("L'exécution de l'action avec réponse 'non' doit déconnecter l'utilisateur et quitter")
    void testExecuterAvecNon() {
        // Simuler la réponse "non"
        String input = "non\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        context = new MenuContext(calendarManager, userManager, scanner);
        context.setUtilisateurConnecte(utilisateur);
        
        DeconnexionAction action = new DeconnexionAction();
        
        // Vérifier qu'un utilisateur est connecté avant
        assertNotNull(context.getUtilisateurConnecte());
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'application doit s'arrêter
        assertFalse(resultat);
        
        // Vérifier que l'utilisateur est déconnecté
        assertNull(context.getUtilisateurConnecte());
    }
}