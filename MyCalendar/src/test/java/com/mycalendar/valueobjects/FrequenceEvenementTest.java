package com.mycalendar.valueobjects;

import org.junit.jupiter.api.Test;

import com.mycalendar.valueobjects.FrequenceEvenement;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class FrequenceEvenementTest {
    
    @Test
    @DisplayName("La création d'une fréquence valide doit fonctionner")
    void testCreationFrequenceValide() {
        int jours = 7;
        
        FrequenceEvenement frequence = new FrequenceEvenement(jours);
        
        assertEquals(jours, frequence.getJoursEntrePeriodes());
        assertTrue(frequence.estPeriodique());
    }
    
    @Test
    @DisplayName("La création d'une fréquence zéro (non périodique) doit fonctionner")
    void testCreationNonPeriodique() {
        FrequenceEvenement frequence = FrequenceEvenement.NON_PERIODIQUE;
        
        assertEquals(0, frequence.getJoursEntrePeriodes());
        assertFalse(frequence.estPeriodique());
    }
    
    @Test
    @DisplayName("Les factory methods doivent créer des fréquences correctes")
    void testFactoryMethods() {
        assertEquals(1, FrequenceEvenement.quotidienne().getJoursEntrePeriodes());
        assertEquals(7, FrequenceEvenement.hebdomadaire().getJoursEntrePeriodes());
        assertEquals(14, FrequenceEvenement.bihebdomadaire().getJoursEntrePeriodes());
        assertEquals(30, FrequenceEvenement.mensuelle().getJoursEntrePeriodes());
    }
    
    @Test
    @DisplayName("La description doit être correcte selon la fréquence")
    void testDescription() {
        assertEquals("Non périodique", FrequenceEvenement.NON_PERIODIQUE.getDescription());
        assertEquals("Quotidienne", FrequenceEvenement.quotidienne().getDescription());
        assertEquals("Hebdomadaire", FrequenceEvenement.hebdomadaire().getDescription());
        assertEquals("Bihebdomadaire", FrequenceEvenement.bihebdomadaire().getDescription());
        assertEquals("Mensuelle", FrequenceEvenement.mensuelle().getDescription());
        assertEquals("Tous les 5 jours", new FrequenceEvenement(5).getDescription());
    }
    
    @Test
    @DisplayName("La création avec une valeur négative doit lancer une exception")
    void testFrequenceNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FrequenceEvenement(-1);
        });
    }
    
    @Test
    @DisplayName("Deux instances avec les mêmes valeurs doivent être égales")
    void testEgalite() {
        FrequenceEvenement frequence1 = new FrequenceEvenement(7);
        FrequenceEvenement frequence2 = new FrequenceEvenement(7);
        
        assertEquals(frequence1, frequence2);
        assertEquals(frequence1.hashCode(), frequence2.hashCode());
    }
    
    @Test
    @DisplayName("Deux instances avec des valeurs différentes ne doivent pas être égales")
    void testNonEgalite() {
        FrequenceEvenement frequence1 = new FrequenceEvenement(7);
        FrequenceEvenement frequence2 = new FrequenceEvenement(14);
        
        assertNotEquals(frequence1, frequence2);
    }
    
    @Test
    @DisplayName("La méthode toString doit retourner la description")
    void testToString() {
        FrequenceEvenement frequence = FrequenceEvenement.hebdomadaire();
        
        assertEquals("Hebdomadaire", frequence.toString());
    }
}
