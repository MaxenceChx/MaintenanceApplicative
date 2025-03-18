package com.mycalendar.menu.supprimer;

import com.mycalendar.CalendarManager;
import com.mycalendar.UserManager;
import com.mycalendar.evenements.RendezVousPersonnel;
import com.mycalendar.menu.MenuContext;
import com.mycalendar.menu.supprimer.SupprimerEvenementAction;
import com.mycalendar.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class SupprimerEvenementActionTest {
    
    private MenuContext context;
    private CalendarManager calendarManager;
    private UserManager userManager;
    private Utilisateur utilisateur;
    private EventId eventId;
    
    @BeforeEach
    void setUp() {
        calendarManager = new CalendarManager();
        userManager = new UserManager();
        utilisateur = new Utilisateur("TestUser", "TestPass");
        
        // Créer un événement pour les tests
        RendezVousPersonnel rdv = new RendezVousPersonnel(
            new TitreEvenement("Rendez-vous test"),
            utilisateur,
            new DateEvenement(2025, 6, 15),
            new HeureDebut(14, 30),
            new DureeEvenement(60)
        );
        
        eventId = rdv.getId();
        calendarManager.ajouterEvenement(rdv);
        
        // Simuler l'entrée "1" pour supprimer le premier événement
        Scanner scanner = new Scanner(new ByteArrayInputStream("1\n".getBytes()));
        context = new MenuContext(calendarManager, userManager, scanner);
        context.setUtilisateurConnecte(utilisateur);
    }
    
    @Test
    @DisplayName("L'action doit être disponible pour un utilisateur connecté")
    void testEstDisponible() {
        SupprimerEvenementAction action = new SupprimerEvenementAction();
        
        assertTrue(action.estDisponible(context));
        
        context.setUtilisateurConnecte(null);
        assertFalse(action.estDisponible(context));
    }
    
    @Test
    @DisplayName("L'action doit avoir une description appropriée")
    void testGetDescription() {
        SupprimerEvenementAction action = new SupprimerEvenementAction();
        
        assertEquals("Supprimer un événement", action.getDescription());
    }
    
    @Test
    @DisplayName("L'exécution de l'action doit supprimer l'événement")
    void testExecuter() {
        SupprimerEvenementAction action = new SupprimerEvenementAction();
        
        // Vérifier que l'événement existe avant
        assertEquals(1, calendarManager.getEvenements().size());
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'action s'est terminée correctement
        assertTrue(resultat);
        
        // Vérifier que l'événement a été supprimé
        assertEquals(0, calendarManager.getEvenements().size());
    }
    
    @Test
    @DisplayName("L'action doit gérer le cas où l'utilisateur n'a pas d'événements")
    void testExecuterSansEvenements() {
        // Créer un contexte avec un utilisateur qui n'a pas d'événements
        Utilisateur utilisateurSansEvenements = new Utilisateur("UserSansEvenement", "pass");
        context.setUtilisateurConnecte(utilisateurSansEvenements);
        
        SupprimerEvenementAction action = new SupprimerEvenementAction();
        
        // L'exécution ne devrait pas échouer
        boolean resultat = action.executer(context);
        assertTrue(resultat);
    }
}