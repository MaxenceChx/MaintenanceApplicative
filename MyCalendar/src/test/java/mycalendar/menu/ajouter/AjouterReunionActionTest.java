package mycalendar.menu.ajouter;

import com.mycalendar.CalendarManager;
import com.mycalendar.UserManager;
import com.mycalendar.evenements.Evenement;
import com.mycalendar.evenements.Reunion;
import com.mycalendar.menu.MenuContext;
import com.mycalendar.menu.ajouter.AjouterReunionAction;
import com.mycalendar.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AjouterReunionActionTest {
    
    private MenuContext context;
    private CalendarManager calendarManager;
    private UserManager userManager;
    private Utilisateur utilisateur;
    
    @BeforeEach
    void setUp() {
        calendarManager = new CalendarManager();
        userManager = new UserManager();
        utilisateur = new Utilisateur("TestUser", "TestPass");
        
        // Simule les entrées pour créer une réunion
        String input = "Réunion test\n2025\n6\n15\n14\n30\n90\nSalle A\nnon\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        context = new MenuContext(calendarManager, userManager, scanner);
        context.setUtilisateurConnecte(utilisateur);
    }
    
    @Test
    @DisplayName("L'action doit être disponible pour un utilisateur connecté")
    void testEstDisponible() {
        AjouterReunionAction action = new AjouterReunionAction();
        
        assertTrue(action.estDisponible(context));
        
        context.setUtilisateurConnecte(null);
        assertFalse(action.estDisponible(context));
    }
    
    @Test
    @DisplayName("L'action doit avoir une description appropriée")
    void testGetDescription() {
        AjouterReunionAction action = new AjouterReunionAction();
        
        assertEquals("Ajouter une réunion", action.getDescription());
    }
    
    @Test
    @DisplayName("L'exécution de l'action doit créer une réunion")
    void testExecuter() {
        AjouterReunionAction action = new AjouterReunionAction();
        
        // Vérifier qu'aucun événement n'existe avant
        assertEquals(0, calendarManager.getEvenements().size());
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'action s'est terminée correctement
        assertTrue(resultat);
        
        // Vérifier qu'un événement a été créé
        List<Evenement> evenements = calendarManager.getEvenements();
        assertEquals(1, evenements.size());
        
        // Vérifier que c'est une réunion avec les bonnes données
        Evenement evenement = evenements.get(0);
        assertTrue(evenement instanceof Reunion);
        assertEquals("Réunion test", evenement.getTitre().getValeur());
        assertEquals(utilisateur, evenement.getProprietaire());
        assertEquals(2025, evenement.getDate().getAnnee());
        assertEquals(6, evenement.getDate().getMois());
        assertEquals(15, evenement.getDate().getJour());
        assertEquals(14, evenement.getHeureDebut().getHeure());
        assertEquals(30, evenement.getHeureDebut().getMinute());
        assertEquals(90, evenement.getDuree().getMinutes());
        assertEquals("Salle A", evenement.getLieu().getValeur());
        
        // Vérifier que le propriétaire est inclus dans les participants
        Reunion reunion = (Reunion) evenement;
        assertTrue(reunion.estParticipant(utilisateur));
    }
    
    @Test
    @DisplayName("L'exécution de l'action avec ajout de participants doit les inclure")
    void testExecuterAvecParticipants() {
        // Simule les entrées pour créer une réunion avec participants
        String input = "Réunion test\n2025\n6\n15\n14\n30\n90\nSalle A\noui\nParticipant1\noui\nParticipant2\nnon\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        context = new MenuContext(calendarManager, userManager, scanner);
        context.setUtilisateurConnecte(utilisateur);
        
        AjouterReunionAction action = new AjouterReunionAction();
        
        // Exécuter l'action
        boolean resultat = action.executer(context);
        
        // Vérifier que l'action s'est terminée correctement
        assertTrue(resultat);
        
        // Vérifier qu'un événement a été créé
        List<Evenement> evenements = calendarManager.getEvenements();
        assertEquals(1, evenements.size());
        
        // Vérifier que c'est une réunion avec les participants
        Reunion reunion = (Reunion) evenements.get(0);
        
        // Vérifier les participants
        ParticipantsEvenement participants = reunion.getParticipants();
        assertTrue(participants.contientUtilisateurParIdentifiant("TestUser"));
        assertTrue(participants.contientUtilisateurParIdentifiant("Participant1"));
        assertTrue(participants.contientUtilisateurParIdentifiant("Participant2"));
        assertEquals(3, participants.getNombreParticipants());
    }
}