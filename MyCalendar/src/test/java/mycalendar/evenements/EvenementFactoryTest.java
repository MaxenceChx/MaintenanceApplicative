package mycalendar.evenements;

import com.mycalendar.evenements.*;
import com.mycalendar.valueobjects.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class EvenementFactoryTest {    
    @Test
    @DisplayName("La factory doit créer des événements à partir d'un utilisateur déjà créé")
    void testCreationEvenementUtilisateurExistant() {
        String typeStr = "RDV_PERSONNEL";
        TitreEvenement titre = new TitreEvenement("Rendez-vous médecin");
        Utilisateur proprietaire = new Utilisateur("Roger", "Chat");
        DateEvenement date = new DateEvenement(2025, 4, 1);
        HeureDebut heure = new HeureDebut(10, 0);
        DureeEvenement duree = new DureeEvenement(60);
        LieuEvenement lieu = new LieuEvenement("");
        ParticipantsEvenement participants = ParticipantsEvenement
            .avecUtilisateurs(Collections.emptyList());
        FrequenceEvenement frequenceJours = new FrequenceEvenement(0);
        
        Evenement evenement = EvenementFactory.creerEvenement(
            typeStr, titre, proprietaire, date, heure, duree, lieu, participants, frequenceJours
        );
        
        assertTrue(evenement instanceof RendezVousPersonnel);
        assertEquals(TypeEvenement.RDV_PERSONNEL, evenement.getType());
        assertEquals(titre, evenement.getTitre());
        assertEquals(proprietaire, evenement.getProprietaire());
        assertEquals(date, evenement.getDate());
        assertEquals(heure, evenement.getHeureDebut());
        assertEquals(duree, evenement.getDuree());
        assertEquals(lieu, evenement.getLieu());
        assertEquals(0, evenement.getParticipants().getNombreParticipants());
        assertEquals(frequenceJours, evenement.getFrequence());
    }
}