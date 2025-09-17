package com.ghiloufi.kata.domain;

public final class InProgressGameState implements TennisGameState {

  public static final InProgressGameState INSTANCE = new InProgressGameState();

  private InProgressGameState() {}

  @Override
  public TennisGameState scorePoint(
      Player playerA, Player playerB, Point point, TennisScoringSystem scoringSystem) {
    int pointsA = playerA.score().points();
    int pointsB = playerB.score().points();

    TennisGameState nextState = GameStateTransitioner.nextState(pointsA, pointsB, scoringSystem);
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
