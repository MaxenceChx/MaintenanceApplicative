package trivia;

public class Player {
    private final String name;
    private int position = 1;
    private int coins = 0;
    private boolean inPenaltyBox = false;
    private int consecutiveCorrectAnswers = 0;
    
    public Player(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public int getPosition() {
        return position;
    }
    
    public void move(int roll, int boardSize) {
        position = (position + roll - 1) % boardSize + 1;
    }
    
    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }
    
    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }
    
    public void addCoin() {
        coins++;
    }
    
    public void addCoins(int amount) {
        coins += amount;
    }
    
    public int getCoins() {
        return coins;
    }
    
    /**
     * Vérifie si le joueur a gagné.
     * Les règles sont:
     * - Avoir au moins le nombre de pièces requises
     * - Avoir un nombre pair de pièces (pour les règles de double)
     * 
     * @param winningCoins Le nombre minimum de pièces pour gagner
     * @return true si le joueur a gagné, false sinon
     */
    public boolean hasWon(int winningCoins) {
        return coins >= winningCoins && coins % 2 == 0;
    }
    
    /**
     * Incrémente le compteur de réponses correctes consécutives.
     */
    public void incrementCorrectAnswers() {
        consecutiveCorrectAnswers++;
    }
    
    /**
     * Réinitialise le compteur de réponses correctes consécutives.
     */
    public void resetConsecutiveCorrectAnswers() {
        consecutiveCorrectAnswers = 0;
    }
    
    /**
     * Retourne le nombre de réponses correctes consécutives.
     * @return le nombre de réponses correctes consécutives.
     */
    public int getConsecutiveCorrectAnswers() {
        return consecutiveCorrectAnswers;
    }
}