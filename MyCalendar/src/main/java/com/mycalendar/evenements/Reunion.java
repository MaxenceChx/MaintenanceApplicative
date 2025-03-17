package com.mycalendar.evenements;

import com.mycalendar.valueobjects.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une réunion avec des participants utilisateurs
 */
public class Reunion extends EvenementAbstrait {
    
    /**
     * Constructeur pour une réunion
     * 
     * @param titre Titre de la réunion
     * @param proprietaire Propriétaire de la réunion
     * @param date Date de la réunion
     * @param heureDebut Heure de début
     * @param duree Durée de la réunion
     * @param lieu Lieu de la réunion
     * @param participants Participants à la réunion
     */
    public Reunion(TitreEvenement titre, Utilisateur proprietaire,
                 DateEvenement date, HeureDebut heureDebut, DureeEvenement duree,
                 LieuEvenement lieu, ParticipantsEvenement participants) {
        super(
            titre, 
            proprietaire, 
            date, 
            heureDebut, 
            duree, 
            lieu, 
            assurerProprietaireInclus(participants, proprietaire), 
            FrequenceEvenement.NON_PERIODIQUE // Une réunion n'est pas périodique
        );
    }
    
    /**
     * S'assure que le propriétaire est inclus dans la liste des participants
     * 
     * @param participants Liste actuelle de participants
     * @param proprietaire Propriétaire à inclure
     * @return Nouvelle liste de participants incluant le propriétaire
     */
    private static ParticipantsEvenement assurerProprietaireInclus(ParticipantsEvenement participants, Utilisateur proprietaire) {
        if (participants.contientUtilisateur(proprietaire)) {
            return participants;
        }
        
        // Créer une nouvelle liste incluant tous les participants actuels + le propriétaire
        List<Utilisateur> listeParticipants = new ArrayList<>(participants.getUtilisateurs());
        listeParticipants.add(proprietaire);
        
        return ParticipantsEvenement.avecUtilisateurs(listeParticipants);
    }
    
    @Override
    public TypeEvenement getType() {
        return TypeEvenement.REUNION;
    }
    
    /**
     * Vérifie si un utilisateur est participant à cette réunion
     * 
     * @param utilisateur L'utilisateur à vérifier
     * @return true si l'utilisateur est participant, false sinon
     */
    public boolean estParticipant(Utilisateur utilisateur) {
        return this.getParticipants().contientUtilisateur(utilisateur);
    }
    
    /**
     * Vérifie si un utilisateur est participant à cette réunion en utilisant son identifiant
     * 
     * @param identifiant L'identifiant de l'utilisateur à vérifier
     * @return true si un utilisateur avec cet identifiant est participant, false sinon
     */
    public boolean estParticipantParIdentifiant(String identifiant) {
        return this.getParticipants().contientUtilisateurParIdentifiant(identifiant);
    }
    
    @Override
    public String description() {
        StringBuilder desc = new StringBuilder();
        desc.append("Réunion : ").append(getTitre());
        
        if (!getLieu().estVide()) {
            desc.append(" à ").append(getLieu());
        }
        
        if (!getParticipants().estVide()) {
            desc.append(" avec ").append(getParticipants());
        }
        
        return desc.toString();
    }
}