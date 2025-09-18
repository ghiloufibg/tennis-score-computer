package com.ghiloufi.kata.domain.state;

import com.ghiloufi.kata.domain.model.Player;
import com.ghiloufi.kata.domain.model.Point;
import com.ghiloufi.kata.domain.model.ScoringSystem;
import com.ghiloufi.kata.domain.service.GameStateTransitioner;

public final class InProgressGameState implements GameState {

  public static final InProgressGameState INSTANCE = new InProgressGameState();

  private InProgressGameState() {}

  @Override
  public GameState scorePoint(
      Player playerA, Player playerB, Point point, ScoringSystem scoringSystem) {
    int pointsA = playerA.score().points();
    int pointsB = playerB.score().points();

    GameState nextState = GameStateTransitioner.nextState(pointsA, pointsB, scoringSystem);
    return nextState instanceof InProgressGameState ? this : nextState;
  }

  @Override
  public boolean isGameFinished() {
    return false;
  }

  @Override
  public String getDisplayName() {
    return "IN_PROGRESS";
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
