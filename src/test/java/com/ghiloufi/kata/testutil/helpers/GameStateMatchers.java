package com.ghiloufi.kata.testutil.helpers;

import com.ghiloufi.kata.domain.model.Player;
import com.ghiloufi.kata.domain.state.AdvantageGameState;
import com.ghiloufi.kata.domain.state.DeuceGameState;
import com.ghiloufi.kata.domain.state.GameState;
import com.ghiloufi.kata.domain.state.GameWonState;
import com.ghiloufi.kata.domain.state.InProgressGameState;

public final class GameStateMatchers {

  private GameStateMatchers() {}

  public static boolean isInProgress(GameState state) {
    return state instanceof InProgressGameState;
  }

  public static boolean isDeuce(GameState state) {
    return state instanceof DeuceGameState;
  }

  public static boolean isAdvantageA(GameState state) {
    return state instanceof AdvantageGameState advantage
        && advantage.getPlayerWithAdvantage() == Player.A;
  }

  public static boolean isAdvantageB(GameState state) {
    return state instanceof AdvantageGameState advantage
        && advantage.getPlayerWithAdvantage() == Player.B;
  }

  public static boolean isGameWonA(GameState state) {
    return state instanceof GameWonState gameWon && gameWon.getWinner() == Player.A;
  }

  public static boolean isGameWonB(GameState state) {
    return state instanceof GameWonState gameWon && gameWon.getWinner() == Player.B;
  }
}
