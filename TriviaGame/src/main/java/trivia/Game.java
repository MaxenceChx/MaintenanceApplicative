package trivia;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Game implements IGame {
    // Constantes
    private static final int MAX_PLAYERS = 6;
    private static final int BOARD_SIZE = 12;
    private static final int WINNING_COINS = 6;
    private static final int QUESTIONS_PER_CATEGORY = 50;
    private static final int STREAK_THRESHOLD = 3; // Nombre de bonnes réponses consécutives pour commencer une série

    // État du jeu
    private final List<Player> players = new ArrayList<>();
    private final Map<Categories, Queue<String>> questionMap = new HashMap<>();
    private boolean gameStarted = false;

    // État du tour actuel
    private int currentPlayerIndex = 0;
    private boolean isGettingOutOfPenaltyBox;
    private boolean secondChanceActive = false; // Indique si le joueur a droit à une seconde chance
    private Categories lastCategory = null; // Stocke la dernière catégorie pour la seconde chance

    /**
     * Constructeur de la classe Game.
     */
    public Game() {
        initializeQuestions();
    }

    /**
     * Initialise les questions du jeu.
     */
    private void initializeQuestions() {
        for (Categories category : Categories.values()) {
            Queue<String> questions = loadQuestionsFromFile(category);
            questionMap.put(category, questions);
        }
    }

    /**
     * Charge les questions d'une catégorie à partir d'un fichier.
     * @param category la catégorie des questions à charger.
     * @return une file de questions.
     */
    private Queue<String> loadQuestionsFromFile(Categories category) {
        Queue<String> questions = new LinkedList<>();
        String filename = category.getFilename();
        Properties properties = new Properties();
        
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filename)) {
            // If file doesn't exist, fall back to generated questions
            if (input == null) {
                System.out.println("Unable to find " + filename + ". Using generated questions instead.");
                return generateDefaultQuestions(category);
            }
            
            properties.load(input);
            
            // Load questions from properties file
            for (int i = 1; i <= QUESTIONS_PER_CATEGORY; i++) {
                String question = properties.getProperty("question." + i);
                if (question != null) {
                    questions.add(question);
                }
            }
            
            System.out.println("Loaded " + questions.size() + " " + category + " questions from " + filename);
            
            // If we didn't get enough questions, add generated ones to reach the desired count
            if (questions.size() < QUESTIONS_PER_CATEGORY) {
                int remaining = QUESTIONS_PER_CATEGORY - questions.size();
                System.out.println("Adding " + remaining + " generated " + category + " questions");
                for (int i = questions.size() + 1; i <= QUESTIONS_PER_CATEGORY; i++) {
                    questions.add(category + " Question " + i);
                }
            }
            
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
            return generateDefaultQuestions(category);
        }
        
        return questions;
    }

    /**
     * Génère des questions par défaut pour une catégorie.
     * @param category la catégorie des questions à générer.
     * @return une file de questions.
     */
    private Queue<String> generateDefaultQuestions(Categories category) {
        Queue<String> questions = new LinkedList<>();
        for (int i = 0; i < QUESTIONS_PER_CATEGORY; i++) {
            questions.add(category + " Question " + i);
        }
        return questions;
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
        if (gameStarted) {
            System.out.println("Cannot add new players after the game has started.");
            return false;
        }
        if (players.size() >= MAX_PLAYERS) {
            System.out.println("Cannot add more than " + MAX_PLAYERS + " players");
            return false;
        }
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                System.out.println("A player with this name already exists.");
                return false;
            }
        }
        players.add(new Player(playerName));
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    /**
     * Démarre le jeu.
     */
    public boolean startGame() {
        if (players.size() < 2) {
            System.out.println("At least two players are required to start the game.");
            return false;
        }
        gameStarted = true;
        System.out.println("Game has started!");
        return true;
    }

    /**
     * Lance un tour de jeu.
     * @param roll le résultat du lancer de dé.
     */
    public void roll(int roll) {
        if (!gameStarted && !startGame()) {
            return;
        }

        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println(currentPlayer.getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox()) {
            handlePenaltyBoxRoll(currentPlayer, roll);
        } else {
            movePlayerPosition(currentPlayer, roll);
            lastCategory = currentCategory();  // Stocke la catégorie actuelle pour la seconde chance
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
            lastCategory = currentCategory();  // Stocke la catégorie actuelle pour la seconde chance
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
     * Pose une question de seconde chance dans la même catégorie.
     */
    private void askSecondChanceQuestion() {
        Queue<String> questions = questionMap.get(lastCategory);
        if (questions.isEmpty()) {
            System.out.println("No more " + lastCategory + " questions for second chance!");
        } else {
            System.out.println("Second chance question in " + lastCategory + " category:");
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
        
        // Réinitialise la seconde chance si elle était active
        secondChanceActive = false;
        
        // Gestion des séries et attribution des points
        currentPlayer.incrementCorrectAnswers();
        int consecutiveCorrectAnswers = currentPlayer.getConsecutiveCorrectAnswers();

        int points = 1;
        if (consecutiveCorrectAnswers > STREAK_THRESHOLD) {
            points = 2;
            System.out.println(currentPlayer.getName() + " is on a streak! Double points awarded!");
        }
        
        // Ajoute les points
        currentPlayer.addCoins(points);
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getCoins() + " Gold Coins.");
        
        // Vérifie si le joueur a gagné (doit avoir un nombre pair de pièces si > WINNING_COINS)
        boolean winner = currentPlayer.hasWon(WINNING_COINS);
        boolean gameEnds = winner;
        
        if (winner) {
            System.out.println(currentPlayer.getName() + " has won the game!");
        }
        
        if (!gameEnds) {
            moveToNextPlayer();
        }
        
        return !gameEnds;
    }

    /**
     * Gère une réponse incorrecte.
     * @return true comme le jeu continue.
     */
    public boolean wrongAnswer() {
        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println("Question was incorrectly answered");

        // Si la seconde chance n'est pas encore active, l'activer
        if (!secondChanceActive) {
            System.out.println(currentPlayer.getName() + " gets a second chance in the " + lastCategory + " category");
            secondChanceActive = true;
            System.out.println(currentPlayer.getName() + " lost their streak!");
            currentPlayer.resetConsecutiveCorrectAnswers();
            askSecondChanceQuestion();
            return true;
        }
        
        // Vérifie si le joueur est en série, si oui, il perd juste sa série
        if (currentPlayer.getConsecutiveCorrectAnswers() >= STREAK_THRESHOLD) {
            System.out.println(currentPlayer.getName() + " lost their streak!");
            currentPlayer.resetConsecutiveCorrectAnswers();
            moveToNextPlayer();
            return true;
        }

        // Réinitialise le compteur de réponses correctes consécutives
        currentPlayer.resetConsecutiveCorrectAnswers();
        
        // Si la seconde chance était déjà active et que le joueur a encore échoué
        System.out.println(currentPlayer.getName() + " failed their second chance");
        System.out.println(currentPlayer.getName() + " was sent to the penalty box");
        currentPlayer.setInPenaltyBox(true);
        secondChanceActive = false;  // Réinitialise la seconde chance
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