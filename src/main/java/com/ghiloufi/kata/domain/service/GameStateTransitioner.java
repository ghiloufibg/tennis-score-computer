package com.ghiloufi.kata.domain.service;

import com.ghiloufi.kata.domain.model.Player;
import com.ghiloufi.kata.domain.model.ScoringSystem;
import com.ghiloufi.kata.domain.state.AdvantageGameState;
import com.ghiloufi.kata.domain.state.DeuceGameState;
import com.ghiloufi.kata.domain.state.GameState;
import com.ghiloufi.kata.domain.state.GameWonState;
import com.ghiloufi.kata.domain.state.InProgressGameState;

public final class GameStateTransitioner {

  private GameStateTransitioner() {}

  public static GameState nextState(int pointsA, int pointsB, ScoringSystem scoring) {
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
