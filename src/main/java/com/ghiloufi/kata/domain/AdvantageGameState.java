package com.ghiloufi.kata.domain;

import java.util.Objects;

public final class AdvantageGameState implements TennisGameState {

  private final Player playerWithAdvantage;

  public AdvantageGameState(Player playerWithAdvantage) {
    this.playerWithAdvantage = Objects.requireNonNull(playerWithAdvantage);
  }

  @Override
  public TennisGameState scorePoint(
      Player playerA, Player playerB, Point point, TennisScoringSystem scoringSystem) {
    if (point.isWonBy(playerWithAdvantage)) {
      return new GameWonState(playerWithAdvantage);
    } else {
      return DeuceGameState.INSTANCE;
    }
  }

  @Override
  public boolean isGameFinished() {
    return false;
  }

  @Override
  public String getDisplayName() {
    return playerWithAdvantage == Player.A ? "ADVANTAGE_A" : "ADVANTAGE_B";
  }

  @Override
  public Player getWinner() {
    return null;
  }

  public Player getPlayerWithAdvantage() {
    return playerWithAdvantage;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    AdvantageGameState that = (AdvantageGameState) obj;
    return Objects.equals(playerWithAdvantage, that.playerWithAdvantage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(playerWithAdvantage);
  }

  @Override
  public String toString() {
    return getDisplayName();
  }
}
