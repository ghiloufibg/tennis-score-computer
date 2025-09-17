package com.ghiloufi.kata.domain;

import java.util.Objects;

public final class GameWonState implements TennisGameState {

  private final Player winner;

  public GameWonState(Player winner) {
    this.winner = Objects.requireNonNull(winner);
  }

  @Override
  public TennisGameState scorePoint(
      Player playerA, Player playerB, Point point, TennisScoringSystem scoringSystem) {
    return this;
  }

  @Override
  public boolean isGameFinished() {
    return true;
  }

  @Override
  public String getDisplayName() {
    return winner == Player.A ? "GAME_WON_A" : "GAME_WON_B";
  }

  @Override
  public Player getWinner() {
    return winner;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    GameWonState that = (GameWonState) obj;
    return Objects.equals(winner, that.winner);
  }

  @Override
  public int hashCode() {
    return Objects.hash(winner);
  }

  @Override
  public String toString() {
    return getDisplayName();
  }
}
