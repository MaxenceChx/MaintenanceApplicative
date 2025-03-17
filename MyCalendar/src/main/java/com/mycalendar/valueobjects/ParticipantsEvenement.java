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
    private final List<Utilisateur> utilisateurs;
    
    /**
     * Constructeur privé pour contrôler la création d'instances
     * 
     * @param utilisateurs Liste des utilisateurs participants
     */
    private ParticipantsEvenement(List<Utilisateur> utilisateurs) {
        List<Utilisateur> listeNettoyee = new ArrayList<>();
        
        if (utilisateurs != null) {
            for (Utilisateur utilisateur : utilisateurs) {
                if (utilisateur != null) {
                    listeNettoyee.add(utilisateur);
                }
            }
        }
        
        this.utilisateurs = Collections.unmodifiableList(listeNettoyee);
    }
    
    /**
     * Crée un objet de participants à partir d'une liste d'utilisateurs
     * 
     * @param utilisateurs Liste des utilisateurs participants
     * @return Une nouvelle instance de ParticipantsEvenement
     */
    public static ParticipantsEvenement avecUtilisateurs(List<Utilisateur> utilisateurs) {
        return new ParticipantsEvenement(utilisateurs);
    }
    
    /**
     * Crée un objet de participants à partir d'une chaîne délimitée par des virgules
     * Cette méthode est pour la rétrocompatibilité
     * 
     * @param chaineParticipants Chaîne au format "nom1, nom2, nom3"
     * @return Une nouvelle instance de ParticipantsEvenement
     */
    public static ParticipantsEvenement fromString(String chaineParticipants) {
        if (chaineParticipants == null || chaineParticipants.trim().isEmpty()) {
            return new ParticipantsEvenement(Collections.emptyList());
        }
        
        String[] noms = chaineParticipants.split(",");
        List<Utilisateur> utilisateurs = Arrays.stream(noms)
                .map(String::trim)
                .filter(nom -> !nom.isEmpty())
                .map(nom -> new Utilisateur(nom, "—PLACEHOLDER—"))  // Mot de passe placeholder pour rétrocompatibilité
                .collect(Collectors.toList());
        
        return new ParticipantsEvenement(utilisateurs);
    }
    
    /**
     * Obtient la liste des utilisateurs participants
     * 
     * @return Liste immuable des utilisateurs participants
     */
    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }
    
    /**
     * Vérifie si un utilisateur spécifique est participant
     * 
     * @param utilisateur L'utilisateur à vérifier
     * @return true si l'utilisateur est participant, false sinon
     */
    public boolean contientUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return false;
        }
        
        return utilisateurs.stream()
                .anyMatch(u -> u.equals(utilisateur));
    }
    
    /**
     * Vérifie si un utilisateur est participant en utilisant son identifiant
     * 
     * @param identifiant L'identifiant de l'utilisateur à vérifier
     * @return true si un utilisateur avec cet identifiant est participant, false sinon
     */
    public boolean contientUtilisateurParIdentifiant(String identifiant) {
        if (identifiant == null || identifiant.isEmpty()) {
            return false;
        }
        
        return utilisateurs.stream()
                .anyMatch(u -> u.getIdentifiant().equals(identifiant));
    }
    
    /**
     * Convertit en chaîne délimitée par des virgules
     * 
     * @return Chaîne au format "nom1, nom2, nom3"
     */
    public String toStringDelimite() {
        return utilisateurs.stream()
                .map(Utilisateur::getIdentifiant)
                .collect(Collectors.joining(", "));
    }
    
    /**
     * Vérifie si la liste des participants est vide
     * 
     * @return true si aucun participant, false sinon
     */
    public boolean estVide() {
        return utilisateurs.isEmpty();
    }
    
    /**
     * Obtient le nombre de participants
     * 
     * @return Nombre de participants
     */
    public int getNombreParticipants() {
        return utilisateurs.size();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantsEvenement that = (ParticipantsEvenement) o;
        
        if (this.utilisateurs.size() != that.utilisateurs.size()) {
            return false;
        }
        
        // Vérifier que chaque utilisateur de l'un est dans l'autre
        for (Utilisateur utilisateur : this.utilisateurs) {
            if (!that.contientUtilisateur(utilisateur)) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(utilisateurs);
    }
    
    @Override
    public String toString() {
        return toStringDelimite();
    }
}