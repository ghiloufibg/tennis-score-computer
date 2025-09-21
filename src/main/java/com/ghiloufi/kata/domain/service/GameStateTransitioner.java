package com.ghiloufi.kata.domain.service;

import com.ghiloufi.kata.domain.model.ScoringSystem;
import com.ghiloufi.kata.domain.state.GameState;

public final class GameStateTransitioner {

  private GameStateTransitioner() {}

  public static GameState nextState(int pointsA, int pointsB, ScoringSystem scoring) {
    if (scoring.hasWonGame(pointsA, pointsB)) return GameState.WON_A;
    if (scoring.hasWonGame(pointsB, pointsA)) return GameState.WON_B;
    if (scoring.hasAdvantage(pointsA, pointsB)) return GameState.ADVANTAGE_A;
    if (scoring.hasAdvantage(pointsB, pointsA)) return GameState.ADVANTAGE_B;
    if (scoring.isDeuce(pointsA, pointsB)) return GameState.DEUCE;
    return GameState.PLAYING;
  }
}
