package trivia;

public class Player {
    private final String name;
    private int position = 1;
    private int coins = 0;
    private boolean inPenaltyBox = false;
    
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
    
    public int getCoins() {
        return coins;
    }
    
    public boolean hasWon(int winningCoins) {
        return coins == winningCoins;
    }
}
