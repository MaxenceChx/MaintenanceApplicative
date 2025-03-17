package mycalendar.menu.ajouter;

import com.mycalendar.CalendarManager;
import com.mycalendar.UserManager;
import com.mycalendar.evenements.Evenement;
import com.mycalendar.evenements.Tache;
import com.mycalendar.menu.MenuContext;
import com.mycalendar.menu.ajouter.AjouterTacheAction;
import com.mycalendar.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AjouterTacheActionTest {
    
    private MenuContext context;
    private CalendarManager calendarManager;
    private UserManager userManager;
    private Utilisateur utilisateur;
    
    @BeforeEach
    void setUp() {
        calendarManager = new CalendarManager();
        userManager = new UserManager();
        utilisateur = new Utilisateur("TestUser", "TestPass");
        
        // Simuler les entrées pour créer une tâche (avec priorité haute = 1)
        String input = "Rédiger rapport\n2025\n6\n15\n14\n30\n120\n1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        context = new MenuContext(calendarManager, userManager, scanner);
        context.setUtilisateurConnecte(utilisateur);
    }
    
    @Test
    @DisplayName("L'action doit être disponible pour un utilisateur connecté")
    void testEstDisponible() {
        AjouterTacheAction action = new AjouterTacheAction();
        
        assertTrue(action.estDisponible(context));
        
        context.setUtilisateurConnecte(null);
        assertFalse(action.estDisponible(context));
    }
    
    @Test
    @DisplayName("L'action doit avoir une description appropriée")
    void testGetDescription() {
        AjouterTacheAction action = new AjouterTacheAction();
        
        assertEquals("Ajouter une tâche", action.getDescription());
    }
    
    @Test
    @DisplayName("L'exécution de l'action doit créer une tâche avec priorité haute")
    void testExecuterAvecPrioriteHaute() {
        AjouterTacheAction action = new AjouterTacheAction();
        
        // Vérifier qu'aucun événement n'existe avant
        assertEquals(0, calendarManager.getEvenements().size());
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'action s'est terminée correctement
        assertTrue(resultat);
        
        // Vérifier qu'un événement a été créé
        List<Evenement> evenements = calendarManager.getEvenements();
        assertEquals(1, evenements.size());
        
        // Vérifier que c'est une tâche avec les bonnes données
        Evenement evenement = evenements.get(0);
        assertTrue(evenement instanceof Tache);
        assertEquals("Rédiger rapport", evenement.getTitre().getValeur());
        assertEquals(utilisateur, evenement.getProprietaire());
        assertEquals(2025, evenement.getDate().getAnnee());
        assertEquals(6, evenement.getDate().getMois());
        assertEquals(15, evenement.getDate().getJour());
        assertEquals(14, evenement.getHeureDebut().getHeure());
        assertEquals(30, evenement.getHeureDebut().getMinute());
        assertEquals(120, evenement.getDuree().getMinutes());
        
        // Vérifier la priorité
        Tache tache = (Tache) evenement;
        assertEquals(PrioriteTache.HAUTE, tache.getPriorite());
    }
    
    @Test
    @DisplayName("L'exécution de l'action doit créer une tâche avec priorité moyenne")
    void testExecuterAvecPrioriteMoyenne() {
        // Simuler les entrées pour créer une tâche avec priorité moyenne = 2
        String input = "Rédiger rapport\n2025\n6\n15\n14\n30\n120\n2\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        context = new MenuContext(calendarManager, userManager, scanner);
        context.setUtilisateurConnecte(utilisateur);
        
        AjouterTacheAction action = new AjouterTacheAction();
        
        // Exécuter l'action
        action.executer(context);
        
        // Vérifier qu'un événement a été créé
        List<Evenement> evenements = calendarManager.getEvenements();
        assertEquals(1, evenements.size());
        
        // Vérifier la priorité
        Tache tache = (Tache) evenements.get(0);
        assertEquals(PrioriteTache.MOYENNE, tache.getPriorite());
    }
    
    @Test
    @DisplayName("L'exécution de l'action doit créer une tâche avec priorité basse")
    void testExecuterAvecPrioriteBasse() {
        // Simuler les entrées pour créer une tâche avec priorité basse = 3
        String input = "Rédiger rapport\n2025\n6\n15\n14\n30\n120\n3\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        context = new MenuContext(calendarManager, userManager, scanner);
        context.setUtilisateurConnecte(utilisateur);
        
        AjouterTacheAction action = new AjouterTacheAction();
        
        // Exécuter l'action
        action.executer(context);
        
        // Vérifier qu'un événement a été créé
        List<Evenement> evenements = calendarManager.getEvenements();
        assertEquals(1, evenements.size());
        
        // Vérifier la priorité
        Tache tache = (Tache) evenements.get(0);
        assertEquals(PrioriteTache.BASSE, tache.getPriorite());
    }
    
    @Test
    @DisplayName("L'exécution de l'action avec une priorité invalide doit utiliser la priorité moyenne par défaut")
    void testExecuterAvecPrioriteInvalide() {
        // Simuler les entrées avec une priorité invalide = 4
        String input = "Rédiger rapport\n2025\n6\n15\n14\n30\n120\n4\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        context = new MenuContext(calendarManager, userManager, scanner);
        context.setUtilisateurConnecte(utilisateur);
        
        AjouterTacheAction action = new AjouterTacheAction();
        
        // Exécuter l'action
        action.executer(context);
        
        // Vérifier qu'un événement a été créé
        List<Evenement> evenements = calendarManager.getEvenements();
        assertEquals(1, evenements.size());
        
        // Vérifier que la priorité par défaut est utilisée
        Tache tache = (Tache) evenements.get(0);
        assertEquals(PrioriteTache.MOYENNE, tache.getPriorite());
    }
    
    @Test
    @DisplayName("L'exécution de l'action avec des données invalides doit être gérée correctement")
    void testExecuterAvecDonneesInvalides() {
        // Simuler les entrées avec une date invalide
        String input = "Rédiger rapport\n2025\n13\n15\n14\n30\n120\n1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        context = new MenuContext(calendarManager, userManager, scanner);
        context.setUtilisateurConnecte(utilisateur);
        
        AjouterTacheAction action = new AjouterTacheAction();
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'action s'est terminée correctement malgré l'erreur
        assertTrue(resultat);
        
        // Vérifier qu'aucun événement n'a été créé
        assertEquals(0, calendarManager.getEvenements().size());
    }
}