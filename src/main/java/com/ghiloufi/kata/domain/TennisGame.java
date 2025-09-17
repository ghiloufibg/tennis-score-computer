package com.ghiloufi.kata.domain;

import java.util.Objects;

public final class TennisGame {

  private final Player playerA;
  private final Player playerB;
  private final GameState gameState;

  public TennisGame(Player playerA, Player playerB, GameState gameState) {
    this.playerA = playerA;
    this.playerB = playerB;
    this.gameState = gameState;
  }

  public static TennisGame newGame() {
    return new TennisGame(Player.A, Player.B, GameState.IN_PROGRESS);
  }

  public static TennisGame withPlayers(String nameA, String nameB) {
    return new TennisGame(Player.withName(nameA), Player.withName(nameB), GameState.IN_PROGRESS);
  }

  public TennisGame scorePoint(Point point) {
    if (isGameFinished()) {
      return this;
    }

    Player newPlayerA = point.isWonBy(Player.A) ? playerA.scorePoint() : playerA;
    Player newPlayerB = point.isWonBy(Player.B) ? playerB.scorePoint() : playerB;

    GameState newGameState = calculateGameState(newPlayerA, newPlayerB);

    return new TennisGame(newPlayerA, newPlayerB, newGameState);
  }

  private GameState calculateGameState(Player playerA, Player playerB) {
    Score scoreA = playerA.score();
    Score scoreB = playerB.score();

    int pointsA = scoreA.points();
    int pointsB = scoreB.points();

    if (pointsA >= 3 && pointsA == pointsB) {
      return GameState.DEUCE;
    }

    if (pointsA >= 4 && pointsA == pointsB + 1) {
      return GameState.ADVANTAGE_A;
    }

    if (pointsB >= 4 && pointsB == pointsA + 1) {
      return GameState.ADVANTAGE_B;
    }

    if (pointsA >= 4 && pointsA >= pointsB + 2) {
      return GameState.GAME_WON_A;
    }

    if (pointsB >= 4 && pointsB >= pointsA + 2) {
      return GameState.GAME_WON_B;
    }

    return GameState.IN_PROGRESS;
  }

  public TennisGame reset() {
    return new TennisGame(playerA.reset(), playerB.reset(), GameState.IN_PROGRESS);
  }

  public boolean isGameFinished() {
    return gameState == GameState.GAME_WON_A || gameState == GameState.GAME_WON_B;
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
    TennisGame that = (TennisGame) obj;
    return Objects.equals(playerA, that.playerA)
        && Objects.equals(playerB, that.playerB)
        && gameState == that.gameState;
  }

  @Override
  public int hashCode() {
    return Objects.hash(playerA, playerB, gameState);
  }

  @Override
  public String toString() {
    return "TennisGame{"
        + "playerA="
        + playerA
        + ", playerB="
        + playerB
        + ", gameState="
        + gameState
        + '}';
  }
}
