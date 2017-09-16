/*
* Skeleton Program code for the AQA A Level Paper 1 2018 examination
* this code should be used in conjunction with the Preliminary Material
* written by the AQA Programmer Team
* developed using Netbeans IDE 8.1
*
*/

package words.with.aqa;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

public class Main {
    
    class QueueOfTiles
    {
        protected char[] contents;
        protected int rear;
        protected int maxSize;
        
        QueueOfTiles(int maxSize)
        {
            contents = new char[maxSize];
            rear = -1;
            this.maxSize = maxSize;
            for (int count = 0; count < maxSize; count++)
            { 
                add();
            }
        }

        boolean isEmpty()
        {
            if (rear == -1) 
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        
        char remove()
        {
            if (isEmpty()) {
                return '\n';
            }
            else
            {
                char item = contents[0];
                for (int count = 1; count < rear + 1; count++) {
                    contents[count - 1] = contents[count];
                }
                contents[rear] = '\n';
                rear -= 1;
                return item;
            }
        }
        
        void add()
        {
            Random rnd = new Random();
            if (rear < maxSize - 1) 
            {
                int randNo = rnd.nextInt(25);
                rear += 1;
                contents[rear] = (char)(65 + randNo);
            }
        }
        
        void show()
        {
            if (rear != -1) 
            {
                Console.println();
                Console.print("The contents of the queue are: ");
                for(char item : contents)
                {
                    Console.print(item);
                }
                Console.println();
            }
        }
    }
        
    Map createTileDictionary()
    {
        Map<Character, Integer> tileDictionary = new HashMap<Character, Integer>();
        for (int count = 0; count < 26; count++) 
        {
            switch (count) {
                case 0:
                case 4:
                case 8:
                case 13:
                case 14:
                case 17:
                case 18:
                case 19:
                    tileDictionary.put((char)(65 + count), 1);
                    break;
                case 1:
                case 2:
                case 3:
                case 6:
                case 11:
                case 12:
                case 15:
                case 20:
                    tileDictionary.put((char)(65 + count), 2);
                    break;
                case 5:
                case 7:
                case 10:
                case 21:
                case 22:
                case 24:
                    tileDictionary.put((char)(65 + count), 3);
                    break;
                default:
                    tileDictionary.put((char)(65 + count), 5);
                    break;
            }
        }
        return tileDictionary;
    }

    void displayTileValues(Map tileDictionary, String[] allowedWords)
    {
        Console.println();
        Console.println("TILE VALUES");
        Console.println();
        for (Object letter : tileDictionary.keySet()) 
        {
            int points = (int)tileDictionary.get(letter);
            Console.println("Points for " + letter + ": " + points);
        }
        Console.println();
    }

    String getStartingHand(QueueOfTiles tileQueue, int startHandSize)
    {
        String hand = "";
        for (int count = 0; count < startHandSize; count++) {
            hand += tileQueue.remove();
            tileQueue.add();
        }
        return hand;
    }

    String[] loadAllowedWords()
    {
        String[] allowedWords = {};
        try {
            Path filePath = new File("aqawords.txt").toPath();
            Charset charset = Charset.defaultCharset();        
            List<String> stringList = Files.readAllLines(filePath, charset);
            allowedWords = new String[stringList.size()];
            int count = 0;
            for(String word: stringList)
            {
                allowedWords[count] = word.trim().toUpperCase();
                count++;
            }
        } catch (IOException e) {
        }
        return allowedWords;            
    }
    
    boolean checkWordIsInTiles(String word, String playerTiles)
    {
        boolean inTiles = true;
        String copyOfTiles = playerTiles;
        for (int count = 0; count < word.length(); count++) {
            if(copyOfTiles.contains(word.charAt(count) + ""))
            {
                copyOfTiles = copyOfTiles.replaceFirst(word.charAt(count) + "", "");
            }
            else
            {
                inTiles = false;
            }
        }
        return inTiles;
    }
    
    boolean checkWordIsValid(String word, String[] allowedWords)
    {
        boolean validWord = false;
        int count = 0;
        while(count < allowedWords.length && !validWord)
        {
            if(allowedWords[count].equals(word))
            {
                validWord = true;
            }
            count += 1;
        }
        return validWord;
    }

    void addEndOfTurnTiles(QueueOfTiles tileQueue, Tiles playerTiles,
            String newTileChoice, String choice)
    {
        int noOfEndOfTurnTiles;
        if(newTileChoice.equals("1"))
        {
            noOfEndOfTurnTiles = choice.length();
        }
        else if(newTileChoice.equals("2"))
        {
            noOfEndOfTurnTiles = 3;
        }   
        else
        {
            noOfEndOfTurnTiles = choice.length()+3;
        }
        for (int count = 0; count < noOfEndOfTurnTiles; count++) 
        {
            playerTiles.playerTiles += tileQueue.remove();
            tileQueue.add();
        }
    }
    
