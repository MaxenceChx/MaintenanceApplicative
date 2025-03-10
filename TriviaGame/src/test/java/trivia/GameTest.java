package trivia;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class GameTest {
    private Game game;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    
    @BeforeEach
    public void setUp() {
        game = new Game();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }
    
    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }
    
    @Test
    @Disabled("Utiliser uniquement pour comparer l'ancienne et la nouvelle implémentation")
    public void caracterizationTest() {
        // runs 10.000 "random" games to see the output of old and new code mathces
        for (int seed = 1; seed < 10_000; seed++) {
            testSeed(seed, false);
        }
    }

    private void testSeed(int seed, boolean printExpected) {
        String expectedOutput = extractOutput(new Random(seed), new GameOld());
        if (printExpected) {
            System.out.println(expectedOutput);
        }
        String actualOutput = extractOutput(new Random(seed), new Game());
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    @Disabled("enable back and set a particular seed to see the output")
    public void oneSeed() {
        testSeed(1, true);
    }

    private String extractOutput(Random rand, IGame aGame) {
        PrintStream old = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (PrintStream inmemory = new PrintStream(baos)) {
            // WARNING: System.out.println() doesn't work in this try {} as the sysout is captured and recorded in memory.
            System.setOut(inmemory);

            aGame.add("Chet");
            aGame.add("Pat");
            aGame.add("Sue");

            boolean notAWinner = false;
            do {
                aGame.roll(rand.nextInt(5) + 1);

                if (rand.nextInt(9) == 7) {
                    notAWinner = aGame.wrongAnswer();
                } else {
                    notAWinner = aGame.handleCorrectAnswer();
                }

            } while (notAWinner);
        } finally {
            System.setOut(old);
        }

        return new String(baos.toByteArray());
    }
    
    /* Tests pour la limite maximale de 6 joueurs */
    
    @Test
    public void testMaxSixPlayers() {
        // Ajouter 6 joueurs
        assertTrue(game.add("Player1"));
        assertTrue(game.add("Player2"));
        assertTrue(game.add("Player3"));
        assertTrue(game.add("Player4"));
        assertTrue(game.add("Player5"));
        assertTrue(game.add("Player6"));
        
        // Le 7ème joueur doit être refusé
        assertFalse(game.add("Player7"));
        
        // Vérifier le message d'erreur
        String output = outputStream.toString();
        assertTrue(output.contains("Cannot add more than 6 players"));
    }
    
    /* Tests pour la nouvelle catégorie Géographie */
    
    @Test
    public void testGeographyCategory() {
        // Vérifier que la catégorie Géographie existe dans l'énumération Categories
        boolean hasGeographyCategory = false;
        for (Categories category : Categories.values()) {
            if (category.toString().equalsIgnoreCase("Geography") || 
                category.toString().equalsIgnoreCase("Géographie")) {
                hasGeographyCategory = true;
                break;
            }
        }
        assertTrue(hasGeographyCategory, "La catégorie Géographie devrait exister");
        
        // Initialiser un jeu
        game.add("Player1");
        game.add("Player2");
        game.startGame();
        
        // Jouer plusieurs tours pour essayer d'atteindre la catégorie Géographie
        boolean foundGeographyCategory = false;
        for (int i = 0; i < 20; i++) {
            outputStream.reset();
            game.roll(i % 6 + 1);
            game.handleCorrectAnswer();
            
            String output = outputStream.toString();
            if (output.contains("Geography") || output.contains("Géographie")) {
                foundGeographyCategory = true;
                break;
            }
        }
        
        assertTrue(foundGeographyCategory, "La catégorie Géographie devrait apparaître pendant le jeu");
    }
    
    /* Tests pour la condition de 2 joueurs minimum */
    
    @Test
    public void testMinTwoPlayers() {
        // Essayer de démarrer avec 0 joueur
        assertFalse(game.startGame());
        
        // Ajouter 1 joueur et essayer de démarrer
        game.add("Player1");
        assertFalse(game.startGame());
        
        // Vérifier le message d'erreur
        String output = outputStream.toString();
        assertTrue(output.contains("At least two players are required"));
        
        // Ajouter un second joueur et réessayer
        outputStream.reset();
        game.add("Player2");
        assertTrue(game.startGame());
        
        // Vérifier le message de succès
        output = outputStream.toString();
        assertTrue(output.contains("Game has started"));
    }
    
    /* Tests pour l'ajout de joueurs après le début du jeu */
    
    @Test
    public void testNoPlayersAfterGameStart() {
        // Ajouter 2 joueurs et démarrer
        game.add("Player1");
        game.add("Player2");
        game.startGame();
        
        // Essayer d'ajouter un joueur après le démarrage
        outputStream.reset();
        assertFalse(game.add("LatePlayer"));
        
        // Vérifier le message d'erreur
        String output = outputStream.toString();
        assertTrue(output.contains("Cannot add new players after the game has started"));
    }
    
    /* Tests pour les noms de joueurs uniques */
    
    @Test
    public void testUniquePlayerNames() {
        // Ajouter un premier joueur
        assertTrue(game.add("Player1"));
        
        // Essayer d'ajouter un joueur avec le même nom
        outputStream.reset();
        assertFalse(game.add("Player1"));
        
        // Vérifier le message d'erreur
        String output = outputStream.toString();
        assertTrue(output.contains("A player with this name already exists"));
        
        // On peut ajouter un joueur avec un nom différent
        outputStream.reset();
        assertTrue(game.add("Player2"));
    }
    
    /* Tests pour la fonctionnalité de seconde chance après une réponse incorrecte */
    
    @Test
    public void testSecondChanceFeature() {
        // Initialisation du jeu
        game.add("Player1");
        game.add("Player2");
        game.startGame();
        
        // Premier joueur fait un lancer et répond incorrectement
        outputStream.reset();
        game.roll(1);
        game.wrongAnswer();
        
        // Vérifier qu'il obtient une seconde chance
        String output = outputStream.toString();
        assertTrue(output.contains("gets a second chance"));
        assertFalse(output.contains("was sent to the penalty box"));
        
        // Réussite à la seconde chance
        outputStream.reset();
        game.handleCorrectAnswer();
        output = outputStream.toString();
        assertTrue(output.contains("Answer was correct"));
        
        // Essai avec un échec à la seconde chance
        outputStream.reset();
        game.roll(1);
        game.wrongAnswer(); // Première réponse incorrecte -> seconde chance
        game.wrongAnswer(); // Échec à la seconde chance -> prison
        
        output = outputStream.toString();
        assertTrue(output.contains("failed their second chance"));
        assertTrue(output.contains("was sent to the penalty box"));
    }
    
    /* Tests pour la fonctionnalité de série et de double points */
    
    @Test
    public void testStreakAndDoublePoints() {
        // Initialisation
        game.add("Player1");
        game.add("Player2");
        game.startGame();
        
        // Réinitialiser la sortie
        outputStream.reset();
        
        // Faire 3 bonnes réponses consécutives (pas encore en série)
        for (int i = 0; i < 3; i++) {
            game.roll(1);
            game.handleCorrectAnswer();
            
            // Skip Player2's turn
            game.roll(1);
            game.handleCorrectAnswer();
        }
        
        // La 4ème réponse correcte (maintenant en série) devrait donner 2 points
        outputStream.reset();
        game.roll(1);
        game.handleCorrectAnswer();
        
        String output = outputStream.toString();
        assertTrue(output.contains("on a streak! Double points awarded"));
        
        // Vérifier la gestion de la perte de série
        outputStream.reset();
        game.roll(1);
        game.handleCorrectAnswer(); // Skip Player2's turn
        game.roll(1);
        game.wrongAnswer(); // Player1 répond incorrectement en série
        
        output = outputStream.toString();
        assertTrue(output.contains("lost their streak"));
        assertFalse(output.contains("was sent to the penalty box"));
    }
    
    /* Tests pour la condition de victoire modifiée (double de points) */
    
    @Test
    public void testModifiedWinCondition() {
        // Initialisation
        game.add("Player1");
        game.add("Player2");
        game.startGame();
        
        // Amener Player1 à 5 points (impair)
        for (int i = 0; i < 4; i++) {
            game.roll(1);
            game.handleCorrectAnswer(); // Player1 gagne 1 point
            
            game.roll(1);
            game.handleCorrectAnswer(); // Skip Player2's turn
        }
        
        // Avec 5 points (impair), le joueur ne peut pas gagner même si > WINNING_COINS-1
        outputStream.reset();
        game.roll(1);
        boolean gameStillRunning = game.handleCorrectAnswer(); // Player1 à 7 points
        assertTrue(gameStillRunning, "Le joueur ne devrait pas gagner avec un nombre impair de points");
        
        // Une bonne réponse supplémentaire amène à 7 points (toujours impair)
        game.roll(1);
        game.handleCorrectAnswer(); // Skip Player2's turn
        
        outputStream.reset();
        game.roll(1);
        gameStillRunning = game.wrongAnswer(); // Player1 à 7 points
		gameStillRunning = game.handleCorrectAnswer(); // Player1 à 8 points
        
        assertFalse(gameStillRunning, "Le joueur devrait gagner avec 8 points (pair et > WINNING_COINS)");
        
        String output = outputStream.toString();
        assertTrue(output.contains("has won the game"));
    }
    
    /* Test intégré des fonctionnalités de seconde chance et de série */
    
    @Test
    public void testSecondChanceAndStreakIntegration() {
        // Initialisation
        game.add("Player1");
        game.add("Player2");
        game.startGame();
        
        // Player1 établit une série de 3 bonnes réponses
        for (int i = 0; i < 3; i++) {
            game.roll(1);
            game.handleCorrectAnswer();
            
            // Skip Player2
            game.roll(1);
            game.handleCorrectAnswer();
        }
        
        // Player1 répond incorrectement pendant sa série
        outputStream.reset();
        game.roll(1);
        game.wrongAnswer();
        
        // Vérifier que Player1 perd sa série mais ne va pas en prison
        String output = outputStream.toString();
        assertTrue(output.contains("lost their streak"));
        assertFalse(output.contains("was sent to the penalty box"));
        
        // Player1 peut répondre à la seconde chance
        outputStream.reset();
        game.handleCorrectAnswer();
        
        // Player2 établit une série
        for (int i = 0; i < 6; i++) {
            game.roll(1);
            game.handleCorrectAnswer();
            
            // Skip Player1
            game.roll(1);
            game.handleCorrectAnswer();
        }
        
        // Player2 répond incorrectement en dehors d'une série
        outputStream.reset();
        game.roll(1);
        game.wrongAnswer();
        
        // Vérifier que Player2 obtient une seconde chance
        output = outputStream.toString();
        assertTrue(output.contains("gets a second chance"));
        
        // Player2 échoue à sa seconde chance
        outputStream.reset();
        game.wrongAnswer();
        
        // Vérifier que Player2 va en prison
        output = outputStream.toString();
        assertTrue(output.contains("was sent to the penalty box"));
    }
}