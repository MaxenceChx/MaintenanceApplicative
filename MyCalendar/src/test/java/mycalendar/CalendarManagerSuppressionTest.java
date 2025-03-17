package mycalendar;

import com.mycalendar.CalendarManager;
import com.mycalendar.evenements.*;
import com.mycalendar.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarManagerSuppressionTest {
    
    private CalendarManager calendarManager;
    private Utilisateur roger;
    private Utilisateur pierre;
    private Evenement rdvRoger;
    private Evenement reunionPierre;
    private Evenement periodiquePierre;
    
    @BeforeEach
    void setUp() {
        calendarManager = new CalendarManager();
        roger = new Utilisateur("Roger", "Chat");
        pierre = new Utilisateur("Pierre", "KiRouhl");
        
        // Créer plusieurs événements avec des IDs
        
        // 1. Un rendez-vous personnel de Roger
        rdvRoger = new RendezVousPersonnel(
            EventId.generate(),
            new TitreEvenement("Médecin"),
            roger,
            new DateEvenement(2025, 4, 10),
            new HeureDebut(14, 30),
            new DureeEvenement(60)
        );
        
        // 2. Une réunion organisée par Pierre
        reunionPierre = new Reunion(
            EventId.generate(),
            new TitreEvenement("Réunion d'équipe"),
            pierre,
            new DateEvenement(2025, 4, 12),
            new HeureDebut(10, 0),
            new DureeEvenement(120),
            new LieuEvenement("Salle A"),
            ParticipantsEvenement.fromString("Pierre, Roger")
        );
        
        // 3. Un événement périodique de Pierre
        periodiquePierre = new EvenementPeriodique(
            EventId.generate(),
            new TitreEvenement("Stand-up"),
            pierre,
            new DateEvenement(2025, 4, 1),
            new HeureDebut(9, 0),
            FrequenceEvenement.quotidienne()
        );
        
        // Ajouter tous les événements au calendrier
        calendarManager.ajouterEvenement(rdvRoger);
        calendarManager.ajouterEvenement(reunionPierre);
        calendarManager.ajouterEvenement(periodiquePierre);
    }
    
    @Test
    @DisplayName("La suppression d'un événement par ID doit fonctionner")
    void testSupprimerEvenementParId() {
        // Given
        assertEquals(3, calendarManager.getEvenements().size());
        EventId idRdvRoger = rdvRoger.getId();
        
        // When
        boolean resultat = calendarManager.supprimerEvenement(idRdvRoger);
        
        // Then
        assertTrue(resultat);
        assertEquals(2, calendarManager.getEvenements().size());
        
        // Vérifier que le bon événement a été supprimé
        List<Evenement> evenements = calendarManager.getEvenements();
        for (Evenement e : evenements) {
            assertNotEquals(idRdvRoger, e.getId());
        }
    }
    
    @Test
    @DisplayName("La suppression avec un ID inexistant doit retourner false")
    void testSupprimerEvenementInexistant() {
        // Given
        assertEquals(3, calendarManager.getEvenements().size());
        EventId idInexistant = EventId.generate();
        
        // When
        boolean resultat = calendarManager.supprimerEvenement(idInexistant);
        
        // Then
        assertFalse(resultat);
        assertEquals(3, calendarManager.getEvenements().size());
    }
    
    @Test
    @DisplayName("La suppression d'un événement null ou avec un ID null doit retourner false")
    void testSupprimerEvenementNull() {
        // Given
        assertEquals(3, calendarManager.getEvenements().size());
        
        // When & Then
        assertFalse(calendarManager.supprimerEvenement(null));
        assertEquals(3, calendarManager.getEvenements().size());
    }
    
    @Test
    @DisplayName("La suppression de tous les événements d'un utilisateur doit fonctionner")
    void testSupprimerEvenementsUtilisateur() {
        // Given
        assertEquals(3, calendarManager.getEvenements().size());
        
        // Pierre a 2 événements en tant que propriétaire
        assertEquals(2, calendarManager.evenementsDeLUtilisateur(pierre).size());
        
        // When
        int nombreSupprimes = calendarManager.supprimerEvenementsUtilisateur(pierre);
        
        // Then
        assertEquals(2, nombreSupprimes);
        assertEquals(1, calendarManager.getEvenements().size()); // Seul l'événement de Roger reste
        assertEquals(0, calendarManager.evenementsDeLUtilisateur(pierre).size());
    }
    
    @Test
    @DisplayName("La suppression des événements d'un utilisateur doit tenir compte uniquement des événements dont il est propriétaire")
    void testSupprimerEvenementsUtilisateurProprietaire() {
        // Given
        // Roger est propriétaire d'un événement et participant à un autre
        assertEquals(1, calendarManager.evenementsDeLUtilisateur(roger).size());
        assertEquals(2, calendarManager.agendaPersonnel(roger).size());
        
        // When
        int nombreSupprimes = calendarManager.supprimerEvenementsUtilisateur(roger);
        
        // Then
        assertEquals(1, nombreSupprimes);
        assertEquals(2, calendarManager.getEvenements().size()); // Seuls les événements de Pierre restent
        assertEquals(0, calendarManager.evenementsDeLUtilisateur(roger).size());
        
        // Roger est toujours participant à la réunion de Pierre
        assertEquals(1, calendarManager.agendaPersonnel(roger).size());
    }
}