    void fillHandWithTiles(QueueOfTiles tileQueue, Tiles playerTiles,
            int maxHandSize)
    {
        while(playerTiles.playerTiles.length() <= maxHandSize)
        {
            playerTiles.playerTiles += tileQueue.remove();
            tileQueue.add();
        }
    }
    
    int getScoreForWord(String word, Map tileDictionary)
    {
        int score = 0;
        for (int count = 0; count < word.length(); count++) 
        {
            score += (int)tileDictionary.get(word.charAt(count));
        }
        if(word.length() > 7)
        {
            score += 20;
        }
        else if(word.length() > 5)
        {
            score += 5;
        }
        return score;       
    }
    
    void updateAfterAllowedWord(String word, Tiles playerTiles, 
            Score playerScore, TileCount playerTilesPlayed, Map tileDictionary, 
            String[] allowedWords)
    {
        playerTilesPlayed.numberOfTiles += word.length();
        for(char letter : word.toCharArray())
        {
            playerTiles.playerTiles = playerTiles.playerTiles.replaceFirst(letter + "", "");
        }
        playerScore.score += getScoreForWord(word, tileDictionary);      
    }
    
    int updateScoreWithPenalty(int playerScore, String playerTiles, 
            Map tileDictionary)
    {
        for (int count = 0; count < playerTiles.length(); count++) 
        {
            playerScore -= (int)tileDictionary.get(playerTiles.charAt(count));
        }
        return playerScore;
    }
    
    String getChoice()
    {
        Console.println();
        Console.println("Either:");
        Console.println("     enter the word you would like to play OR");
        Console.println("     press 1 to display the letter values OR");
        Console.println("     press 4 to view the tile queue OR");
        Console.println("     press 7 to view your tiles again OR");
        Console.println("     press 0 to fill hand and stop the game.");
        Console.print("> ");
        String choice = Console.readLine();
        Console.println();
        choice = choice.toUpperCase(); 
        return choice;
    }
    
    String getNewTileChoice()
    {
      String newTileChoice = "";
      do
      {
          Console.println("Do you want to:"); 
          Console.println("     replace the tiles you used (1) OR");
          Console.println("     get three extra tiles (2) OR");
          Console.println("     replace the tiles you used and get three extra tiles (3) OR");
          Console.println("     get no new tiles (4)?");
          Console.print("> ");
          newTileChoice = Console.readLine();
      } while(!"1".equals(newTileChoice) && !"2".equals(newTileChoice) &&
              !"3".equals(newTileChoice) && !"4".equals(newTileChoice));
      return newTileChoice;   
    }
    
    void displayTilesInHand(String PlayerTiles)
    {
        Console.println();
        Console.println("Your current hand: " + PlayerTiles);
    }
    
    void haveTurn(String playerName, Tiles playerTiles, 
            TileCount playerTilesPlayed, Score playerScore, Map tileDictionary, 
            QueueOfTiles tileQueue, String[] allowedWords, int maxHandSize, 
            int noOfEndOfTurnTiles)
    {
      Console.println();
      Console.println(playerName + " it is your turn.");
      displayTilesInHand(playerTiles.playerTiles);
      String newTileChoice = "2";
      boolean validChoice = false;
      boolean validWord;
      while (!validChoice)
      {
        String choice = getChoice();
        if (choice.equals("1"))
        {
          displayTileValues(tileDictionary, allowedWords);
        }
        else if (choice.equals("4"))
        {
          tileQueue.show();
        }
        else if (choice.equals("7"))
        {
          displayTilesInHand(playerTiles.playerTiles);
        }
        else if (choice.equals("0"))
        {
          validChoice = true;
          fillHandWithTiles(tileQueue, playerTiles, maxHandSize);
        }
        else
        {
          validChoice = true;
          if (choice.length() == 0)
          {
            validWord = false;
          }
          else
          {
            validWord = checkWordIsInTiles(choice, playerTiles.playerTiles);
          }
          if (validWord)
          {
            validWord = checkWordIsValid(choice, allowedWords);
            if (validWord)
            {
              Console.println();
              Console.println("Valid word");
              Console.println();
              updateAfterAllowedWord(choice, playerTiles, playerScore, 
                      playerTilesPlayed, tileDictionary, allowedWords);
              newTileChoice = getNewTileChoice();
            }
          }
          if (!validWord)
          {
            Console.println();
            Console.println("Not a valid attempt, you lose your turn.");
            Console.println();
          }
          if (!newTileChoice.equals("4"))
          {
            addEndOfTurnTiles(tileQueue, playerTiles, newTileChoice, choice);
          }
          Console.println();
          Console.println("Your word was:" + choice);
          Console.println("Your new score is:" + playerScore.score);
          Console.println("You have played " + playerTilesPlayed.numberOfTiles + " tiles so far in this game.");
        }
      }
    }
    
