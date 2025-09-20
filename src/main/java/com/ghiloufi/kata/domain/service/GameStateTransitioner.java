package com.ghiloufi.kata.domain.service;

import com.ghiloufi.kata.domain.model.ScoringSystem;
import com.ghiloufi.kata.domain.state.GameState;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class GameStateTransitioner {

  private GameStateTransitioner() {}

  public static GameState nextState(int pointsA, int pointsB, ScoringSystem scoring) {
    return Stream.<Supplier<GameState>>of(
            () -> scoring.isDeuce(pointsA, pointsB) ? GameState.DEUCE : null,
            () -> scoring.hasAdvantage(pointsA, pointsB) ? GameState.ADVANTAGE_A : null,
            () -> scoring.hasAdvantage(pointsB, pointsA) ? GameState.ADVANTAGE_B : null,
            () -> scoring.hasWonGame(pointsA, pointsB) ? GameState.WON_A : null,
            () -> scoring.hasWonGame(pointsB, pointsA) ? GameState.WON_B : null)
        .map(Supplier::get)
        .filter(Objects::nonNull)
        .findFirst()
        .orElse(GameState.PLAYING);
  }
}
