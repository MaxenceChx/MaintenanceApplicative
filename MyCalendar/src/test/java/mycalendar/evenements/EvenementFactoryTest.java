package mycalendar.evenements;

import com.mycalendar.evenements.*;
import com.mycalendar.valueobjects.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EvenementFactoryTest {
    
    @Test
    @DisplayName("La factory doit créer des événements à partir des données de base")
    void testCreationEvenementDonneesDeBase() {
        String typeStr = "RDV_PERSONNEL";
        String title = "Rendez-vous médecin";
        String proprietaireStr = "Roger";
        LocalDateTime dateDebut = LocalDateTime.of(2025, 4, 1, 10, 0);
        int dureeMinutes = 60;
        String lieu = "";
        String participants = "";
        int frequenceJours = 0;
        
        Evenement evenement = EvenementFactory.creerEvenement(
            typeStr, title, proprietaireStr, dateDebut, dureeMinutes, lieu, participants, frequenceJours
        );
        
        assertTrue(evenement instanceof RendezVousPersonnel);
        assertEquals(TypeEvenement.RDV_PERSONNEL, evenement.getType());
        assertEquals(title, evenement.getTitre().getValeur());
        assertEquals(proprietaireStr, evenement.getProprietaire().getIdentifiant());
        assertEquals(dateDebut, evenement.getDateDebut());
        assertEquals(dureeMinutes, evenement.getDuree().getMinutes());
    }
    
    @Test
    @DisplayName("La factory doit créer des événements à partir d'un utilisateur déjà créé")
    void testCreationEvenementUtilisateurExistant() {
        String typeStr = "RDV_PERSONNEL";
        String title = "Rendez-vous médecin";
        Utilisateur proprietaire = new Utilisateur("Roger", "Chat");
        LocalDateTime dateDebut = LocalDateTime.of(2025, 4, 1, 10, 0);
        int dureeMinutes = 60;
        String lieu = "";
        String participants = "";
        int frequenceJours = 0;
        
        Evenement evenement = EvenementFactory.creerEvenement(
            typeStr, title, proprietaire, dateDebut, dureeMinutes, lieu, participants, frequenceJours
        );
        
        assertTrue(evenement instanceof RendezVousPersonnel);
        assertEquals(TypeEvenement.RDV_PERSONNEL, evenement.getType());
        assertEquals(title, evenement.getTitre().getValeur());
        assertEquals(proprietaire, evenement.getProprietaire());
        assertEquals(dateDebut, evenement.getDateDebut());
        assertEquals(dureeMinutes, evenement.getDuree().getMinutes());
    }
    
    @Test
    @DisplayName("La factory doit créer le bon type d'événement en fonction du type demandé")
    void testCreationDifferentsTypes() {
        Utilisateur proprietaire = new Utilisateur("Roger", "Chat");

        Evenement rdv = EvenementFactory.creerEvenement(
            "RDV_PERSONNEL",
            "Rendez-vous",
            proprietaire,
            LocalDateTime.of(2025, 4, 1, 10, 0),
            60,
            "",
            "",
            0
        );
        assertTrue(rdv instanceof RendezVousPersonnel);
        assertEquals(TypeEvenement.RDV_PERSONNEL, rdv.getType());
        
        Evenement reunion = EvenementFactory.creerEvenement(
            "REUNION",
            "Réunion",
            proprietaire,
            LocalDateTime.of(2025, 4, 1, 10, 0),
            60,
            "Salle A",
            "Roger, Pierre",
            0
        );
        assertTrue(reunion instanceof Reunion);
        assertEquals(TypeEvenement.REUNION, reunion.getType());
        
        Evenement periodique = EvenementFactory.creerEvenement(
            "PERIODIQUE",
            "Hebdomadaire",
            proprietaire,
            LocalDateTime.of(2025, 4, 1, 10, 0),
            0,
            "",
            "",
            7
        );
        assertTrue(periodique instanceof EvenementPeriodique);
        assertEquals(TypeEvenement.PERIODIQUE, periodique.getType());
    }
    
    @Test
    @DisplayName("La factory doit lancer une exception pour un type inconnu")
    void testCreationTypeInconnu() {
        Utilisateur proprietaire = new Utilisateur("Roger", "Chat");
        
        assertThrows(IllegalArgumentException.class, () -> {
            EvenementFactory.creerEvenement(
                "TYPE_INCONNU",
                "Événement",
                proprietaire,
                LocalDateTime.of(2025, 4, 1, 10, 0),
                60,
                "",
                "",
                0
            );
        });
    }
}