package com.ghiloufi.kata.domain;

public final class GameStateTransitioner {

  private GameStateTransitioner() {}

  public static TennisGameState nextState(int pointsA, int pointsB, TennisScoringSystem scoring) {
    if (scoring.isDeuce(pointsA, pointsB)) {
      return DeuceGameState.INSTANCE;
    }

    if (scoring.hasAdvantage(pointsA, pointsB)) {
      return new AdvantageGameState(Player.A);
    }

    if (scoring.hasAdvantage(pointsB, pointsA)) {
      return new AdvantageGameState(Player.B);
    }

    if (scoring.hasWonGame(pointsA, pointsB)) {
      return new GameWonState(Player.A);
    }

    if (scoring.hasWonGame(pointsB, pointsA)) {
      return new GameWonState(Player.B);
    }

    return InProgressGameState.INSTANCE;
  }
}
