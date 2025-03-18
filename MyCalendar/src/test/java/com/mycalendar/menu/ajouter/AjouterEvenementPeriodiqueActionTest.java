package com.mycalendar.menu.ajouter;

import com.mycalendar.CalendarManager;
import com.mycalendar.UserManager;
import com.mycalendar.evenements.Evenement;
import com.mycalendar.evenements.EvenementPeriodique;
import com.mycalendar.menu.MenuContext;
import com.mycalendar.menu.ajouter.AjouterEvenementPeriodiqueAction;
import com.mycalendar.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AjouterEvenementPeriodiqueActionTest {
    
    private MenuContext context;
    private CalendarManager calendarManager;
    private UserManager userManager;
    private Utilisateur utilisateur;
    
    @BeforeEach
    void setUp() {
        calendarManager = new CalendarManager();
        userManager = new UserManager();
        utilisateur = new Utilisateur("TestUser", "TestPass");
        
        // Simule les entrées pour créer un événement périodique
        String input = "Stand-up hebdomadaire\n2025\n6\n1\n9\n0\n7\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        context = new MenuContext(calendarManager, userManager, scanner);
        context.setUtilisateurConnecte(utilisateur);
    }
    
    @Test
    @DisplayName("L'action doit être disponible pour un utilisateur connecté")
    void testEstDisponible() {
        AjouterEvenementPeriodiqueAction action = new AjouterEvenementPeriodiqueAction();
        
        assertTrue(action.estDisponible(context));
        
        context.setUtilisateurConnecte(null);
        assertFalse(action.estDisponible(context));
    }
    
    @Test
    @DisplayName("L'action doit avoir une description appropriée")
    void testGetDescription() {
        AjouterEvenementPeriodiqueAction action = new AjouterEvenementPeriodiqueAction();
        
        assertEquals("Ajouter un événement périodique", action.getDescription());
    }
    
    @Test
    @DisplayName("L'exécution de l'action doit créer un événement périodique")
    void testExecuter() {
        AjouterEvenementPeriodiqueAction action = new AjouterEvenementPeriodiqueAction();
        
        // Vérifier qu'aucun événement n'existe avant
        assertEquals(0, calendarManager.getEvenements().size());
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'action s'est terminée correctement
        assertTrue(resultat);
        
        // Vérifier qu'un événement a été créé
        List<Evenement> evenements = calendarManager.getEvenements();
        assertEquals(1, evenements.size());
        
        // Vérifier que c'est un événement périodique avec les bonnes données
        Evenement evenement = evenements.get(0);
        assertTrue(evenement instanceof EvenementPeriodique);
        assertEquals("Stand-up hebdomadaire", evenement.getTitre().getValeur());
        assertEquals(utilisateur, evenement.getProprietaire());
        assertEquals(2025, evenement.getDate().getAnnee());
        assertEquals(6, evenement.getDate().getMois());
        assertEquals(1, evenement.getDate().getJour());
        assertEquals(9, evenement.getHeureDebut().getHeure());
        assertEquals(0, evenement.getHeureDebut().getMinute());
        
        // Vérifier la fréquence
        FrequenceEvenement frequence = evenement.getFrequence();
        assertTrue(frequence.estPeriodique());
        assertEquals(7, frequence.getJoursEntrePeriodes());
    }
    
    @Test
    @DisplayName("L'exécution de l'action avec une fréquence invalide doit être gérée")
    void testExecuterAvecFrequenceInvalide() {
        // Simule les entrées avec une fréquence négative
        String input = "Stand-up hebdomadaire\n2025\n6\n1\n9\n0\n-1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        context = new MenuContext(calendarManager, userManager, scanner);
        context.setUtilisateurConnecte(utilisateur);
        
        AjouterEvenementPeriodiqueAction action = new AjouterEvenementPeriodiqueAction();
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'action s'est terminée correctement
        assertTrue(resultat);
        
        // Vérifier qu'aucun événement n'a été créé
        assertEquals(0, calendarManager.getEvenements().size());
    }
}