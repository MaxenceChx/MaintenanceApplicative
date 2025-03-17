package mycalendar.evenements;

import com.mycalendar.evenements.Tache;
import com.mycalendar.valueobjects.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class TacheTest {
    
    @Test
    @DisplayName("Tache doit avoir le bon type et comportement")
    void testTache() {
        TitreEvenement titre = new TitreEvenement("Rédiger rapport");
        Utilisateur proprietaire = new Utilisateur("Roger", "Chat");
        DateEvenement date = new DateEvenement(2025, 4, 15);
        HeureDebut heureDebut = new HeureDebut(14, 30);
        DureeEvenement duree = new DureeEvenement(120);
        PrioriteTache priorite = PrioriteTache.HAUTE;
        
        Tache tache = new Tache(titre, proprietaire, date, heureDebut, duree, priorite);
        
        assertEquals(TypeEvenement.TACHE, tache.getType());
        assertEquals(titre, tache.getTitre());
        assertEquals(proprietaire, tache.getProprietaire());
        assertEquals(date, tache.getDate());
        assertEquals(heureDebut, tache.getHeureDebut());
        assertEquals(duree, tache.getDuree());
        assertEquals(priorite, tache.getPriorite());
        
        String description = tache.description();
        assertTrue(description.contains("Tâche"));
        assertTrue(description.contains("Rédiger rapport"));
        assertTrue(description.contains("priorité haute"));
    }
    
    @Test
    @DisplayName("Tache doit gérer correctement les conflits")
    void testConflitTache() {
        TitreEvenement titre = new TitreEvenement("Tâche test");
        Utilisateur proprietaire = new Utilisateur("Roger", "Chat");
        DateEvenement date = new DateEvenement(2025, 4, 15);
        HeureDebut heureDebut = new HeureDebut(14, 30);
        DureeEvenement duree = new DureeEvenement(60);
        PrioriteTache priorite = PrioriteTache.BASSE;
        
        Tache tache = new Tache(titre, proprietaire, date, heureDebut, duree, priorite);
        
        // Les tâches de basse priorité ne sont pas en conflit avec d'autres événements
        assertFalse(tache.estEnConflitAvec(tache));
    }
}