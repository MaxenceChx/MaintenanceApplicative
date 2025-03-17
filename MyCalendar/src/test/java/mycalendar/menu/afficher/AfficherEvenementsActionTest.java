package mycalendar.menu.afficher;

import com.mycalendar.CalendarManager;
import com.mycalendar.UserManager;
import com.mycalendar.evenements.RendezVousPersonnel;
import com.mycalendar.menu.MenuContext;
import com.mycalendar.menu.afficher.AfficherEvenementsAction;
import com.mycalendar.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AfficherEvenementsActionTest {
    
    private MenuContext context;
    private CalendarManager calendarManager;
    private UserManager userManager;
    private Utilisateur utilisateur;
    private ByteArrayOutputStream outContent;
    
    @BeforeEach
    void setUp() {
        calendarManager = new CalendarManager();
        userManager = new UserManager();
        utilisateur = new Utilisateur("TestUser", "TestPass");
        
        // Ajouter un événement pour le test
        RendezVousPersonnel rdv = new RendezVousPersonnel(
            new TitreEvenement("Rendez-vous test"),
            utilisateur,
            new DateEvenement(2025, 6, 15),
            new HeureDebut(14, 30),
            new DureeEvenement(60)
        );
        calendarManager.ajouterEvenement(rdv);
        
        // Capturer la sortie standard pour les tests
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }
    
    @Test
    @DisplayName("L'action doit être disponible pour un utilisateur connecté")
    void testEstDisponible() {
        AfficherEvenementsAction action = new AfficherEvenementsAction();
        
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
        AfficherEvenementsAction action = new AfficherEvenementsAction();
        
        assertEquals("Voir les événements", action.getDescription());
    }
    
    @Test
    @DisplayName("L'exécution de l'action doit afficher le sous-menu de visualisation")
    void testExecuter() {
        // Simuler l'entrée "5" pour retourner au menu principal
        String input = "5\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        context = new MenuContext(calendarManager, userManager, scanner);
        context.setUtilisateurConnecte(utilisateur);
        
        AfficherEvenementsAction action = new AfficherEvenementsAction();
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'action s'est terminée correctement
        assertTrue(resultat);
        
        // Vérifier que la sortie contient le menu de visualisation
        String output = outContent.toString();
        assertTrue(output.contains("Menu de visualisation d'Événements"));
        assertTrue(output.contains("Afficher TOUS les événements"));
        assertTrue(output.contains("Afficher les événements d'un MOIS précis"));
        assertTrue(output.contains("Afficher les événements d'une SEMAINE précise"));
        assertTrue(output.contains("Afficher les événements d'un JOUR précis"));
    }
    
    @Test
    @DisplayName("L'exécution de l'action avec sélection d'une sous-action doit fonctionner")
    void testExecuterAvecSousAction() {
        // Simuler la sélection de l'action "Afficher TOUS les événements" (1)
        String input = "1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        context = new MenuContext(calendarManager, userManager, scanner);
        context.setUtilisateurConnecte(utilisateur);
        
        AfficherEvenementsAction action = new AfficherEvenementsAction();
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'action s'est terminée correctement
        assertTrue(resultat);
        
        // Vérifier que la sortie contient l'événement
        String output = outContent.toString();
        assertTrue(output.contains("Rendez-vous test"));
    }
    
    @Test
    @DisplayName("L'action doit gérer correctement les entrées invalides")
    void testExecuterAvecEntreesInvalides() {
        // Simuler une entrée invalide puis retour au menu
        String input = "texte\n5\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        context = new MenuContext(calendarManager, userManager, scanner);
        context.setUtilisateurConnecte(utilisateur);
        
        AfficherEvenementsAction action = new AfficherEvenementsAction();
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'action s'est terminée correctement malgré l'erreur
        assertTrue(resultat);
        
        // Vérifier que la sortie contient un message d'erreur
        String output = outContent.toString();
        assertTrue(output.contains("Choix invalide"));
    }
}