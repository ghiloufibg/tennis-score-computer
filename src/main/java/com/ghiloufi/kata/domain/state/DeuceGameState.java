package com.ghiloufi.kata.domain.state;

import com.ghiloufi.kata.domain.model.Player;
import com.ghiloufi.kata.domain.model.Point;
import com.ghiloufi.kata.domain.model.ScoringSystem;

public final class DeuceGameState implements GameState {

  public static final DeuceGameState INSTANCE = new DeuceGameState();

  private DeuceGameState() {}

  @Override
  public GameState scorePoint(
      Player playerA, Player playerB, Point point, ScoringSystem scoringSystem) {
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
