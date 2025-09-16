package com.ghiloufi.kata.computer;

import com.ghiloufi.kata.display.ScoreboardDisplay;
import com.ghiloufi.kata.display.TerminalScoreRenderer;
import com.ghiloufi.kata.domain.Game;
import com.ghiloufi.kata.domain.GameState;
import com.ghiloufi.kata.domain.Player;
import com.ghiloufi.kata.validator.InputValidator;

public class TennisScoreComputer {

  private static final int MAX_INPUT_LENGTH = 10000;

  private final Player playerA;
  private final Player playerB;
  private final ScoreboardDisplay displayService;
  private GameState gameState;

  public TennisScoreComputer() {
    this.playerA = new Player("A");
    this.playerB = new Player("B");
    this.gameState = GameState.IN_PROGRESS;
    this.displayService = new ScoreboardDisplay(System.out::println, new TerminalScoreRenderer());
  }

  public TennisScoreComputer(ScoreboardDisplay displayService) {
    this.playerA = new Player("A");
    this.playerB = new Player("B");
    this.gameState = GameState.IN_PROGRESS;
    this.displayService = displayService;
  }

  /**
   * Processes a sequence of balls won and displays the score after each ball
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
      if (isGameFinished(gameState)) {
        break;
      }

      processBall(ball);

      displayService.displayScore(new Game(playerA, playerB, gameState));

      if (isGameFinished(gameState)) {
        break;
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

  private void resetGame() {
    playerA.reset();
    playerB.reset();
    gameState = GameState.IN_PROGRESS;
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

  private boolean isGameFinished(GameState gameState) {
    return gameState == GameState.GAME_WON_A || gameState == GameState.GAME_WON_B;
  }
}
