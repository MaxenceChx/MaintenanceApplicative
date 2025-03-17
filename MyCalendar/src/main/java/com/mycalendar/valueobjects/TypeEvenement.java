package com.mycalendar.valueobjects;

/**
 * Value Object représentant le type d'un événement
 * Implémenté comme une énumération pour garantir un ensemble fini de valeurs possibles
 */
public enum TypeEvenement {
    RDV_PERSONNEL("RDV_PERSONNEL", "Rendez-vous personnel", false, false, false),
    REUNION("REUNION", "Réunion", true, true, false),
    PERIODIQUE("PERIODIQUE", "Événement périodique", false, false, true),
    TACHE("TACHE", "Tâche", false, false, false);

    private final String code;
    private final String libelle;
    private final boolean necessiteLieu;
    private final boolean necessiteParticipants;
    private final boolean estPeriodique;
    
    /**
     * Constructeur privé pour l'énumération
     * 
     * @param code Code interne du type
     * @param libelle Libellé utilisateur
     * @param necessiteLieu Indique si un lieu est requis
     * @param necessiteParticipants Indique si des participants sont requis
     * @param estPeriodique Indique si l'événement est périodique
     */
    private TypeEvenement(String code, String libelle, boolean necessiteLieu, 
                         boolean necessiteParticipants, boolean estPeriodique) {
        this.code = code;
        this.libelle = libelle;
        this.necessiteLieu = necessiteLieu;
        this.necessiteParticipants = necessiteParticipants;
        this.estPeriodique = estPeriodique;
    }
    
    /**
     * Obtient le type d'événement à partir de son code
     * 
     * @param code Code du type
     * @return Le type d'événement correspondant
     * @throws IllegalArgumentException si le code est invalide
     */
    public static TypeEvenement fromString(String code) {
        for (TypeEvenement type : TypeEvenement.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Type d'événement inconnu: " + code);
    }
    
    /**
     * Obtient le code du type
     * 
     * @return Code du type
     */
    public String getCode() {
        return code;
    }
    
    /**
     * Obtient le libellé du type
     * 
     * @return Libellé du type
     */
    public String getLibelle() {
        return libelle;
    }
    
    /**
     * Indique si ce type d'événement nécessite un lieu
     * 
     * @return true si un lieu est requis, false sinon
     */
    public boolean necessiteLieu() {
        return necessiteLieu;
    }
    
    /**
     * Indique si ce type d'événement nécessite des participants
     * 
     * @return true si des participants sont requis, false sinon
     */
    public boolean necessiteParticipants() {
        return necessiteParticipants;
    }
    
    /**
     * Indique si ce type d'événement est périodique
     * 
     * @return true si l'événement est périodique, false sinon
     */
    public boolean estPeriodique() {
        return estPeriodique;
    }
    
    @Override
    public String toString() {
        return libelle;
    }
}