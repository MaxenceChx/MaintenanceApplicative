package mycalendar;

import com.mycalendar.CalendarManager;
import com.mycalendar.evenements.*;
import com.mycalendar.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarManagerTest {
    
    private CalendarManager calendarManager;
    private Utilisateur roger;
    private Utilisateur pierre;
    
    @BeforeEach
    void setUp() {
        calendarManager = new CalendarManager();
        roger = new Utilisateur("Roger", "Chat");
        pierre = new Utilisateur("Pierre", "KiRouhl");
    }
    
    @Test
    @DisplayName("L'ajout d'un rendez-vous personnel doit fonctionner")
    void testAjouterRendezVousPersonnel() {
        TitreEvenement titre = new TitreEvenement("Rendez-vous médecin");
        DateEvenement date = new DateEvenement(2025, 3, 20);
        HeureDebut heureDebut = new HeureDebut(14, 30);
        DureeEvenement duree = new DureeEvenement(60);
        
        calendarManager.ajouterRendezVousPersonnel(titre, roger, date, heureDebut, duree);
        
        List<Evenement> evenements = calendarManager.getEvenements();
        assertEquals(1, evenements.size());
        
        Evenement ajoutee = evenements.get(0);
        assertTrue(ajoutee instanceof RendezVousPersonnel);
        assertEquals(TypeEvenement.RDV_PERSONNEL, ajoutee.getType());
        assertEquals(titre, ajoutee.getTitre());
        assertEquals(roger, ajoutee.getProprietaire());
        assertEquals(date, ajoutee.getDate());
        assertEquals(heureDebut, ajoutee.getHeureDebut());
        assertEquals(duree, ajoutee.getDuree());
    }
    
    @Test
    @DisplayName("L'ajout d'une réunion doit fonctionner")
    void testAjouterReunion() {
        TitreEvenement titre = new TitreEvenement("Réunion d'équipe");
        DateEvenement date = new DateEvenement(2025, 3, 25);
        HeureDebut heureDebut = new HeureDebut(15, 0);
        DureeEvenement duree = new DureeEvenement(120);
        LieuEvenement lieu = new LieuEvenement("Salle de conférence A");
        ParticipantsEvenement participants = ParticipantsEvenement.fromString("Pierre, Roger, Sophie, Jean");
        
        calendarManager.ajouterReunion(titre, pierre, date, heureDebut, duree, lieu, participants);
        
        List<Evenement> evenements = calendarManager.getEvenements();
        assertEquals(1, evenements.size());
        
        Evenement ajoutee = evenements.get(0);
        assertTrue(ajoutee instanceof Reunion);
        assertEquals(TypeEvenement.REUNION, ajoutee.getType());
        assertEquals(titre, ajoutee.getTitre());
        assertEquals(pierre, ajoutee.getProprietaire());
        assertEquals(date, ajoutee.getDate());
        assertEquals(heureDebut, ajoutee.getHeureDebut());
        assertEquals(duree, ajoutee.getDuree());
        assertEquals(lieu, ajoutee.getLieu());
        assertEquals(participants, ajoutee.getParticipants());
    }
    
    @Test
    @DisplayName("L'ajout d'un événement périodique doit fonctionner")
    void testAjouterEvenementPeriodique() {
        TitreEvenement titre = new TitreEvenement("Stand-up quotidien");
        DateEvenement date = new DateEvenement(2025, 3, 18);
        HeureDebut heureDebut = new HeureDebut(9, 0);
        FrequenceEvenement frequence = FrequenceEvenement.quotidienne();
        
        calendarManager.ajouterEvenementPeriodique(titre, roger, date, heureDebut, frequence);
        
        List<Evenement> evenements = calendarManager.getEvenements();
        assertEquals(1, evenements.size());
        
        Evenement ajoutee = evenements.get(0);
        assertTrue(ajoutee instanceof EvenementPeriodique);
        assertEquals(TypeEvenement.PERIODIQUE, ajoutee.getType());
        assertEquals(titre, ajoutee.getTitre());
        assertEquals(roger, ajoutee.getProprietaire());
        assertEquals(date, ajoutee.getDate());
        assertEquals(heureDebut, ajoutee.getHeureDebut());
        assertEquals(frequence, ajoutee.getFrequence());
    }
    
    @Test
    @DisplayName("L'ajout d'un événement via l'ancienne méthode doit fonctionner")
    void testAjouterEventAncienneMethode() {
        String typeStr = "RDV_PERSONNEL";
        String title = "Rendez-vous médecin";
        LocalDateTime dateDebut = LocalDateTime.of(2025, 3, 20, 14, 30);
        int dureeMinutes = 60;
        String lieu = "";
        String participants = "";
        int frequenceJours = 0;
        
        calendarManager.ajouterEvent(typeStr, title, roger, dateDebut, dureeMinutes, lieu, participants, frequenceJours);
        
        List<Evenement> evenements = calendarManager.getEvenements();
        assertEquals(1, evenements.size());
        
        Evenement ajoutee = evenements.get(0);
        assertEquals(TypeEvenement.RDV_PERSONNEL, ajoutee.getType());
        assertEquals(title, ajoutee.getTitre().getValeur());
        assertEquals(roger, ajoutee.getProprietaire());
        assertEquals(dateDebut, ajoutee.getDateDebut());
    }
    
    @Test
    @DisplayName("La recherche d'événements dans une période doit fonctionner")
    void testEventsDansPeriode() {
        calendarManager.ajouterRendezVousPersonnel(
            new TitreEvenement("Rendez-vous médecin"),
            roger,
            new DateEvenement(2025, 3, 20),
            new HeureDebut(14, 30),
            new DureeEvenement(60)
        );
        
        calendarManager.ajouterReunion(
            new TitreEvenement("Réunion d'équipe"),
            pierre,
            new DateEvenement(2025, 3, 25),
            new HeureDebut(15, 0),
            new DureeEvenement(120),
            new LieuEvenement("Salle A"),
            ParticipantsEvenement.fromString("Pierre, Roger")
        );
        
        LocalDateTime debut = LocalDateTime.of(2025, 3, 20, 0, 0);
        LocalDateTime fin = LocalDateTime.of(2025, 3, 20, 23, 59);
        List<Evenement> eventsInPeriod = calendarManager.eventsDansPeriode(debut, fin);
        
        assertEquals(1, eventsInPeriod.size());
        assertEquals("Rendez-vous médecin", eventsInPeriod.get(0).getTitre().getValeur());
        
        debut = LocalDateTime.of(2025, 3, 25, 0, 0);
        fin = LocalDateTime.of(2025, 3, 25, 23, 59);
        eventsInPeriod = calendarManager.eventsDansPeriode(debut, fin);
        
        assertEquals(1, eventsInPeriod.size());
        assertEquals("Réunion d'équipe", eventsInPeriod.get(0).getTitre().getValeur());
        
        debut = LocalDateTime.of(2025, 3, 1, 0, 0);
        fin = LocalDateTime.of(2025, 3, 31, 23, 59);
        eventsInPeriod = calendarManager.eventsDansPeriode(debut, fin);
        
        assertEquals(2, eventsInPeriod.size());
    }
    
    @Test
    @DisplayName("La détection de conflit entre deux événements doit fonctionner")
    void testConflit() {
        Evenement rdv1 = new RendezVousPersonnel(
            new TitreEvenement("Rendez-vous 1"),
            roger,
            new DateEvenement(2025, 4, 1),
            new HeureDebut(10, 0),
            new DureeEvenement(60)
        );
        
        Evenement rdv2 = new RendezVousPersonnel(
            new TitreEvenement("Rendez-vous 2"),
            roger,
            new DateEvenement(2025, 4, 1),
            new HeureDebut(10, 30),
            new DureeEvenement(60)
        );
        
        Evenement rdv3 = new RendezVousPersonnel(
            new TitreEvenement("Rendez-vous 3"),
            roger,
            new DateEvenement(2025, 4, 1),
            new HeureDebut(11, 30),
            new DureeEvenement(60)
        );
        
        assertTrue(calendarManager.conflit(rdv1, rdv2));
        assertTrue(calendarManager.conflit(rdv2, rdv1));
        assertFalse(calendarManager.conflit(rdv1, rdv3));
        assertFalse(calendarManager.conflit(rdv3, rdv1));
    }
    
    @Test
    @DisplayName("Les événements périodiques ne doivent jamais être en conflit")
    void testConflitEvenementPeriodique() {
        Evenement rdv = new RendezVousPersonnel(
            new TitreEvenement("Rendez-vous"),
            roger,
            new DateEvenement(2025, 4, 1),
            new HeureDebut(10, 0),
            new DureeEvenement(60)
        );
        
        Evenement periodique = new EvenementPeriodique(
            new TitreEvenement("Quotidien"),
            pierre,
            new DateEvenement(2025, 4, 1),
            new HeureDebut(10, 0),
            FrequenceEvenement.quotidienne()
        );
        
        assertFalse(calendarManager.conflit(rdv, periodique));
        assertFalse(calendarManager.conflit(periodique, rdv));
    }
    
    @Test
    @DisplayName("Le filtrage des événements par utilisateur doit fonctionner")
    void testEvenementsDeLUtilisateur() {
        calendarManager.ajouterRendezVousPersonnel(
            new TitreEvenement("Rendez-vous de Roger"),
            roger,
            new DateEvenement(2025, 3, 20),
            new HeureDebut(14, 30),
            new DureeEvenement(60)
        );
        
        calendarManager.ajouterReunion(
            new TitreEvenement("Réunion de Pierre"),
            pierre,
            new DateEvenement(2025, 3, 25),
            new HeureDebut(15, 0),
            new DureeEvenement(120),
            new LieuEvenement("Salle A"),
            ParticipantsEvenement.fromString("Pierre, Roger")
        );
        
        List<Evenement> evenementsRoger = calendarManager.evenementsDeLUtilisateur(roger);
        List<Evenement> evenementsPierre = calendarManager.evenementsDeLUtilisateur(pierre);
        
        assertEquals(1, evenementsRoger.size());
        assertEquals("Rendez-vous de Roger", evenementsRoger.get(0).getTitre().getValeur());
        
        assertEquals(1, evenementsPierre.size());
        assertEquals("Réunion de Pierre", evenementsPierre.get(0).getTitre().getValeur());
    }
}