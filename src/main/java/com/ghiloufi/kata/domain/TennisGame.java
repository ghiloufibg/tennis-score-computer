package com.ghiloufi.kata.domain;

import java.util.Objects;

public final class TennisGame {

  private final Player playerA;
  private final Player playerB;
  private final TennisGameState gameState;
  private final TennisScoringSystem scoringSystem;

  public TennisGame(Player playerA, Player playerB, TennisGameState gameState) {
    this(playerA, playerB, gameState, TennisScoringSystem.STANDARD);
  }

  public TennisGame(
      Player playerA,
      Player playerB,
      TennisGameState gameState,
      TennisScoringSystem scoringSystem) {
    this.playerA = playerA;
    this.playerB = playerB;
    this.gameState = gameState;
    this.scoringSystem = scoringSystem;
  }

  public static TennisGame newGame() {
    return new TennisGame(
        Player.A, Player.B, InProgressGameState.INSTANCE, TennisScoringSystem.STANDARD);
  }

  public static TennisGame withPlayers(String nameA, String nameB) {
    return new TennisGame(
        Player.withName(nameA),
        Player.withName(nameB),
        InProgressGameState.INSTANCE,
        TennisScoringSystem.STANDARD);
  }

  public TennisGame scorePoint(Point point) {
    if (isGameFinished()) {
      return this;
    }

    Player newPlayerA = point.isWonBy(Player.A) ? playerA.scorePoint() : playerA;
    Player newPlayerB = point.isWonBy(Player.B) ? playerB.scorePoint() : playerB;
    TennisGameState newGameState =
        gameState.scorePoint(newPlayerA, newPlayerB, point, scoringSystem);

    return new TennisGame(newPlayerA, newPlayerB, newGameState, scoringSystem);
  }

  public TennisGame reset() {
    return new TennisGame(
        playerA.reset(), playerB.reset(), InProgressGameState.INSTANCE, scoringSystem);
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

  public TennisGameState getGameState() {
    return gameState;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    TennisGame that = (TennisGame) obj;
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
    return "TennisGame{"
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
