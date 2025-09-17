package com.ghiloufi.kata.computer;

import com.ghiloufi.kata.display.ScoreboardDisplay;
import com.ghiloufi.kata.domain.Game;
import com.ghiloufi.kata.domain.GameState;
import com.ghiloufi.kata.domain.Player;
import java.util.Iterator;

public class TennisScoreComputer {

  private final Player playerA;
  private final Player playerB;
  private GameState gameState;
  private final ScoreboardDisplay scoreboardDisplay;

  public TennisScoreComputer(final ScoreboardDisplay scoreboardDisplay) {
    this.playerA = new Player("A");
    this.playerB = new Player("B");
    this.gameState = GameState.IN_PROGRESS;
    this.scoreboardDisplay = scoreboardDisplay;
  }

  public void playMatch(Iterator<Character> gameSequence) {

    resetGame();

    while (gameSequence.hasNext() && !isGameFinished(gameState)) {
      final char ball = gameSequence.next();
      processBall(ball);
      scoreboardDisplay.displayScore(new Game(playerA, playerB, gameState));
    }
  }

  private void processBall(char winner) {
    if (winner == 'A') {
      playerA.incrementPoints();
    } else {
      playerB.incrementPoints();
    }

    updateGameState();
  }

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
