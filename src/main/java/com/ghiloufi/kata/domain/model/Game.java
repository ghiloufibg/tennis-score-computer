package com.ghiloufi.kata.domain.model;

import com.ghiloufi.kata.domain.state.GameState;
import com.ghiloufi.kata.domain.state.InProgressGameState;
import java.util.Objects;

public final class Game {

  private final Player playerA;
  private final Player playerB;
  private final GameState gameState;
  private final ScoringSystem scoringSystem;

  public Game(Player playerA, Player playerB, GameState gameState) {
    this(playerA, playerB, gameState, ScoringSystem.STANDARD);
  }

  public Game(Player playerA, Player playerB, GameState gameState, ScoringSystem scoringSystem) {
    this.playerA = playerA;
    this.playerB = playerB;
    this.gameState = gameState;
    this.scoringSystem = scoringSystem;
  }

  public static Game newGame() {
    return new Game(Player.A, Player.B, InProgressGameState.INSTANCE, ScoringSystem.STANDARD);
  }

  public static Game withPlayers(String nameA, String nameB) {
    return new Game(
        Player.withName(nameA),
        Player.withName(nameB),
        InProgressGameState.INSTANCE,
        ScoringSystem.STANDARD);
  }

  public Game scorePoint(Point point) {
    if (isGameFinished()) {
      return this;
    }

    Player newPlayerA = point.isWonBy(Player.A) ? playerA.scorePoint() : playerA;
    Player newPlayerB = point.isWonBy(Player.B) ? playerB.scorePoint() : playerB;
    GameState newGameState = gameState.scorePoint(newPlayerA, newPlayerB, point, scoringSystem);

    return new Game(newPlayerA, newPlayerB, newGameState, scoringSystem);
  }

  public Game reset() {
    return new Game(playerA.reset(), playerB.reset(), InProgressGameState.INSTANCE, scoringSystem);
  }

  public boolean isGameFinished() {
    return gameState.isGameFinished();
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Game that = (Game) obj;
    return Objects.equals(playerA, that.playerA)
        && Objects.equals(playerB, that.playerB)
        && gameState == that.gameState
        && Objects.equals(scoringSystem, that.scoringSystem);
  }

  @Override
  public int hashCode() {
    return Objects.hash(playerA, playerB, gameState, scoringSystem);
  }

  @Override
  public String toString() {
    return "Game{"
        + "playerA="
        + playerA
        + ", playerB="
        + playerB
        + ", gameState="
        + gameState
        + ", scoringSystem="
        + scoringSystem
        + '}';
  }
}
