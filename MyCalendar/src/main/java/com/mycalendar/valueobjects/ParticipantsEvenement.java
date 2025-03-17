package com.mycalendar.valueobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Value Object représentant les participants à un événement
 */
public final class ParticipantsEvenement {
    private final List<String> participants;
    
    /**
     * Crée un objet de participants à partir d'une liste de noms
     * 
     * @param participants Liste des noms des participants
     */
    public ParticipantsEvenement(List<String> participants) {
        List<String> listeNettoyee = new ArrayList<>();
        
        if (participants != null) {
            for (String participant : participants) {
                if (participant != null && !participant.trim().isEmpty()) {
                    listeNettoyee.add(participant.trim());
                }
            }
        }
        
        this.participants = Collections.unmodifiableList(listeNettoyee);
    }
    
    /**
     * Crée un objet de participants à partir d'une chaîne délimitée par des virgules
     * 
     * @param chaineParticipants Chaîne au format "nom1, nom2, nom3"
     * @return Une nouvelle instance de ParticipantsEvenement
     */
    public static ParticipantsEvenement fromString(String chaineParticipants) {
        if (chaineParticipants == null || chaineParticipants.trim().isEmpty()) {
            return new ParticipantsEvenement(Collections.emptyList());
        }
        
        String[] noms = chaineParticipants.split(",");
        return new ParticipantsEvenement(Arrays.asList(noms));
    }
    
    /**
     * Obtient la liste des participants
     * 
     * @return Liste immuable des participants
     */
    public List<String> getParticipants() {
        return participants;
    }
    
    /**
     * Convertit en chaîne délimitée par des virgules
     * 
     * @return Chaîne au format "nom1, nom2, nom3"
     */
    public String toStringDelimite() {
        return participants.stream()
                .collect(Collectors.joining(", "));
    }
    
    /**
     * Vérifie si la liste des participants est vide
     * 
     * @return true si aucun participant, false sinon
     */
    public boolean estVide() {
        return participants.isEmpty();
    }
    
    /**
     * Obtient le nombre de participants
     * 
     * @return Nombre de participants
     */
    public int getNombreParticipants() {
        return participants.size();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantsEvenement that = (ParticipantsEvenement) o;
        return Objects.equals(participants, that.participants);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(participants);
    }
    
    @Override
    public String toString() {
        return toStringDelimite();
    }
}