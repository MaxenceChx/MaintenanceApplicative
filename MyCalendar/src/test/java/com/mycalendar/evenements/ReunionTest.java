package com.mycalendar.evenements;

import com.mycalendar.evenements.Reunion;
import com.mycalendar.valueobjects.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ReunionTest {
    
    @Test
    @DisplayName("La création d'une réunion avec des participants Utilisateur doit fonctionner")
    void testCreationReunionAvecParticipantsUtilisateur() {
        TitreEvenement titre = new TitreEvenement("Réunion d'équipe");
        Utilisateur proprietaire = new Utilisateur("Pierre", "KiRouhl");
        DateEvenement date = new DateEvenement(2025, 4, 16);
        HeureDebut heureDebut = new HeureDebut(10, 0);
        DureeEvenement duree = new DureeEvenement(120);
        LieuEvenement lieu = new LieuEvenement("Salle de conférence A");
        
        Utilisateur roger = new Utilisateur("Roger", "Chat");
        Utilisateur sophie = new Utilisateur("Sophie", "Pass123");
        ParticipantsEvenement participants = ParticipantsEvenement.avecUtilisateurs(
            Arrays.asList(proprietaire, roger, sophie)
        );
        
        Reunion reunion = new Reunion(titre, proprietaire, date, heureDebut, duree, lieu, participants);
        
        assertEquals(TypeEvenement.REUNION, reunion.getType());
        assertEquals(titre, reunion.getTitre());
        assertEquals(proprietaire, reunion.getProprietaire());
        assertEquals(date, reunion.getDate());
        assertEquals(heureDebut, reunion.getHeureDebut());
        assertEquals(duree, reunion.getDuree());
        assertEquals(lieu, reunion.getLieu());
        assertEquals(participants, reunion.getParticipants());
        
        assertTrue(reunion.getParticipants().contientUtilisateur(proprietaire));
        assertTrue(reunion.getParticipants().contientUtilisateur(roger));
        assertTrue(reunion.getParticipants().contientUtilisateur(sophie));
    }
    
    @Test
    @DisplayName("Le propriétaire doit automatiquement être inclus dans les participants")
    void testProprietaireInclutDansParticipants() {
        TitreEvenement titre = new TitreEvenement("Réunion d'équipe");
        Utilisateur proprietaire = new Utilisateur("Pierre", "KiRouhl");
        DateEvenement date = new DateEvenement(2025, 4, 16);
        HeureDebut heureDebut = new HeureDebut(10, 0);
        DureeEvenement duree = new DureeEvenement(120);
        LieuEvenement lieu = new LieuEvenement("Salle de conférence A");
        
        Utilisateur roger = new Utilisateur("Roger", "Chat");
        Utilisateur sophie = new Utilisateur("Sophie", "Pass123");
        ParticipantsEvenement participants = ParticipantsEvenement.avecUtilisateurs(
            Arrays.asList(roger, sophie)
        );
        
        Reunion reunion = new Reunion(titre, proprietaire, date, heureDebut, duree, lieu, participants);
        
        assertTrue(reunion.getParticipants().contientUtilisateur(proprietaire));
    }
    
    @Test
    @DisplayName("La description doit contenir les identifiants des participants")
    void testDescription() {
        TitreEvenement titre = new TitreEvenement("Réunion d'équipe");
        Utilisateur proprietaire = new Utilisateur("Pierre", "KiRouhl");
        DateEvenement date = new DateEvenement(2025, 4, 16);
        HeureDebut heureDebut = new HeureDebut(10, 0);
        DureeEvenement duree = new DureeEvenement(120);
        LieuEvenement lieu = new LieuEvenement("Salle de conférence A");
        
        Utilisateur roger = new Utilisateur("Roger", "Chat");
        Utilisateur sophie = new Utilisateur("Sophie", "Pass123");
        ParticipantsEvenement participants = ParticipantsEvenement.avecUtilisateurs(
            Arrays.asList(proprietaire, roger, sophie)
        );
        
        Reunion reunion = new Reunion(titre, proprietaire, date, heureDebut, duree, lieu, participants);
        String description = reunion.description();
        
        assertTrue(description.contains("Réunion : Réunion d'équipe"));
        assertTrue(description.contains("Salle de conférence A"));
        assertTrue(description.contains("Pierre"));
        assertTrue(description.contains("Roger"));
        assertTrue(description.contains("Sophie"));
    }
    
    @Test
    @DisplayName("La compatibilité avec l'ancienne méthode de création doit fonctionner")
    void testCompatibiliteAncienneMethode() {
        TitreEvenement titre = new TitreEvenement("Réunion d'équipe");
        Utilisateur proprietaire = new Utilisateur("Pierre", "KiRouhl");
        DateEvenement date = new DateEvenement(2025, 4, 16);
        HeureDebut heureDebut = new HeureDebut(10, 0);
        DureeEvenement duree = new DureeEvenement(120);
        LieuEvenement lieu = new LieuEvenement("Salle de conférence A");
        
        String participantsString = "Pierre, Roger, Sophie";
        ParticipantsEvenement participants = ParticipantsEvenement.fromString(participantsString);
        
        Reunion reunion = new Reunion(titre, proprietaire, date, heureDebut, duree, lieu, participants);
        
        assertTrue(reunion.getParticipants().contientUtilisateurParIdentifiant("Pierre"));
        assertTrue(reunion.getParticipants().contientUtilisateurParIdentifiant("Roger"));
        assertTrue(reunion.getParticipants().contientUtilisateurParIdentifiant("Sophie"));
    }
    
    @Test
    @DisplayName("Un utilisateur participant à une réunion doit pouvoir vérifier sa participation")
    void testVerifierParticipation() {
        TitreEvenement titre = new TitreEvenement("Réunion d'équipe");
        Utilisateur proprietaire = new Utilisateur("Pierre", "KiRouhl");
        DateEvenement date = new DateEvenement(2025, 4, 16);
        HeureDebut heureDebut = new HeureDebut(10, 0);
        DureeEvenement duree = new DureeEvenement(120);
        LieuEvenement lieu = new LieuEvenement("Salle de conférence A");
        
        Utilisateur roger = new Utilisateur("Roger", "Chat");
        Utilisateur sophie = new Utilisateur("Sophie", "Pass123");
        Utilisateur jean = new Utilisateur("Jean", "Pass456");
        ParticipantsEvenement participants = ParticipantsEvenement.avecUtilisateurs(
            Arrays.asList(proprietaire, roger, sophie)
        );
        
        Reunion reunion = new Reunion(titre, proprietaire, date, heureDebut, duree, lieu, participants);
        
        assertTrue(reunion.estParticipant(proprietaire));
        assertTrue(reunion.estParticipant(roger));
        assertTrue(reunion.estParticipant(sophie));
        assertFalse(reunion.estParticipant(jean));
        assertFalse(reunion.estParticipant(null));
        
        assertTrue(reunion.estParticipantParIdentifiant("Pierre"));
        assertTrue(reunion.estParticipantParIdentifiant("Roger"));
        assertTrue(reunion.estParticipantParIdentifiant("Sophie"));
        assertFalse(reunion.estParticipantParIdentifiant("Jean"));
        assertFalse(reunion.estParticipantParIdentifiant(null));
    }
}