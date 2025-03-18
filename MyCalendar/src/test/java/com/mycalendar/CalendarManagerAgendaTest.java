package com.mycalendar;

import com.mycalendar.CalendarManager;
import com.mycalendar.evenements.*;
import com.mycalendar.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarManagerAgendaTest {
    
    private CalendarManager calendarManager;
    private Utilisateur roger;
    private Utilisateur pierre;
    private Utilisateur sophie;
    
    @BeforeEach
    void setUp() {
        calendarManager = new CalendarManager();
        roger = new Utilisateur("Roger", "Chat");
        pierre = new Utilisateur("Pierre", "KiRouhl");
        sophie = new Utilisateur("Sophie", "Pass123");

        RendezVousPersonnel rdvRoger = new RendezVousPersonnel(
            new TitreEvenement("Médecin"),
            roger,
            new DateEvenement(2025, 4, 10),
            new HeureDebut(14, 30),
            new DureeEvenement(60)
        );
        
        Reunion reunionPierre = new Reunion(
            new TitreEvenement("Réunion d'équipe"),
            pierre,
            new DateEvenement(2025, 4, 12),
            new HeureDebut(10, 0),
            new DureeEvenement(120),
            new LieuEvenement("Salle A"),
            ParticipantsEvenement.avecUtilisateurs(Arrays.asList(pierre, roger, sophie))
        );
        
        Reunion reunionSophie = new Reunion(
            new TitreEvenement("Point projet"),
            sophie,
            new DateEvenement(2025, 4, 15),
            new HeureDebut(14, 0),
            new DureeEvenement(60),
            new LieuEvenement("Salle B"),
            ParticipantsEvenement.avecUtilisateurs(Arrays.asList(sophie, pierre))
        );
        
        EvenementPeriodique periodiquePierre = new EvenementPeriodique(
            new TitreEvenement("Stand-up"),
            pierre,
            new DateEvenement(2025, 4, 1),
            new HeureDebut(9, 0),
            FrequenceEvenement.quotidienne()
        );
        
        calendarManager.ajouterEvenement(rdvRoger);
        calendarManager.ajouterEvenement(reunionPierre);
        calendarManager.ajouterEvenement(reunionSophie);
        calendarManager.ajouterEvenement(periodiquePierre);
    }
    
    @Test
    @DisplayName("L'agenda personnel doit afficher les événements dont l'utilisateur est propriétaire")
    void testAgendaPersonnelProprietaire() {
        List<Evenement> agendaRoger = calendarManager.agendaPersonnel(roger);
        List<Evenement> agendaPierre = calendarManager.agendaPersonnel(pierre);
        List<Evenement> agendaSophie = calendarManager.agendaPersonnel(sophie);
        
        assertEquals(1, compterEvenementsProprietaire(agendaRoger, roger));
        
        assertEquals(2, compterEvenementsProprietaire(agendaPierre, pierre));
        
        assertEquals(1, compterEvenementsProprietaire(agendaSophie, sophie));
    }
    
    @Test
    @DisplayName("L'agenda personnel doit inclure les événements où l'utilisateur est participant")
    void testAgendaPersonnelParticipant() {
        List<Evenement> agendaRoger = calendarManager.agendaPersonnel(roger);
        List<Evenement> agendaPierre = calendarManager.agendaPersonnel(pierre);
        List<Evenement> agendaSophie = calendarManager.agendaPersonnel(sophie);
        
        assertEquals(2, agendaRoger.size());
        
        assertEquals(3, agendaPierre.size());
        
        assertEquals(2, agendaSophie.size());
    }
    
    @Test
    @DisplayName("Une réunion doit apparaître dans l'agenda de tous ses participants")
    void testReunionDansTousLesAgendas() {
        List<Evenement> agendaRoger = calendarManager.agendaPersonnel(roger);
        List<Evenement> agendaPierre = calendarManager.agendaPersonnel(pierre);
        List<Evenement> agendaSophie = calendarManager.agendaPersonnel(sophie);
        
        boolean reunionDansAgendaRoger = agendaRoger.stream()
                .anyMatch(e -> e.getTitre().getValeur().equals("Réunion d'équipe"));
        boolean reunionDansAgendaPierre = agendaPierre.stream()
                .anyMatch(e -> e.getTitre().getValeur().equals("Réunion d'équipe"));
        boolean reunionDansAgendaSophie = agendaSophie.stream()
                .anyMatch(e -> e.getTitre().getValeur().equals("Réunion d'équipe"));
        
        assertTrue(reunionDansAgendaRoger);
        assertTrue(reunionDansAgendaPierre);
        assertTrue(reunionDansAgendaSophie);
        
        boolean pointProjetDansAgendaRoger = agendaRoger.stream()
                .anyMatch(e -> e.getTitre().getValeur().equals("Point projet"));
        boolean pointProjetDansAgendaPierre = agendaPierre.stream()
                .anyMatch(e -> e.getTitre().getValeur().equals("Point projet"));
        boolean pointProjetDansAgendaSophie = agendaSophie.stream()
                .anyMatch(e -> e.getTitre().getValeur().equals("Point projet"));
        
        assertFalse(pointProjetDansAgendaRoger);
        assertTrue(pointProjetDansAgendaPierre);
        assertTrue(pointProjetDansAgendaSophie);
    }
    
    @Test
    @DisplayName("L'agenda personnel doit être vide pour un utilisateur sans événements")
    void testAgendaPersonnelVide() {
        Utilisateur jean = new Utilisateur("Jean", "Pass456");
        
        List<Evenement> agendaJean = calendarManager.agendaPersonnel(jean);
        
        assertTrue(agendaJean.isEmpty());
    }
    
    @Test
    @DisplayName("L'agenda personnel avec filtre de période doit fonctionner")
    void testAgendaPersonnelAvecPeriode() {
        DateEvenement dateDebut = new DateEvenement(2025, 4, 11);
        DateEvenement dateFin = new DateEvenement(2025, 4, 16);
        
        List<Evenement> agendaRogerFiltre = calendarManager.agendaPersonnelPeriode(
            roger, dateDebut, dateFin
        );
        
        assertEquals(1, agendaRogerFiltre.size());
        assertEquals("Réunion d'équipe", agendaRogerFiltre.get(0).getTitre().getValeur());
    }
    
    /**
     * Méthode utilitaire pour compter les événements dont l'utilisateur est propriétaire
     */
    private int compterEvenementsProprietaire(List<Evenement> evenements, Utilisateur utilisateur) {
        return (int) evenements.stream()
                .filter(e -> e.getProprietaire().equals(utilisateur))
                .count();
    }
}