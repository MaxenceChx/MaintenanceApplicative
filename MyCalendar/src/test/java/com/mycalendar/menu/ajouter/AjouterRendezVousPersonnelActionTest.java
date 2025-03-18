package com.mycalendar.menu.ajouter;

import com.mycalendar.CalendarManager;
import com.mycalendar.UserManager;
import com.mycalendar.evenements.Evenement;
import com.mycalendar.evenements.RendezVousPersonnel;
import com.mycalendar.menu.MenuContext;
import com.mycalendar.menu.ajouter.AjouterRendezVousPersonnelAction;
import com.mycalendar.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AjouterRendezVousPersonnelActionTest {
    
    private MenuContext context;
    private CalendarManager calendarManager;
    private UserManager userManager;
    private Utilisateur utilisateur;
    
    @BeforeEach
    void setUp() {
        calendarManager = new CalendarManager();
        userManager = new UserManager();
        utilisateur = new Utilisateur("TestUser", "TestPass");
        
        // Simule les entrées pour créer un rendez-vous personnel
        String input = "Rendez-vous test\n2025\n6\n15\n14\n30\n60\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        context = new MenuContext(calendarManager, userManager, scanner);
        context.setUtilisateurConnecte(utilisateur);
    }
    
    @Test
    @DisplayName("L'action doit être disponible pour un utilisateur connecté")
    void testEstDisponible() {
        AjouterRendezVousPersonnelAction action = new AjouterRendezVousPersonnelAction();
        
        assertTrue(action.estDisponible(context));
        
        context.setUtilisateurConnecte(null);
        assertFalse(action.estDisponible(context));
    }
    
    @Test
    @DisplayName("L'action doit avoir une description appropriée")
    void testGetDescription() {
        AjouterRendezVousPersonnelAction action = new AjouterRendezVousPersonnelAction();
        
        assertEquals("Ajouter un rendez-vous personnel", action.getDescription());
    }
    
    @Test
    @DisplayName("L'exécution de l'action doit créer un rendez-vous personnel")
    void testExecuter() {
        AjouterRendezVousPersonnelAction action = new AjouterRendezVousPersonnelAction();
        
        // Vérifier qu'aucun événement n'existe avant
        assertEquals(0, calendarManager.getEvenements().size());
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'action s'est terminée correctement
        assertTrue(resultat);
        
        // Vérifier qu'un événement a été créé
        List<Evenement> evenements = calendarManager.getEvenements();
        assertEquals(1, evenements.size());
        
        // Vérifier que c'est un rendez-vous personnel avec les bonnes données
        Evenement evenement = evenements.get(0);
        assertTrue(evenement instanceof RendezVousPersonnel);
        assertEquals("Rendez-vous test", evenement.getTitre().getValeur());
        assertEquals(utilisateur, evenement.getProprietaire());
        assertEquals(2025, evenement.getDate().getAnnee());
        assertEquals(6, evenement.getDate().getMois());
        assertEquals(15, evenement.getDate().getJour());
        assertEquals(14, evenement.getHeureDebut().getHeure());
        assertEquals(30, evenement.getHeureDebut().getMinute());
        assertEquals(60, evenement.getDuree().getMinutes());
    }
}