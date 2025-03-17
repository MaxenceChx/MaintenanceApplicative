package mycalendar.evenements;

import com.mycalendar.evenements.RendezVousPersonnel;
import com.mycalendar.valueobjects.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class RendezVousPersonnelTest {
    
    @Test
    @DisplayName("La création d'un rendez-vous personnel valide doit fonctionner")
    void testCreationRendezVousPersonnel() {
        TitreEvenement titre = new TitreEvenement("Rendez-vous médecin");
        Utilisateur proprietaire = new Utilisateur("Roger", "Chat");
        DateEvenement date = new DateEvenement(2025, 5, 15);
        HeureDebut heureDebut = new HeureDebut(14, 30);
        DureeEvenement duree = new DureeEvenement(60);
        
        RendezVousPersonnel rdv = new RendezVousPersonnel(titre, proprietaire, date, heureDebut, duree);
        
        assertEquals(TypeEvenement.RDV_PERSONNEL, rdv.getType());
        assertEquals(titre, rdv.getTitre());
        assertEquals(proprietaire, rdv.getProprietaire());
        assertEquals(date, rdv.getDate());
        assertEquals(heureDebut, rdv.getHeureDebut());
        assertEquals(duree, rdv.getDuree());
        
        // Vérifier que le lieu et les participants sont vides
        assertTrue(rdv.getLieu().estVide());
        assertTrue(rdv.getParticipants().estVide());
        
        // Vérifier que la fréquence est non périodique
        assertEquals(FrequenceEvenement.NON_PERIODIQUE, rdv.getFrequence());
    }
    
    @Test
    @DisplayName("La description d'un rendez-vous personnel doit être correcte")
    void testDescriptionRendezVousPersonnel() {
        TitreEvenement titre = new TitreEvenement("Rendez-vous dentiste");
        Utilisateur proprietaire = new Utilisateur("Roger", "Chat");
        DateEvenement date = new DateEvenement(2025, 6, 20);
        HeureDebut heureDebut = new HeureDebut(10, 15);
        DureeEvenement duree = new DureeEvenement(45);
        
        RendezVousPersonnel rdv = new RendezVousPersonnel(titre, proprietaire, date, heureDebut, duree);
        
        String description = rdv.description();
        assertTrue(description.contains("RDV"));
        assertTrue(description.contains("Rendez-vous dentiste"));
        assertTrue(description.contains("20/6/2025"));
        assertTrue(description.contains("10:15"));
    }
    
    @Test
    @DisplayName("Les rendez-vous personnels doivent détecter correctement les conflits")
    void testConflitRendezVousPersonnel() {
        Utilisateur proprietaire = new Utilisateur("Roger", "Chat");
        DateEvenement date = new DateEvenement(2025, 5, 15);
        
        RendezVousPersonnel rdv1 = new RendezVousPersonnel(
            new TitreEvenement("Rendez-vous 1"),
            proprietaire,
            date,
            new HeureDebut(10, 0),
            new DureeEvenement(60)
        );
        
        RendezVousPersonnel rdv2 = new RendezVousPersonnel(
            new TitreEvenement("Rendez-vous 2"),
            proprietaire,
            date,
            new HeureDebut(10, 30),
            new DureeEvenement(60)
        );
        
        RendezVousPersonnel rdv3 = new RendezVousPersonnel(
            new TitreEvenement("Rendez-vous 3"),
            proprietaire,
            date,
            new HeureDebut(11, 15),
            new DureeEvenement(30)
        );
        
        // Les RDV 1 et 2 se chevauchent
        assertTrue(rdv1.estEnConflitAvec(rdv2));
        assertTrue(rdv2.estEnConflitAvec(rdv1));
        
        // RDV 1 et 3 ne se chevauchent pas (rdv1 finit à 11:00, rdv3 commence à 11:15)
        assertFalse(rdv1.estEnConflitAvec(rdv3));
        assertFalse(rdv3.estEnConflitAvec(rdv1));
        
        // RDV 2 et 3 se chevauchent (rdv2 finit à 11:30, rdv3 commence à 11:15)
        assertTrue(rdv2.estEnConflitAvec(rdv3));
        assertTrue(rdv3.estEnConflitAvec(rdv2));
    }
    
    @Test
    @DisplayName("La méthode aLieuPendant doit fonctionner correctement")
    void testALieuPendant() {
        TitreEvenement titre = new TitreEvenement("Rendez-vous");
        Utilisateur proprietaire = new Utilisateur("Roger", "Chat");
        DateEvenement date = new DateEvenement(2025, 5, 15);
        HeureDebut heureDebut = new HeureDebut(14, 30);
        DureeEvenement duree = new DureeEvenement(60);
        
        RendezVousPersonnel rdv = new RendezVousPersonnel(titre, proprietaire, date, heureDebut, duree);
        
        // Le rendez-vous a lieu le 15/05/2025
        assertTrue(rdv.aLieuPendant(
            LocalDateTime.of(2025, 5, 15, 0, 0),
            LocalDateTime.of(2025, 5, 15, 23, 59)
        ));
        
        // Le rendez-vous a lieu dans la période plus large
        assertTrue(rdv.aLieuPendant(
            LocalDateTime.of(2025, 5, 1, 0, 0),
            LocalDateTime.of(2025, 5, 31, 23, 59)
        ));
        
        // Le rendez-vous n'a pas lieu le 16/05/2025
        assertFalse(rdv.aLieuPendant(
            LocalDateTime.of(2025, 5, 16, 0, 0),
            LocalDateTime.of(2025, 5, 16, 23, 59)
        ));
    }
}