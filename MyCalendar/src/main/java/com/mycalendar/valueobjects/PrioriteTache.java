package com.mycalendar.valueobjects;

/**
 * Value Object représentant la priorité d'une tâche
 * Implémenté comme une énumération pour garantir un ensemble fini de valeurs possibles
 */
public enum PrioriteTache {
    HAUTE("HAUTE", "Haute"),
    MOYENNE("MOYENNE", "Moyenne"),
    BASSE("BASSE", "Basse");
    
    private final String code;
    private final String libelle;
    
    /**
     * Constructeur privé pour l'énumération
     * 
     * @param code Code interne de la priorité
     * @param libelle Libellé utilisateur
     */
    private PrioriteTache(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }
    
    /**
     * Obtient la priorité à partir de son code
     * 
     * @param code Code de la priorité
     * @return La priorité correspondante
     * @throws IllegalArgumentException si le code est invalide
     */
    public static PrioriteTache fromString(String code) {
        for (PrioriteTache priorite : PrioriteTache.values()) {
            if (priorite.getCode().equals(code)) {
                return priorite;
            }
        }
        throw new IllegalArgumentException("Priorité de tâche inconnue: " + code);
    }
    
    /**
     * Obtient le code de la priorité
     * 
     * @return Code de la priorité
     */
    public String getCode() {
        return code;
    }
    
    /**
     * Obtient le libellé de la priorité
     * 
     * @return Libellé de la priorité
     */
    public String getLibelle() {
        return libelle;
    }
    
    @Override
    public String toString() {
        return libelle;
    }
}