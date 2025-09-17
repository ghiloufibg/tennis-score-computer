package com.ghiloufi.kata.testutil.helpers;

import com.ghiloufi.kata.domain.AdvantageGameState;
import com.ghiloufi.kata.domain.DeuceGameState;
import com.ghiloufi.kata.domain.GameWonState;
import com.ghiloufi.kata.domain.InProgressGameState;
import com.ghiloufi.kata.domain.Player;
import com.ghiloufi.kata.domain.TennisGameState;
import java.util.function.Predicate;

public final class GameStateMatchers {

  private GameStateMatchers() {
  }

  public static boolean isInProgress(TennisGameState state) {
    return state instanceof InProgressGameState;
  }

  public static boolean isDeuce(TennisGameState state) {
    return state instanceof DeuceGameState;
  }

  public static boolean isAdvantageA(TennisGameState state) {
    return state instanceof AdvantageGameState advantage
        && advantage.getPlayerWithAdvantage() == Player.A;
  }

  public static boolean isAdvantageB(TennisGameState state) {
    return state instanceof AdvantageGameState advantage
        && advantage.getPlayerWithAdvantage() == Player.B;
  }

  public static boolean isGameWonA(TennisGameState state) {
    return state instanceof GameWonState gameWon
        && gameWon.getWinner() == Player.A;
  }

  public static boolean isGameWonB(TennisGameState state) {
    return state instanceof GameWonState gameWon
        && gameWon.getWinner() == Player.B;
  }
}