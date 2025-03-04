package trivia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

// REFACTOR ME
public class Game implements IGame {
   ArrayList<String> players = new ArrayList<>();
   int[] places = new int[6];
   int[] purses = new int[6];
   boolean[] inPenaltyBox = new boolean[6];

   private final String[] CATEGORIES = {"Pop", "Science", "Sports", "Rock"};

   private final Map<String, LinkedList<String>> questionMap = initializeQuestionMap();

   private Map<String, LinkedList<String>> initializeQuestionMap() {
      Map<String, LinkedList<String>> map = new HashMap<>();
      for (String category : CATEGORIES) {
         map.put(category, new LinkedList<>());
      }
      return map;
   }

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public Game() {
      initializeQuestions();
   }
  
   private void initializeQuestions() {
      for (int i = 0; i < 50; i++) {
          final int questionNumber = i;
          questionMap.forEach((category, questions) -> {
              String questionText = category + " Question " + questionNumber;
              questions.addLast(questionText);
          });
      }
   }

   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {
      places[howManyPlayers()] = 1;
      purses[howManyPlayers()] = 0;
      inPenaltyBox[howManyPlayers()] = false;
      players.add(playerName);

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      String playerName = players.get(currentPlayer);
      System.out.println(playerName + " is the current player");
      System.out.println("They have rolled a " + roll);
  
      if (inPenaltyBox[currentPlayer]) {
         handlePenaltyBoxRoll(roll);
      } else {
         movePlayerPosition(roll);
         announcePositionAndCategory();
         askQuestion();
      }
   }
  
   private void handlePenaltyBoxRoll(int roll) {
      boolean isOdd = roll % 2 != 0;
      isGettingOutOfPenaltyBox = isOdd;
      
      if (isOdd) {
         System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
         movePlayerPosition(roll);
         announcePositionAndCategory();
         askQuestion();
      } else {
         System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
      }
   }
  
   private void movePlayerPosition(int roll) {
      places[currentPlayer] = (places[currentPlayer] + roll) % 12;
      if (places[currentPlayer] == 0) {
         places[currentPlayer] = 12;
      }
   }
  
   private void announcePositionAndCategory() {
      System.out.println(players.get(currentPlayer) + "'s new location is " + places[currentPlayer]);
      System.out.println("The category is " + currentCategory());
   }

   private void askQuestion() {
      String category = currentCategory();
      System.out.println(questionMap.get(category).removeFirst());
   }


   private String currentCategory() {
      int position = places[currentPlayer] - 1;
      return CATEGORIES[position % 4];
   }

   public boolean handleCorrectAnswer() {
      // Si le joueur est dans la penalty box mais ne peut pas en sortir
      if (inPenaltyBox[currentPlayer] && !isGettingOutOfPenaltyBox) {
          moveToNextPlayer();
          return true;
      }
      
      // Cas où le joueur n'est pas dans la penalty box
      // OU est dans la penalty box mais peut en sortir
      System.out.println("Answer was correct!!!!");
      purses[currentPlayer]++;
      System.out.println(players.get(currentPlayer) + " now has " + 
                        purses[currentPlayer] + " Gold Coins.");
  
      boolean winner = didPlayerWin();
      moveToNextPlayer();
      return winner;
   }
   
   private void moveToNextPlayer() {
      currentPlayer = (currentPlayer + 1) % players.size();
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;
  
      moveToNextPlayer();
      return true;
   }


   private boolean didPlayerWin() {
      return !(purses[currentPlayer] == 6);
   }
}
