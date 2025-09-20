package com.ghiloufi.kata.domain.state;

import com.ghiloufi.kata.domain.model.Player;
import com.ghiloufi.kata.domain.model.Point;
import com.ghiloufi.kata.domain.model.ScoringSystem;
import com.ghiloufi.kata.domain.service.GameStateTransitioner;

public enum GameState {
  PLAYING(false),
  DEUCE(false),
  ADVANTAGE_A(false),
  ADVANTAGE_B(false),
  WON_A(true),
  WON_B(true);

  private final boolean finished;

  GameState(boolean finished) {
    this.finished = finished;
  }

  public boolean isGameFinished() {
    return finished;
  }

  public GameState scorePoint(
      Player playerA, Player playerB, Point point, ScoringSystem scoringSystem) {
    return switch (this) {
      case PLAYING -> {
        int pointsA = playerA.score().points();
        int pointsB = playerB.score().points();
        yield GameStateTransitioner.nextState(pointsA, pointsB, scoringSystem);
      }
      case DEUCE -> {
        if (point.isWonBy(Player.A)) yield ADVANTAGE_A;
        if (point.isWonBy(Player.B)) yield ADVANTAGE_B;
        yield this;
      }
      case ADVANTAGE_A -> point.isWonBy(Player.A) ? WON_A : DEUCE;
      case ADVANTAGE_B -> point.isWonBy(Player.B) ? WON_B : DEUCE;
      case WON_A, WON_B -> this;
    };
  }
}
