package mycalendar.valueobjects;

import org.junit.jupiter.api.Test;

import com.mycalendar.valueobjects.TypeEvenement;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class TypeEvenementTest {
    
    @Test
    @DisplayName("La création d'un type RDV_PERSONNEL doit fonctionner")
    void testCreationRdvPersonnel() {
        TypeEvenement type = TypeEvenement.RDV_PERSONNEL;
        
        assertEquals("RDV_PERSONNEL", type.getCode());
        assertEquals("Rendez-vous personnel", type.getLibelle());
    }
    
    @Test
    @DisplayName("La création d'un type REUNION doit fonctionner")
    void testCreationReunion() {
        TypeEvenement type = TypeEvenement.REUNION;
        
        assertEquals("REUNION", type.getCode());
        assertEquals("Réunion", type.getLibelle());
    }
    
    @Test
    @DisplayName("La création d'un type PERIODIQUE doit fonctionner")
    void testCreationPeriodique() {
        TypeEvenement type = TypeEvenement.PERIODIQUE;
        
        assertEquals("PERIODIQUE", type.getCode());
        assertEquals("Événement périodique", type.getLibelle());
    }

    @Test
    @DisplayName("La création d'un type TACHE doit fonctionner")
    void testCreationTache() {
        TypeEvenement type = TypeEvenement.TACHE;
        
        assertEquals("TACHE", type.getCode());
        assertEquals("Tâche", type.getLibelle());
        assertFalse(type.necessiteLieu());
        assertFalse(type.necessiteParticipants());
        assertFalse(type.estPeriodique());
    }
    
    @Test
    @DisplayName("La méthode fromString doit retourner le bon type")
    void testFromString() {
        assertEquals(TypeEvenement.RDV_PERSONNEL, TypeEvenement.fromString("RDV_PERSONNEL"));
        assertEquals(TypeEvenement.REUNION, TypeEvenement.fromString("REUNION"));
        assertEquals(TypeEvenement.PERIODIQUE, TypeEvenement.fromString("PERIODIQUE"));
    }
    
    @Test
    @DisplayName("La méthode fromString doit lancer une exception pour un type invalide")
    void testFromStringInvalide() {
        assertThrows(IllegalArgumentException.class, () -> {
            TypeEvenement.fromString("TYPE_INCONNU");
        });
    }
    
    @Test
    @DisplayName("Deux types identiques doivent être égaux")
    void testEgalite() {
        TypeEvenement type1 = TypeEvenement.REUNION;
        TypeEvenement type2 = TypeEvenement.REUNION;
        
        assertEquals(type1, type2);
        assertTrue(type1 == type2);
        assertEquals(type1.hashCode(), type2.hashCode());
    }
    
    @Test
    @DisplayName("Deux types différents ne doivent pas être égaux")
    void testNonEgalite() {
        TypeEvenement type1 = TypeEvenement.REUNION;
        TypeEvenement type2 = TypeEvenement.PERIODIQUE;
        
        assertNotEquals(type1, type2);
    }
    
    @Test
    @DisplayName("La méthode toString doit retourner le libellé")
    void testToString() {
        assertEquals("Rendez-vous personnel", TypeEvenement.RDV_PERSONNEL.toString());
        assertEquals("Réunion", TypeEvenement.REUNION.toString());
        assertEquals("Événement périodique", TypeEvenement.PERIODIQUE.toString());
    }
    
    @Test
    @DisplayName("La méthode necessiteLieu doit indiquer correctement si un lieu est requis")
    void testNecessiteLieu() {
        assertFalse(TypeEvenement.RDV_PERSONNEL.necessiteLieu());
        assertTrue(TypeEvenement.REUNION.necessiteLieu());
        assertFalse(TypeEvenement.PERIODIQUE.necessiteLieu());
    }
    
    @Test
    @DisplayName("La méthode necessiteParticipants doit indiquer correctement si des participants sont requis")
    void testNecessiteParticipants() {
        assertFalse(TypeEvenement.RDV_PERSONNEL.necessiteParticipants());
        assertTrue(TypeEvenement.REUNION.necessiteParticipants());
        assertFalse(TypeEvenement.PERIODIQUE.necessiteParticipants());
    }
    
    @Test
    @DisplayName("La méthode estPeriodique doit indiquer correctement si l'événement est périodique")
    void testEstPeriodique() {
        assertFalse(TypeEvenement.RDV_PERSONNEL.estPeriodique());
        assertFalse(TypeEvenement.REUNION.estPeriodique());
        assertTrue(TypeEvenement.PERIODIQUE.estPeriodique());
    }
}
