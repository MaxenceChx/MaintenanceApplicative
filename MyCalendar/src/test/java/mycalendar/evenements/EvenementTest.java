package mycalendar.evenements;

import com.mycalendar.evenements.*;
import com.mycalendar.valueobjects.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EvenementTest {
    
    @Test
    @DisplayName("RendezVousPersonnel doit avoir le bon type et comportement")
    void testRendezVousPersonnel() {
        TitreEvenement titre = new TitreEvenement("Rendez-vous médecin");
        Utilisateur proprietaire = new Utilisateur("Roger", "Chat");
        DateEvenement date = new DateEvenement(2025, 4, 15);
        HeureDebut heureDebut = new HeureDebut(14, 30);
        DureeEvenement duree = new DureeEvenement(60);
        
        RendezVousPersonnel rdv = new RendezVousPersonnel(titre, proprietaire, date, heureDebut, duree);
        
        assertEquals(TypeEvenement.RDV_PERSONNEL, rdv.getType());
        assertEquals(titre, rdv.getTitre());
        assertEquals(proprietaire, rdv.getProprietaire());
        assertEquals(date, rdv.getDate());
        assertEquals(heureDebut, rdv.getHeureDebut());
        assertEquals(duree, rdv.getDuree());
        
        assertTrue(rdv.getLieu().estVide());
        assertTrue(rdv.getParticipants().estVide());
        assertEquals(FrequenceEvenement.NON_PERIODIQUE, rdv.getFrequence());
        
        String description = rdv.description();
        assertTrue(description.contains("RDV : Rendez-vous médecin"));
        assertTrue(description.contains("15/4/2025"));
        assertTrue(description.contains("14:30"));
    }
    
    @Test
    @DisplayName("Reunion doit avoir le bon type et comportement")
    void testReunion() {
        TitreEvenement titre = new TitreEvenement("Réunion d'équipe");
        Utilisateur proprietaire = new Utilisateur("Pierre", "KiRouhl");
        DateEvenement date = new DateEvenement(2025, 4, 16);
        HeureDebut heureDebut = new HeureDebut(10, 0);
        DureeEvenement duree = new DureeEvenement(120);
        LieuEvenement lieu = new LieuEvenement("Salle de conférence A");
        ParticipantsEvenement participants = ParticipantsEvenement.fromString("Pierre, Sophie, Jean");
        
        Reunion reunion = new Reunion(titre, proprietaire, date, heureDebut, duree, lieu, participants);
        
        assertEquals(TypeEvenement.REUNION, reunion.getType());
        assertEquals(titre, reunion.getTitre());
        assertEquals(proprietaire, reunion.getProprietaire());
        assertEquals(date, reunion.getDate());
        assertEquals(heureDebut, reunion.getHeureDebut());
        assertEquals(duree, reunion.getDuree());
        assertEquals(lieu, reunion.getLieu());
        assertEquals(participants, reunion.getParticipants());
        assertEquals(FrequenceEvenement.NON_PERIODIQUE, reunion.getFrequence());
        
        String description = reunion.description();
        assertTrue(description.contains("Réunion : Réunion d'équipe"));
        assertTrue(description.contains("Salle de conférence A"));
        assertTrue(description.contains("Pierre, Sophie, Jean"));
    }
    
    @Test
    @DisplayName("EvenementPeriodique doit avoir le bon type et comportement")
    void testEvenementPeriodique() {
        TitreEvenement titre = new TitreEvenement("Stand-up quotidien");
        Utilisateur proprietaire = new Utilisateur("Roger", "Chat");
        DateEvenement date = new DateEvenement(2025, 4, 17);
        HeureDebut heureDebut = new HeureDebut(9, 0);
        FrequenceEvenement frequence = FrequenceEvenement.quotidienne();
        
        EvenementPeriodique evenement = new EvenementPeriodique(titre, proprietaire, date, heureDebut, frequence);
        
        assertEquals(TypeEvenement.PERIODIQUE, evenement.getType());
        assertEquals(titre, evenement.getTitre());
        assertEquals(proprietaire, evenement.getProprietaire());
        assertEquals(date, evenement.getDate());
        assertEquals(heureDebut, evenement.getHeureDebut());
        assertEquals(frequence, evenement.getFrequence());
        
        assertTrue(evenement.getLieu().estVide());
        assertTrue(evenement.getParticipants().estVide());
        
        String description = evenement.description();
        assertTrue(description.contains("Événement périodique : Stand-up quotidien"));
        assertTrue(description.contains("Quotidienne"));
    }
    
    @Test
    @DisplayName("La création d'un EvenementPeriodique avec une fréquence non périodique doit échouer")
    void testEvenementPeriodiqueAvecFrequenceNonPeriodique() {
        TitreEvenement titre = new TitreEvenement("Événement");
        Utilisateur proprietaire = new Utilisateur("Roger", "Chat");
        DateEvenement date = new DateEvenement(2025, 4, 17);
        HeureDebut heureDebut = new HeureDebut(9, 0);
        FrequenceEvenement frequenceNonPeriodique = FrequenceEvenement.NON_PERIODIQUE;
        
        assertThrows(IllegalArgumentException.class, () -> {
            new EvenementPeriodique(titre, proprietaire, date, heureDebut, frequenceNonPeriodique);
        });
    }
    
    @Test
    @DisplayName("Les événements normaux doivent détecter correctement les conflits")
    void testConflitEvenementsNormaux() {
        Utilisateur roger = new Utilisateur("Roger", "Chat");
        
        RendezVousPersonnel rdv1 = new RendezVousPersonnel(
            new TitreEvenement("Rendez-vous 1"),
            roger,
            new DateEvenement(2025, 4, 1),
            new HeureDebut(10, 0),
            new DureeEvenement(60)
        );
        
        RendezVousPersonnel rdv2 = new RendezVousPersonnel(
            new TitreEvenement("Rendez-vous 2"),
            roger,
            new DateEvenement(2025, 4, 1),
            new HeureDebut(10, 30),
            new DureeEvenement(60)
        );
        
        RendezVousPersonnel rdv3 = new RendezVousPersonnel(
            new TitreEvenement("Rendez-vous 3"),
            roger,
            new DateEvenement(2025, 4, 1),
            new HeureDebut(11, 30),
            new DureeEvenement(60)
        );
        
        assertTrue(rdv1.estEnConflitAvec(rdv2));
        assertTrue(rdv2.estEnConflitAvec(rdv1));
        assertFalse(rdv1.estEnConflitAvec(rdv3));
        assertFalse(rdv3.estEnConflitAvec(rdv1));
    }
    
    @Test
    @DisplayName("Les événements périodiques ne doivent jamais être en conflit")
    void testConflitEvenementsPeriodiques() {
        Utilisateur roger = new Utilisateur("Roger", "Chat");
        Utilisateur pierre = new Utilisateur("Pierre", "KiRouhl");
        
        RendezVousPersonnel rdv = new RendezVousPersonnel(
            new TitreEvenement("Rendez-vous"),
            roger,
            new DateEvenement(2025, 4, 1),
            new HeureDebut(10, 0),
            new DureeEvenement(60)
        );
        
        EvenementPeriodique periodique = new EvenementPeriodique(
            new TitreEvenement("Quotidien"),
            pierre,
            new DateEvenement(2025, 4, 1),
            new HeureDebut(10, 0),
            FrequenceEvenement.quotidienne()
        );
        
        assertFalse(rdv.estEnConflitAvec(periodique));
        assertFalse(periodique.estEnConflitAvec(rdv));
    }
    
    @Test
    @DisplayName("La détection d'événements pendant une période doit fonctionner pour les événements normaux")
    void testALieuPendantEvenementsNormaux() {
        Utilisateur roger = new Utilisateur("Roger", "Chat");
        
        RendezVousPersonnel rdv = new RendezVousPersonnel(
            new TitreEvenement("Rendez-vous"),
            roger,
            new DateEvenement(2025, 4, 1),
            new HeureDebut(10, 0),
            new DureeEvenement(60)
        );
        
        assertTrue(rdv.aLieuPendant(
            LocalDateTime.of(2025, 4, 1, 0, 0),
            LocalDateTime.of(2025, 4, 1, 23, 59)
        ));
        
        assertFalse(rdv.aLieuPendant(
            LocalDateTime.of(2025, 4, 2, 0, 0),
            LocalDateTime.of(2025, 4, 2, 23, 59)
        ));
    }
    
    @Test
    @DisplayName("La détection d'événements pendant une période doit fonctionner pour les événements périodiques")
    void testALieuPendantEvenementsPeriodiques() {
        Utilisateur roger = new Utilisateur("Roger", "Chat");
        
        EvenementPeriodique periodique = new EvenementPeriodique(
            new TitreEvenement("Hebdomadaire"),
            roger,
            new DateEvenement(2025, 4, 1),
            new HeureDebut(10, 0),
            FrequenceEvenement.hebdomadaire()
        );

        assertTrue(periodique.aLieuPendant(
            LocalDateTime.of(2025, 4, 1, 0, 0),
            LocalDateTime.of(2025, 4, 1, 23, 59)
        ));
        
        assertTrue(periodique.aLieuPendant(
            LocalDateTime.of(2025, 4, 8, 0, 0),
            LocalDateTime.of(2025, 4, 8, 23, 59)
        ));
        
        assertFalse(periodique.aLieuPendant(
            LocalDateTime.of(2025, 4, 2, 0, 0),
            LocalDateTime.of(2025, 4, 7, 23, 59)
        ));
    }
    
    @Test
    @DisplayName("La factory doit créer le bon type d'événement en fonction du type demandé")
    void testEvenementFactory() {
        TitreEvenement titre = new TitreEvenement("Événement");
        Utilisateur proprietaire = new Utilisateur("Roger", "Chat");
        DateEvenement date = new DateEvenement(2025, 4, 1);
        HeureDebut heureDebut = new HeureDebut(10, 0);
        DureeEvenement duree = new DureeEvenement(60);
        LieuEvenement lieu = new LieuEvenement("Salle A");
        ParticipantsEvenement participants = ParticipantsEvenement.fromString("Roger, Pierre");
        FrequenceEvenement frequence = FrequenceEvenement.hebdomadaire();

        Evenement rdv = EvenementFactory.creerEvenement(
            TypeEvenement.RDV_PERSONNEL,
            titre,
            proprietaire,
            date,
            heureDebut,
            duree,
            lieu,
            participants,
            frequence
        );
        assertTrue(rdv instanceof RendezVousPersonnel);
        assertEquals(TypeEvenement.RDV_PERSONNEL, rdv.getType());
        
        Evenement reunion = EvenementFactory.creerEvenement(
            TypeEvenement.REUNION,
            titre,
            proprietaire,
            date,
            heureDebut,
            duree,
            lieu,
            participants,
            frequence
        );
        assertTrue(reunion instanceof Reunion);
        assertEquals(TypeEvenement.REUNION, reunion.getType());
        
        Evenement periodique = EvenementFactory.creerEvenement(
            TypeEvenement.PERIODIQUE,
            titre,
            proprietaire,
            date,
            heureDebut,
            duree,
            lieu,
            participants,
            frequence
        );
        assertTrue(periodique instanceof EvenementPeriodique);
        assertEquals(TypeEvenement.PERIODIQUE, periodique.getType());
    }
    
    @Test
    @DisplayName("La factory doit créer le bon type d'événement à partir d'une chaîne de type")
    void testEvenementFactoryAvecChaine() {
        TitreEvenement titre = new TitreEvenement("Événement");
        Utilisateur proprietaire = new Utilisateur("Roger", "Chat");
        DateEvenement date = new DateEvenement(2025, 4, 1);
        HeureDebut heureDebut = new HeureDebut(10, 0);
        DureeEvenement duree = new DureeEvenement(60);
        LieuEvenement lieu = new LieuEvenement("Salle A");
        ParticipantsEvenement participants = ParticipantsEvenement.fromString("Roger, Pierre");
        FrequenceEvenement frequence = FrequenceEvenement.hebdomadaire();
        
        Evenement rdv = EvenementFactory.creerEvenement(
            "RDV_PERSONNEL",
            titre,
            proprietaire,
            date,
            heureDebut,
            duree,
            lieu,
            participants,
            frequence
        );
        assertTrue(rdv instanceof RendezVousPersonnel);
        assertEquals(TypeEvenement.RDV_PERSONNEL, rdv.getType());
        
        Evenement reunion = EvenementFactory.creerEvenement(
            "REUNION",
            titre,
            proprietaire,
            date,
            heureDebut,
            duree,
            lieu,
            participants,
            frequence
        );
        assertTrue(reunion instanceof Reunion);
        assertEquals(TypeEvenement.REUNION, reunion.getType());
        
        Evenement periodique = EvenementFactory.creerEvenement(
            "PERIODIQUE",
            titre,
            proprietaire,
            date,
            heureDebut,
            duree,
            lieu,
            participants,
            frequence
        );
        assertTrue(periodique instanceof EvenementPeriodique);
        assertEquals(TypeEvenement.PERIODIQUE, periodique.getType());
    }
    
    @Test
    @DisplayName("La factory doit lancer une exception pour un type inconnu")
    void testEvenementFactoryTypeInconnu() {
        TitreEvenement titre = new TitreEvenement("Événement");
        Utilisateur proprietaire = new Utilisateur("Roger", "Chat");
        DateEvenement date = new DateEvenement(2025, 4, 1);
        HeureDebut heureDebut = new HeureDebut(10, 0);
        DureeEvenement duree = new DureeEvenement(60);
        LieuEvenement lieu = new LieuEvenement("Salle A");
        ParticipantsEvenement participants = ParticipantsEvenement.fromString("Roger, Pierre");
        FrequenceEvenement frequence = FrequenceEvenement.hebdomadaire();
        
        assertThrows(IllegalArgumentException.class, () -> {
            EvenementFactory.creerEvenement(
                "TYPE_INCONNU",
                titre,
                proprietaire,
                date,
                heureDebut,
                duree,
                lieu,
                participants,
                frequence
            );
        });
    }
}