    void displayWinner(int playerOneScore, int playerTwoScore)
    {
      Console.println();
      Console.println("**** GAME OVER! ****");
      Console.println();
      Console.println("Player One your score is " + playerOneScore);
      Console.println("Player Two your score is " + playerTwoScore);
      if (playerOneScore > playerTwoScore)
      {
        Console.println("Player One wins!");
      }
      else if (playerTwoScore > playerOneScore)
      {
        Console.println("Player Two wins!");
      }
      else
      {
        Console.println("It is a draw!");
      }
      Console.println()  ;
    }
    
    void playGame(String[] allowedWords, Map tileDictionary, boolean randomStart, 
            int startHandSize, int maxHandSize, int maxTilesPlayed, 
            int noOfEndOfTurnTiles)
    {
      Score playerOneScore = new Score();
      playerOneScore.score = 50;
      Score playerTwoScore = new Score();
      playerTwoScore.score = 50;
      TileCount playerOneTilesPlayed = new TileCount();
      playerOneTilesPlayed.numberOfTiles = 0;
      TileCount playerTwoTilesPlayed = new TileCount();
      playerTwoTilesPlayed.numberOfTiles = 0;
      Tiles playerOneTiles = new Tiles();
      Tiles playerTwoTiles = new Tiles();
      QueueOfTiles tileQueue  = new QueueOfTiles(20); 
      if(randomStart)
      {
        playerOneTiles.playerTiles = getStartingHand(tileQueue, startHandSize);
        playerTwoTiles.playerTiles = getStartingHand(tileQueue, startHandSize);
      }
      else
      {
        playerOneTiles.playerTiles = "BTAHANDENONSARJ";
        playerTwoTiles.playerTiles = "CELZXIOTNESMUAA";
      }
      while (playerOneTilesPlayed.numberOfTiles <= maxTilesPlayed && 
              playerTwoTilesPlayed.numberOfTiles <= maxTilesPlayed && 
              playerOneTiles.playerTiles.length() < maxHandSize && 
              playerTwoTiles.playerTiles.length() < maxHandSize)
      {
        haveTurn("Player One", playerOneTiles, playerOneTilesPlayed, playerOneScore, 
                tileDictionary, tileQueue, allowedWords, maxHandSize, 
                noOfEndOfTurnTiles);
        Console.println();
        Console.println("Press Enter to continue");
        Console.readLine();
        Console.println();
        haveTurn("Player Two", playerTwoTiles, playerTwoTilesPlayed, playerTwoScore, 
                tileDictionary, tileQueue, allowedWords, maxHandSize, 
                noOfEndOfTurnTiles);
      }
      playerOneScore.score = updateScoreWithPenalty(playerOneScore.score, 
              playerOneTiles.playerTiles, tileDictionary);
      playerTwoScore.score = updateScoreWithPenalty(playerTwoScore.score, 
              playerTwoTiles.playerTiles, tileDictionary);
      displayWinner(playerOneScore.score, playerTwoScore.score);   
    }

    void displayMenu()
    {
      Console.println();
      Console.println("=========");
      Console.println("MAIN MENU");
      Console.println("=========");
      Console.println();
      Console.println("1. Play game with random start hand");
      Console.println("2. Play game with training start hand");
      Console.println("9. Quit");
      Console.println();
    }  
    
    /*
    * Class Tiles is used to allow the value of a String variable to be 
    * returned from a subroutine
    */
    class Tiles
    {
        String playerTiles;
    } 
    /*
    * Class TileCount is used to allow the value of an integer variable to be 
    * returned from a subroutine
    */
    class TileCount
    {
        int numberOfTiles;
    }
    /*
    * Class Score is used to allow the value of an integer variable to be 
    * returned from a subroutine
    */
    class Score
    {
        int score;
    }   
           
    Main()
    {
      Console.println("++++++++++++++++++++++++++++++++++++++");
      Console.println("+ Welcome to the WORDS WITH AQA game +");
      Console.println("++++++++++++++++++++++++++++++++++++++");
      Console.println();
      Console.println();
      String[] allowedWords = loadAllowedWords();
      Map tileDictionary = createTileDictionary();
      int maxHandSize = 20;
      int maxTilesPlayed = 50;
      int noOfEndOfTurnTiles = 3;
      int startHandSize = 15;
      String choice = "";
      while(!choice.equals("9"))
      {
        displayMenu();
        Console.println("Enter your choice: ");
        choice = Console.readLine();
        if (choice.equals("1"))
        {
          playGame(allowedWords, tileDictionary, true, startHandSize, 
                  maxHandSize, maxTilesPlayed, noOfEndOfTurnTiles);
        }
        else if (choice.equals("2"))
        {
          playGame(allowedWords, tileDictionary, false, 15, maxHandSize, 
                  maxTilesPlayed, noOfEndOfTurnTiles);
        }
      }
    }
    
    public static void main(String[] args) 
    {
        new Main();
    }
    
}