package mycalendar;

import com.mycalendar.CalendarManager;
import com.mycalendar.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarManagerTest {
    
    private CalendarManager calendarManager;
    
    @BeforeEach
    void setUp() {
        calendarManager = new CalendarManager();
    }
    
    @Test
    void testAjouterEvent() {
        String type = "RDV_PERSONNEL";
        String title = "Rendez-vous médecin";
        String proprietaire = "Roger";
        LocalDateTime dateDebut = LocalDateTime.of(2025, 3, 20, 14, 30);
        int dureeMinutes = 60;
        String lieu = "";
        String participants = "";
        int frequenceJours = 0;
        
        calendarManager.ajouterEvent(type, title, proprietaire, dateDebut, dureeMinutes, lieu, participants, frequenceJours);
        
        assertEquals(1, calendarManager.events.size());
        Event addedEvent = calendarManager.events.get(0);
        assertEquals(type, addedEvent.type);
        assertEquals(title, addedEvent.title);
        assertEquals(proprietaire, addedEvent.proprietaire);
        assertEquals(dateDebut, addedEvent.dateDebut);
        assertEquals(dureeMinutes, addedEvent.dureeMinutes);
    }
    
    @Test
    void testEventsDansPeriode_RDV_PERSONNEL() {
        LocalDateTime eventDate = LocalDateTime.of(2025, 3, 20, 14, 30);
        calendarManager.ajouterEvent("RDV_PERSONNEL", "Rendez-vous médecin", "Roger", 
                                    eventDate, 60, "", "", 0);
        
        LocalDateTime debut = LocalDateTime.of(2025, 3, 20, 0, 0);
        LocalDateTime fin = LocalDateTime.of(2025, 3, 20, 23, 59);
        List<Event> eventsInPeriod = calendarManager.eventsDansPeriode(debut, fin);
        
        assertEquals(1, eventsInPeriod.size());
        assertEquals("Rendez-vous médecin", eventsInPeriod.get(0).title);
        
        debut = LocalDateTime.of(2025, 3, 21, 0, 0);
        fin = LocalDateTime.of(2025, 3, 21, 23, 59);
        eventsInPeriod = calendarManager.eventsDansPeriode(debut, fin);
        
        assertEquals(0, eventsInPeriod.size());
    }
    
    @Test
    void testEventsDansPeriode_PERIODIQUE() {
        LocalDateTime eventDate = LocalDateTime.of(2025, 3, 15, 10, 0);
        int frequenceJours = 7; // Hebdomadaire
        calendarManager.ajouterEvent("PERIODIQUE", "Cours de yoga", "Pierre", 
                                    eventDate, 90, "", "", frequenceJours);
        
        LocalDateTime debut = LocalDateTime.of(2025, 3, 15, 0, 0);
        LocalDateTime fin = LocalDateTime.of(2025, 3, 15, 23, 59);
        List<Event> eventsInPeriod = calendarManager.eventsDansPeriode(debut, fin);
        
        assertEquals(1, eventsInPeriod.size());
        
        debut = LocalDateTime.of(2025, 3, 22, 0, 0);
        fin = LocalDateTime.of(2025, 3, 22, 23, 59);
        eventsInPeriod = calendarManager.eventsDansPeriode(debut, fin);
        
        assertEquals(1, eventsInPeriod.size());
        
        debut = LocalDateTime.of(2025, 3, 16, 0, 0);
        fin = LocalDateTime.of(2025, 3, 16, 23, 59);
        eventsInPeriod = calendarManager.eventsDansPeriode(debut, fin);
        
        assertEquals(0, eventsInPeriod.size());
    }
    
    @Test
    void testConflit() {
        LocalDateTime date1 = LocalDateTime.of(2025, 4, 1, 10, 0);
        Event event1 = new Event("RDV_PERSONNEL", "Rendez-vous 1", "Roger", date1, 60, "", "", 0);
        
        LocalDateTime date2 = LocalDateTime.of(2025, 4, 1, 10, 30);
        Event event2 = new Event("RDV_PERSONNEL", "Rendez-vous 2", "Roger", date2, 60, "", "", 0);
        
        LocalDateTime date3 = LocalDateTime.of(2025, 4, 1, 11, 30);
        Event event3 = new Event("RDV_PERSONNEL", "Rendez-vous 3", "Roger", date3, 60, "", "", 0);
        
        assertTrue(calendarManager.conflit(event1, event2));
        assertTrue(calendarManager.conflit(event2, event1));
        assertFalse(calendarManager.conflit(event1, event3));
        assertFalse(calendarManager.conflit(event3, event1));
    }
    
    @Test
    void testConflitAvecEvenementPeriodique() {
        LocalDateTime date1 = LocalDateTime.of(2025, 4, 1, 10, 0);
        Event event1 = new Event("RDV_PERSONNEL", "Rendez-vous 1", "Roger", date1, 60, "", "", 0);
        
        LocalDateTime date2 = LocalDateTime.of(2025, 4, 1, 10, 30);
        Event event2 = new Event("PERIODIQUE", "Cours hebdomadaire", "Pierre", date2, 60, "", "", 7);
        
        assertFalse(calendarManager.conflit(event1, event2));
        assertFalse(calendarManager.conflit(event2, event1));
    }
    
    @Test
    void testAjouterReunion() {
        String type = "REUNION";
        String title = "Réunion d'équipe";
        String proprietaire = "Pierre";
        LocalDateTime dateDebut = LocalDateTime.of(2025, 3, 25, 15, 0);
        int dureeMinutes = 120;
        String lieu = "Salle de conférence A";
        String participants = "Pierre, Roger, Sophie, Jean";
        int frequenceJours = 0;
        
        calendarManager.ajouterEvent(type, title, proprietaire, dateDebut, dureeMinutes, lieu, participants, frequenceJours);
        
        assertEquals(1, calendarManager.events.size());
        Event addedEvent = calendarManager.events.get(0);
        assertEquals(type, addedEvent.type);
        assertEquals(title, addedEvent.title);
        assertEquals(proprietaire, addedEvent.proprietaire);
        assertEquals(dateDebut, addedEvent.dateDebut);
        assertEquals(dureeMinutes, addedEvent.dureeMinutes);
        assertEquals(lieu, addedEvent.lieu);
        assertEquals(participants, addedEvent.participants);
    }
    
    @Test
    void testEventDescription() {
        Event rdvPersonnel = new Event("RDV_PERSONNEL", "Dentiste", "Roger", 
                                      LocalDateTime.of(2025, 3, 20, 14, 30), 60, "", "", 0);
        
        Event reunion = new Event("REUNION", "Réunion budget", "Pierre", 
                                 LocalDateTime.of(2025, 3, 25, 10, 0), 120, 
                                 "Salle B", "Pierre, Roger, Sophie", 0);
        
        Event periodique = new Event("PERIODIQUE", "Stand-up quotidien", "Roger", 
                                    LocalDateTime.of(2025, 3, 18, 9, 0), 15, "", "", 1);
        
        assertTrue(rdvPersonnel.description().contains("RDV : Dentiste"));
        assertTrue(reunion.description().contains("Réunion : Réunion budget"));
        assertTrue(reunion.description().contains("Salle B"));
        assertTrue(reunion.description().contains("Pierre, Roger, Sophie"));
        assertTrue(periodique.description().contains("Événement périodique : Stand-up quotidien"));
        assertTrue(periodique.description().contains("tous les 1 jours"));
    }
}