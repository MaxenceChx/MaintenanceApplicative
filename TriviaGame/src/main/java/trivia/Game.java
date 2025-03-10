package trivia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Game implements IGame {
    // Constantes
    private static final int MAX_PLAYERS = 6;
    private static final int BOARD_SIZE = 12;
    private static final int WINNING_COINS = 6;
    private static final int QUESTIONS_PER_CATEGORY = 50;

    // État du jeu
    private final List<Player> players = new ArrayList<>();
    private final Map<Categories, Queue<String>> questionMap = new HashMap<>();

    // État du tour actuel
    private int currentPlayerIndex = 0;
    private boolean isGettingOutOfPenaltyBox;

    /**
     * Constructeur de la classe Game.
     */
    public Game() {
        initializeQuestions();
    }

    /**
     * Initialise les questions pour chaque catégorie.
     */
    private void initializeQuestions() {
        for (Categories category : Categories.values()) {
            Queue<String> questions = new LinkedList<>();
            for (int i = 0; i < QUESTIONS_PER_CATEGORY; i++) {
                questions.add(category + " Question " + i);
            }
            questionMap.put(category, questions);
        }
    }

    /**
     * Vérifie si le jeu est jouable.
     * @return true si le jeu est jouable, false sinon.
     */
    public boolean isPlayable() {
        return players.size() >= 2;
    }

    /**
     * Ajoute un joueur au jeu.
     * @param playerName le nom du joueur à ajouter.
     * @return true si le joueur a été ajouté, false sinon.
     */
    public boolean add(String playerName) {
        if (players.size() >= MAX_PLAYERS) {
            System.out.println("Cannot add more than " + MAX_PLAYERS + " players");
            return false;
        }
        players.add(new Player(playerName));
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    /**
     * Lance un tour de jeu.
     * @param roll le résultat du lancer de dé.
     */
    public void roll(int roll) {
        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println(currentPlayer.getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox()) {
            handlePenaltyBoxRoll(currentPlayer, roll);
        } else {
            movePlayerPosition(currentPlayer, roll);
            askQuestion();
        }
    }

    /**
     * Gère le lancer de dé d'un joueur dans la prison.
     * @param player le joueur actuel.
     * @param roll le résultat du lancer de dé.
     */
    private void handlePenaltyBoxRoll(Player player, int roll) {
        isGettingOutOfPenaltyBox = roll % 2 != 0;
        if (isGettingOutOfPenaltyBox) {
            System.out.println(player.getName() + " is getting out of the penalty box");
            movePlayerPosition(player, roll);
            askQuestion();
        } else {
            System.out.println(player.getName() + " is not getting out of the penalty box");
            moveToNextPlayer();
        }
    }

    /**
     * Déplace un joueur sur le plateau.
     * @param player le joueur à déplacer.
     * @param roll le résultat du lancer de dé.
     */
    private void movePlayerPosition(Player player, int roll) {
        player.move(roll, BOARD_SIZE);
        System.out.println(player.getName() + "'s new location is " + player.getPosition());
        System.out.println("The category is " + currentCategory());
    }

    /**
     * Pose une question au joueur actuel.
     */
    private void askQuestion() {
        Categories category = currentCategory();
        Queue<String> questions = questionMap.get(category);
        if (questions.isEmpty()) {
            System.out.println("No more " + category + " questions!");
        } else {
            System.out.println(questions.poll());
        }
    }

    /**
     * Retourne la catégorie actuelle.
     * @return la catégorie actuelle.
     */
    private Categories currentCategory() {
        return Categories.values()[(players.get(currentPlayerIndex).getPosition() - 1) % Categories.values().length];
    }

    /**
     * Gère une réponse correcte.
     * @return true si le jeu continue, false si un joueur a gagné.
     */
    public boolean handleCorrectAnswer() {
        Player currentPlayer = players.get(currentPlayerIndex);
        if (currentPlayer.isInPenaltyBox() && !isGettingOutOfPenaltyBox) {
            moveToNextPlayer();
            return true;
        }
        System.out.println("Answer was correct!!!!");
        currentPlayer.addCoin();
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getCoins() + " Gold Coins.");
        boolean winner = !currentPlayer.hasWon(WINNING_COINS);
        moveToNextPlayer();
        return winner;
    }

    /**
     * Gère une réponse incorrecte.
     * @return true comme le jeu continue.
     */
    public boolean wrongAnswer() {
        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer.getName() + " was sent to the penalty box");
        currentPlayer.setInPenaltyBox(true);
        moveToNextPlayer();
        return true;
    }

    /**
     * Passe au joueur suivant.
     */
    private void moveToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
}