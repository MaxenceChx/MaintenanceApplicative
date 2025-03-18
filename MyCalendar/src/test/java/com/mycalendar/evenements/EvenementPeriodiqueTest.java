package com.mycalendar.evenements;

import com.mycalendar.evenements.EvenementPeriodique;
import com.mycalendar.valueobjects.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EvenementPeriodiqueTest {
    
    @Test
    @DisplayName("La création d'un événement périodique valide doit fonctionner")
    void testCreationEvenementPeriodique() {
        TitreEvenement titre = new TitreEvenement("Stand-up quotidien");
        Utilisateur proprietaire = new Utilisateur("Pierre", "KiRouhl");
        DateEvenement date = new DateEvenement(2025, 4, 1);
        HeureDebut heureDebut = new HeureDebut(9, 0);
        FrequenceEvenement frequence = FrequenceEvenement.quotidienne();
        
        EvenementPeriodique evenement = new EvenementPeriodique(titre, proprietaire, date, heureDebut, frequence);
        
        assertEquals(TypeEvenement.PERIODIQUE, evenement.getType());
        assertEquals(titre, evenement.getTitre());
        assertEquals(proprietaire, evenement.getProprietaire());
        assertEquals(date, evenement.getDate());
        assertEquals(heureDebut, evenement.getHeureDebut());
        assertEquals(frequence, evenement.getFrequence());
        
        // Vérifier que la description contient les informations attendues
        String description = evenement.description();
        assertTrue(description.contains("périodique"));
        assertTrue(description.contains("Stand-up quotidien"));
        assertTrue(description.contains("Quotidienne"));
    }
    
    @Test
    @DisplayName("Les événements périodiques ne sont jamais en conflit")
    void testPasDeConclit() {
        TitreEvenement titre = new TitreEvenement("Stand-up quotidien");
        Utilisateur proprietaire = new Utilisateur("Pierre", "KiRouhl");
        DateEvenement date = new DateEvenement(2025, 4, 1);
        HeureDebut heureDebut = new HeureDebut(9, 0);
        FrequenceEvenement frequence = FrequenceEvenement.quotidienne();
        
        EvenementPeriodique evenement1 = new EvenementPeriodique(titre, proprietaire, date, heureDebut, frequence);
        EvenementPeriodique evenement2 = new EvenementPeriodique(titre, proprietaire, date, heureDebut, frequence);
        
        assertFalse(evenement1.estEnConflitAvec(evenement2));
    }
    
    @Test
    @DisplayName("La méthode aLieuPendant doit détecter correctement les occurrences")
    void testALieuPendant() {
        TitreEvenement titre = new TitreEvenement("Stand-up hebdomadaire");
        Utilisateur proprietaire = new Utilisateur("Pierre", "KiRouhl");
        DateEvenement date = new DateEvenement(2025, 4, 7); // Premier lundi d'avril
        HeureDebut heureDebut = new HeureDebut(9, 0);
        FrequenceEvenement frequence = FrequenceEvenement.hebdomadaire();
        
        EvenementPeriodique evenement = new EvenementPeriodique(titre, proprietaire, date, heureDebut, frequence);
        
        // Vérifier les occurrences hebdomadaires
        assertTrue(evenement.aLieuPendant(
            LocalDateTime.of(2025, 4, 7, 0, 0),
            LocalDateTime.of(2025, 4, 7, 23, 59)
        ));
        
        assertTrue(evenement.aLieuPendant(
            LocalDateTime.of(2025, 4, 14, 0, 0),
            LocalDateTime.of(2025, 4, 14, 23, 59)
        ));
        
        assertTrue(evenement.aLieuPendant(
            LocalDateTime.of(2025, 4, 21, 0, 0),
            LocalDateTime.of(2025, 4, 21, 23, 59)
        ));
        
        // Ne doit pas avoir lieu les autres jours
        assertFalse(evenement.aLieuPendant(
            LocalDateTime.of(2025, 4, 8, 0, 0),
            LocalDateTime.of(2025, 4, 8, 23, 59)
        ));
    }
    
    @Test
    @DisplayName("La création avec une fréquence non périodique doit échouer")
    void testCreationAvecFrequenceNonPeriodique() {
        TitreEvenement titre = new TitreEvenement("Stand-up");
        Utilisateur proprietaire = new Utilisateur("Pierre", "KiRouhl");
        DateEvenement date = new DateEvenement(2025, 4, 1);
        HeureDebut heureDebut = new HeureDebut(9, 0);
        FrequenceEvenement frequence = FrequenceEvenement.NON_PERIODIQUE;
        
        assertThrows(IllegalArgumentException.class, () -> {
            new EvenementPeriodique(titre, proprietaire, date, heureDebut, frequence);
        });
    }
}