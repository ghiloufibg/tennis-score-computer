package com.ghiloufi.kata.domain;

public final class DeuceGameState implements TennisGameState {

  public static final DeuceGameState INSTANCE = new DeuceGameState();

  private DeuceGameState() {}

  @Override
  public TennisGameState scorePoint(
      Player playerA, Player playerB, Point point, TennisScoringSystem scoringSystem) {
    if (point.isWonBy(Player.A)) {
      return new AdvantageGameState(Player.A);
    }
    if (point.isWonBy(Player.B)) {
      return new AdvantageGameState(Player.B);
    }
    return this;
  }

  @Override
  public boolean isGameFinished() {
    return false;
  }

  @Override
  public String getDisplayName() {
    return "DEUCE";
  }

  @Override
  public Player getWinner() {
    return null;
  }

  @Override
  public String toString() {
    return getDisplayName();
  }
}
