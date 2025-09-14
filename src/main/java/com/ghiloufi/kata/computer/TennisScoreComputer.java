package com.ghiloufi.kata.computer;

import com.ghiloufi.kata.domain.GameState;
import com.ghiloufi.kata.domain.Player;
import com.ghiloufi.kata.validator.InputValidator;
import java.util.ArrayList;
import java.util.List;

public class TennisScoreComputer {

  private static final int MAX_INPUT_LENGTH = 10000; // Reasonable limit for input length

  private final Player playerA;
  private final Player playerB;
  private final List<String> scoreHistory;
  private GameState gameState;

  public TennisScoreComputer() {
    this.playerA = new Player("A");
    this.playerB = new Player("B");
    this.gameState = GameState.IN_PROGRESS;
    this.scoreHistory = new ArrayList<>();
  }

  /**
   * Processes a sequence of balls won and prints the score after each ball
   *
   * @param balls String containing 'A' or 'B' characters representing balls won
   * @throws IllegalArgumentException if input is invalid
   */
  public void processGame(String balls) {

    String sanitizedBalls = InputValidator.sanitizeInput(balls);
    InputValidator.validateGameInput(sanitizedBalls);
    InputValidator.validateInputLength(sanitizedBalls, MAX_INPUT_LENGTH);

    resetGame();

    for (char ball : sanitizedBalls.toCharArray()) {
      if (isGameFinished()) {
        break;
      }

      processBall(ball);

      if (isGameFinished()) {
        String winnerMessage = getWinnerMessage();
        System.out.println(winnerMessage);
        scoreHistory.add(winnerMessage);
        break;
      } else {
        String scoreMessage = getScoreMessage();
        System.out.println(scoreMessage);
        scoreHistory.add(scoreMessage);
      }
    }
  }

  /**
   * Processes a single ball won by a player
   *
   * @param winner 'A' or 'B' representing the player who won the ball
   */
  private void processBall(char winner) {
    if (winner == 'A') {
      playerA.incrementPoints();
    } else {
      playerB.incrementPoints();
    }

    updateGameState();
  }

  /** Updates the game state based on current points */
  private void updateGameState() {
    final int pointsA = playerA.getPoints();
    final int pointsB = playerB.getPoints();

    if (pointsA >= 3 && pointsA == pointsB) {
      gameState = GameState.DEUCE;
      return;
    }

    if (pointsA >= 4 && pointsA == pointsB + 1) {
      gameState = GameState.ADVANTAGE_A;
      return;
    }

    if (pointsB >= 4 && pointsB == pointsA + 1) {
      gameState = GameState.ADVANTAGE_B;
      return;
    }

    if (pointsA >= 4) {
      gameState = GameState.GAME_WON_A;
      return;
    }

    if (pointsB >= 4) {
      gameState = GameState.GAME_WON_B;
      return;
    }

    gameState = GameState.IN_PROGRESS;
  }

  /**
   * Gets the current score message formatted according to tennis scoring
   *
   * @return Formatted score string
   */
  private String getScoreMessage() {
    if (gameState == GameState.DEUCE) {
      return "Player A : 40 / Player B : 40 (Deuce)";
    } else if (gameState == GameState.ADVANTAGE_A) {
      return "Player A : 40 / Player B : 40 (Advantage Player A)";
    } else if (gameState == GameState.ADVANTAGE_B) {
      return "Player A : 40 / Player B : 40 (Advantage Player B)";
    } else {
      return String.format(
          "Player A : %s / Player B : %s",
          convertPointsToScore(playerA.getPoints()), convertPointsToScore(playerB.getPoints()));
    }
  }

  /**
   * Converts numeric points to tennis score format
   *
   * @param points Numeric points (0, 1, 2, 3+)
   * @return Tennis score format ("0", "15", "30", "40")
   */
  private String convertPointsToScore(int points) {
    return switch (points) {
      case 0 -> "0";
      case 1 -> "15";
      case 2 -> "30";
      default -> "40";
    };
  }

  /**
   * Gets the winner message
   *
   * @return Winner announcement string
   */
  private String getWinnerMessage() {
    if (gameState == GameState.GAME_WON_A) {
      return "Player A wins the game";
    } else if (gameState == GameState.GAME_WON_B) {
      return "Player B wins the game";
    }
    return "Game not finished";
  }

  /**
   * Checks if the game is finished
   *
   * @return true if game is won, false otherwise
   */
  private boolean isGameFinished() {
    return gameState == GameState.GAME_WON_A || gameState == GameState.GAME_WON_B;
  }

  /** Resets the game to initial state */
  private void resetGame() {
    playerA.reset();
    playerB.reset();
    gameState = GameState.IN_PROGRESS;
    scoreHistory.clear();
  }

  public Player getPlayerA() {
    return playerA;
  }

  public Player getPlayerB() {
    return playerB;
  }

  public GameState getGameState() {
    return gameState;
  }

  public List<String> getScoreHistory() {
    return new ArrayList<>(scoreHistory);
  }
}
