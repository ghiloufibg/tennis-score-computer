package com.ghiloufi.kata.testutil.helpers;

import com.ghiloufi.kata.domain.state.GameState;

public final class GameStateMatchers {

  private GameStateMatchers() {}

  public static boolean isInProgress(GameState state) {
    return state == GameState.PLAYING;
  }

  public static boolean isDeuce(GameState state) {
    return state == GameState.DEUCE;
  }

  public static boolean isAdvantageA(GameState state) {
    return state == GameState.ADVANTAGE_A;
  }

  public static boolean isAdvantageB(GameState state) {
    return state == GameState.ADVANTAGE_B;
  }

  public static boolean isGameWonA(GameState state) {
    return state == GameState.WON_A;
  }

  public static boolean isGameWonB(GameState state) {
    return state == GameState.WON_B;
  }
}
